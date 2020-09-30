package br.com.zup.pact.consumer.pact;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Map.Entry;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Message.Builder;
import com.google.protobuf.util.JsonFormat;
import com.google.protobuf.util.JsonFormat.Parser;
import com.google.protobuf.util.JsonFormat.Printer;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.MethodDescriptor.Marshaller;
import io.grpc.MethodDescriptor.PrototypeMarshaller;
import io.grpc.Status;
import io.grpc.internal.GrpcUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrpcHttpChannel extends Channel {

    private static final Logger log = LoggerFactory.getLogger(GrpcHttpChannel.class);

    private final String authority;

    private final String host;

    private final int port;

    public GrpcHttpChannel(final String host, final int port) {
        this.host = host;
        this.port = port;
        this.authority = GrpcUtil.authorityFromHostAndPort(host, port);
    }

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> newCall(
            final MethodDescriptor<ReqT, RespT> method, final CallOptions callOptions) {

        final ReqT reqT = ((PrototypeMarshaller<ReqT>) method.getRequestMarshaller()).getMessagePrototype();
        final RespT respT = ((PrototypeMarshaller<RespT>) method.getResponseMarshaller()).getMessagePrototype();
        final Marshaller<ReqT> requestMarshaller = this.jsonMarshaller(reqT);
        final Marshaller<RespT> responseMarshaller = this.jsonMarshaller(respT);

        return new ClientCall<ReqT, RespT>() {
            private Listener<RespT> responseListener;

            private Metadata headers;

            @Override
            public void start(final Listener<RespT> responseListener, final Metadata headers) {
                this.responseListener = responseListener;
            }

            @Override
            public void request(final int numMessages) {}

            @Override
            public void cancel(final String message, final Throwable cause) {}

            @Override
            public void halfClose() {}

            @Override
            public void sendMessage(final ReqT message) {
                log.debug("sendMessage");
                final URL url = GrpcHttpChannel.this.getUrl(method);
                final String jsonRequest = GrpcHttpChannel.this.asString(requestMarshaller.stream(message));
                final String jsonResponse = GrpcHttpChannel.this.post(url, jsonRequest, "application/json", null);
                final RespT response = responseMarshaller.parse(new ByteArrayInputStream(jsonResponse.getBytes()));
                this.responseListener.onMessage(response);
                this.responseListener.onClose(Status.OK, this.headers);
            }

        };

    }

    private <ReqT, RespT> URL getUrl(final MethodDescriptor<ReqT, RespT> method) {
        try {
            return new URL("http", this.host, this.port, "/grpc/" + method.getFullMethodName());
        } catch (final MalformedURLException e) {
            throw new RuntimeException("Error creating URL for grpc method call", e);
        }
    }

    protected String post(final URL url, final String body, final String contentType,
                          final Map<String, String> headers) {
        try {
            return this.doPost(url, body, contentType, headers);
        } catch (final Exception e) {
            throw new RuntimeException("Error sending http request to " + url, e);
        }
    }

    protected String doPost(final URL url, final String body, final String contentType,
                            final Map<String, String> headers)
            throws Exception {
        log.debug("Sending post request to {} with payload {}", url, body);
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        try (AutoCloseable conc = () -> con.disconnect()) {

            con.setRequestMethod("POST");

            if (StringUtils.isNotBlank(contentType)) {
                con.setRequestProperty("Content-Type", contentType);
            }
            if ((headers != null) && !headers.isEmpty()) {
                for (final Entry<String, String> header : headers.entrySet()) {
                    con.setRequestProperty(header.getKey(), header.getValue());
                }
            }

            if (StringUtils.isNotBlank(body)) {
                con.setDoOutput(true);
                final DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(body);
                out.flush();
                out.close();
            }

            final int status = con.getResponseCode();
            final InputStream is = status > 299 ? con.getErrorStream() : con.getInputStream();
            final String response = IOUtils.toString(is, con.getContentEncoding());
            log.debug("Response with statuc code {} and payload {}", status, response);
            is.close();
            con.disconnect();

            return response;
        }

    }

    @Override
    public String authority() {
        return this.authority;
    }

    protected Marshaller jsonMarshaller(final Object defaultInstance) {
        final Parser parser = JsonFormat.parser();
        final Printer printer = JsonFormat.printer();
        return this.jsonMarshaller((Message) defaultInstance, parser, printer);
    }

    protected <T extends Message> Marshaller<T> jsonMarshaller(final T defaultInstance, final Parser parser,
                                                               final Printer printer) {

        final Charset charset = Charset.forName("UTF-8");

        return new Marshaller<T>() {
            @Override
            public InputStream stream(final T value) {
                try {
                    return new ByteArrayInputStream(printer.print(value).getBytes(charset));
                } catch (final InvalidProtocolBufferException e) {
                    throw Status.INTERNAL
                            .withCause(e)
                            .withDescription("Unable to print json proto")
                            .asRuntimeException();
                }
            }

            @SuppressWarnings("unchecked")
            @Override
            public T parse(final InputStream stream) {
                final Builder builder = defaultInstance.newBuilderForType();
                final Reader reader = new InputStreamReader(stream, charset);
                T proto;
                try {
                    parser.merge(reader, builder);
                    proto = (T) builder.build();
                    reader.close();
                } catch (final InvalidProtocolBufferException e) {
                    throw Status.INTERNAL.withDescription("Invalid protobuf byte sequence")
                            .withCause(e)
                            .asRuntimeException();
                } catch (final IOException e) {
                    // Same for now, might be unavailable
                    throw Status.INTERNAL.withDescription("Invalid protobuf byte sequence")
                            .withCause(e)
                            .asRuntimeException();
                }
                return proto;
            }
        };
    }

    private String asString(final InputStream stream) {
        try {
            return IOUtils.toString(stream, "UTF-8");
        } catch (final IOException e) {
            throw new RuntimeException("Error converting stream to string", e);
        }
    }

}
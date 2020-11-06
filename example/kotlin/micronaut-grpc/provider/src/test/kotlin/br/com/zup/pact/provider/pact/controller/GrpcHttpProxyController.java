package br.com.zup.pact.provider.pact.controller;
//
//import com.google.protobuf.InvalidProtocolBufferException;
//import com.google.protobuf.Message;
//import com.google.protobuf.Message.Builder;
//import com.google.protobuf.util.JsonFormat;
//import com.google.protobuf.util.JsonFormat.Parser;
//import com.google.protobuf.util.JsonFormat.Printer;
//import io.grpc.CallOptions;
//import io.grpc.Channel;
//import io.grpc.MethodDescriptor;
//import io.grpc.MethodDescriptor.Marshaller;
//import io.grpc.MethodDescriptor.PrototypeMarshaller;
//import io.grpc.Status;
//import io.grpc.inprocess.InProcessChannelBuilder;
//import io.grpc.netty.NettyChannelBuilder;
//import io.grpc.stub.ClientCalls;
//import io.micronaut.context.annotation.Value;
//import io.micronaut.grpc.server.GrpcEmbeddedServer;
//import io.micronaut.http.MediaType;
//import io.micronaut.http.annotation.*;
//import io.micronaut.runtime.event.annotation.EventListener;
//import io.micronaut.runtime.server.EmbeddedServer;
//import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
//import org.apache.commons.io.IOUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.inject.Inject;
//import javax.servlet.http.HttpServletRequest;
//import java.io.*;
//import java.nio.charset.Charset;
//import java.util.HashMap;
//import java.util.Map;
//
//@Controller
//public class GrpcHttpProxyController {
//
//    private static Logger log = LoggerFactory.getLogger(GrpcHttpProxyController.class);
//
//    @Inject
//    public EmbeddedServer embeddedServer;
//
//    @Value("${micronaut.server.name}")
//    public String inProcessServerName;
//
//    @Value("${grpc.server.port}")
//    public int port;
//
//    private final Map<String, MethodDescriptor> methodsByName = new HashMap<>();
//
////    @Post(uri = "/grpc/**", produces = MediaType.APPLICATION_JSON)
//
//    @Post(value = "/grpc/**", produces = "application/json")
////    @ResponseBody
//    public String grpcProxy(final String json, final HttpServletRequest request) throws IOException {
//        final String grpcMethodName = request.getRequestURI().replace("/grpc/", "");
//        return this.grcpCall(grpcMethodName, json);
//    }
//
//    protected String grcpCall(final String methodName, final String jsonRequest) throws IOException {
//        log.info("Performing grpc call to method {}", methodName);
//        log.debug("Performing grpc call to method {} with payload {}", methodName, jsonRequest);
//
//        final MethodDescriptor method = this.methodsByName.get(methodName);
//        final PrototypeMarshaller<?> requestMarshaller = (PrototypeMarshaller<?>) method.getRequestMarshaller();
//        final PrototypeMarshaller<?> responseMarshaller = (PrototypeMarshaller<?>) method.getResponseMarshaller();
//        final Marshaller<?> jsonRequestMarshaller = this.jsonMarshaller(requestMarshaller.getMessagePrototype());
//        final Marshaller jsonResponseMarshaller = this.jsonMarshaller(responseMarshaller.getMessagePrototype());
//
//        final Object requestGrpcObject = jsonRequestMarshaller.parse(new ByteArrayInputStream(jsonRequest.getBytes()));
//        final Object responseGrpcObject = this.blockingUnaryCall(method, requestGrpcObject);
//        final InputStream responseStream = jsonResponseMarshaller.stream(responseGrpcObject);
//        final String jsonResponse = IOUtils.toString(responseStream, Charset.defaultCharset());
//        log.debug("Returning grpc call to method {} with response {}", jsonResponse);
//
//        return jsonResponse;
//    }
//
//    protected Object blockingUnaryCall(final MethodDescriptor<Object, Object> method, final Object req) {
//        final Channel channel = StringUtils.isNotBlank(this.inProcessServerName)
//                ? InProcessChannelBuilder.forName(this.inProcessServerName).directExecutor().build()
//                : NettyChannelBuilder.forAddress("localhost", this.port).directExecutor().usePlaintext().build();
//
//        final CallOptions callOptions = CallOptions.DEFAULT;
//
//        return ClientCalls.blockingUnaryCall(channel, method, callOptions, req);
//    }
//
//    @EventListener
//    public void handleEvent(final GrpcEmbeddedServer event) {
//        log.info("Reading grpc configured method descriptors");
//
//        event.getServer().getServices().forEach(service -> service.getMethods().forEach(method -> {
//            this.methodsByName.put(method.getMethodDescriptor().getFullMethodName(), method.getMethodDescriptor());
//        }));
//
//        log.debug("MethodDescriptors: {}", this.methodsByName.keySet());
//    }
//
//    protected Marshaller<?> jsonMarshaller(final Object defaultInstance) {
//        final Parser parser = JsonFormat.parser();
//        final Printer printer = JsonFormat.printer();
//        return this.jsonMarshaller((Message) defaultInstance, parser, printer);
//    }
//
//    protected <T extends Message> Marshaller<T> jsonMarshaller(final T defaultInstance, final Parser parser,
//                                                               final Printer printer) {
//
//        final Charset charset = Charset.forName("UTF-8");
//
//        return new Marshaller<T>() {
//            @Override
//            public InputStream stream(final T value) {
//                try {
//                    return new ByteArrayInputStream(printer.print(value).getBytes(charset));
//                } catch (final InvalidProtocolBufferException e) {
//                    throw Status.INTERNAL
//                            .withCause(e)
//                            .withDescription("Unable to print json proto")
//                            .asRuntimeException();
//                }
//            }
//
//            @SuppressWarnings("unchecked")
//            @Override
//            public T parse(final InputStream stream) {
//                final Builder builder = defaultInstance.newBuilderForType();
//                final Reader reader = new InputStreamReader(stream, charset);
//                T proto;
//                try {
//                    parser.merge(reader, builder);
//                    proto = (T) builder.build();
//                    reader.close();
//                } catch (final InvalidProtocolBufferException e) {
//                    throw Status.INTERNAL.withDescription("Invalid protobuf byte sequence")
//                            .withCause(e)
//                            .asRuntimeException();
//                } catch (final IOException e) {
//                    // Same for now, might be unavailable
//                    throw Status.INTERNAL.withDescription("Invalid protobuf byte sequence")
//                            .withCause(e)
//                            .asRuntimeException();
//                }
//                return proto;
//            }
//        };
//    }
//
//}
//package br.com.zup.pact.provider.pact.controller;
//
//import com.google.protobuf.InvalidProtocolBufferException;
//import com.google.protobuf.Message;
//import com.google.protobuf.util.JsonFormat;
//import io.grpc.CallOptions;
//import io.grpc.Channel;
//import io.grpc.MethodDescriptor;
//import io.grpc.Status;
//import io.grpc.inprocess.InProcessChannelBuilder;
//import io.grpc.netty.NettyChannelBuilder;
//import io.grpc.stub.ClientCalls;
//import io.micronaut.context.annotation.Value;
//import io.micronaut.context.event.ApplicationEventListener;
//import io.micronaut.grpc.annotation.GrpcService;
//import io.micronaut.grpc.server.GrpcEmbeddedServer;
//import io.micronaut.http.MediaType;
//import io.micronaut.http.annotation.Body;
//import io.micronaut.http.annotation.Controller;
//import io.micronaut.http.annotation.Post;
//import io.micronaut.runtime.event.annotation.EventListener;
//import io.micronaut.scheduling.annotation.Async;
//import org.apache.commons.io.IOUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.*;
//import java.nio.charset.Charset;
//import java.util.HashMap;
//import java.util.Map;
//
////@Controller
//public class ProxyController {
//
//    private static Logger log = LoggerFactory.getLogger(ProxyController.class);
//
////    @Value("${server.port}")
////    protected int localPort;
//    private final Map<String, MethodDescriptor> methodsByName = new HashMap<>();
////
////    @Value("${grpc.port}")
////    public int port;
//    @Value("${grpc.inProcessServerName:}")
//    public String inProcessServerName;
//
////    @Post(value = "/grpc/br.com.zup.pact.provider.resource.ProductService/getAll", produces = MediaType.APPLICATION_JSON)
////    public String hello(@Body final String json) throws IOException {
////        final String grpcMethodName = "br.com.zup.pact.provider.resource.ProductService/getAll";
////        return this.grcpCall(grpcMethodName, json);
////    }
//
////    @Post(value = "/grpc/br.com.zup.pact.provider.resource.ProductService/getAll", produces = MediaType.APPLICATION_JSON)
//////    @ResponseBody
////    public String grpcProxy(@Body final String json, final HttpServletRequest request) throws IOException {
////        final String grpcMethodName = request.getRequestURI().replace("/grpc/", "");
////        return this.grcpCall(grpcMethodName, json);
////    }
//
//
//
//    protected String grcpCall(final String methodName, final String jsonRequest) throws IOException {
//        log.info("Performing grpc call to method {}", methodName);
//        log.debug("Performing grpc call to method {} with payload {}", methodName, jsonRequest);
//
//        final MethodDescriptor method = this.methodsByName.get(methodName);
//        final MethodDescriptor.PrototypeMarshaller<?> requestMarshaller =
//                (MethodDescriptor.PrototypeMarshaller<?>) method.getRequestMarshaller();
//        final MethodDescriptor.PrototypeMarshaller<?> responseMarshaller =
//                (MethodDescriptor.PrototypeMarshaller<?>) method.getResponseMarshaller();
//        final MethodDescriptor.Marshaller<?> jsonRequestMarshaller =
//                this.jsonMarshaller(requestMarshaller.getMessagePrototype());
//        final MethodDescriptor.Marshaller jsonResponseMarshaller =
//                this.jsonMarshaller(responseMarshaller.getMessagePrototype());
//
//        final Object requestGrpcObject =
//                jsonRequestMarshaller.parse(new ByteArrayInputStream(jsonRequest.getBytes()));
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
//                : NettyChannelBuilder.forAddress("localhost", 6565).directExecutor().usePlaintext().build();
//
//        final CallOptions callOptions = CallOptions.DEFAULT;
//
//        return ClientCalls.blockingUnaryCall(channel, method, callOptions, req);
//    }
//
//    protected MethodDescriptor.Marshaller<?> jsonMarshaller(final Object defaultInstance) {
//        final JsonFormat.Parser parser = JsonFormat.parser();
//        final JsonFormat.Printer printer = JsonFormat.printer();
//        return this.jsonMarshaller((Message) defaultInstance, parser, printer);
//    }
//
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
//    protected <T extends Message> MethodDescriptor.Marshaller<T> jsonMarshaller(final T defaultInstance, final JsonFormat.Parser parser,
//                                                                                final JsonFormat.Printer printer) {
//
//        final Charset charset = Charset.forName("UTF-8");
//
//        return new MethodDescriptor.Marshaller<T>() {
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
//                final Message.Builder builder = defaultInstance.newBuilderForType();
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
////    @Override
////    public void onApplicationEvent(GrpcEmbeddedServer event) {
////        log.info("Reading grpc configured method descriptors");
////
////        event.getServer().getServices().forEach(service -> service.getMethods().forEach(method -> {
////            this.methodsByName.put(method.getMethodDescriptor().getFullMethodName(), method.getMethodDescriptor());
////        }));
////
////        log.debug("MethodDescriptors: {}", this.methodsByName.keySet());
////    }
//
//}

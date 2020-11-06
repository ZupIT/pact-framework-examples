package br.com.zup.pact.provider.pact.controller
//
//import com.google.protobuf.InvalidProtocolBufferException
//import com.google.protobuf.Message
//import com.google.protobuf.util.JsonFormat
//import com.google.protobuf.util.JsonFormat.Parser
//import com.google.protobuf.util.JsonFormat.Printer
//import io.grpc.CallOptions
//import io.grpc.Channel
//import io.grpc.MethodDescriptor
//import io.grpc.MethodDescriptor.Marshaller
//import io.grpc.MethodDescriptor.PrototypeMarshaller
//import io.grpc.Status
//import io.grpc.inprocess.InProcessChannelBuilder
//import io.grpc.netty.NettyChannelBuilder
//import io.grpc.stub.ClientCalls
//import io.micronaut.context.annotation.Value
//import io.micronaut.grpc.server.GrpcEmbeddedServer
//import io.micronaut.http.annotation.Body
//import io.micronaut.http.annotation.Controller
//import io.micronaut.http.annotation.Post
//import io.micronaut.runtime.event.annotation.EventListener
//import org.apache.commons.io.IOUtils
//import org.apache.commons.lang3.StringUtils
//import org.slf4j.LoggerFactory
//import java.io.*
//import java.nio.charset.Charset
//import java.util.*
//import javax.servlet.http.HttpServletRequest
//
//@Controller
//class GrpcHttpProxyController {
//    @Value("\${micronaut.application.port}")
//    protected var localPort = 8080
//
//    @Value("\${grpc.inProcessServerName:}")
//    var inProcessServerName: String? = null
//
//    @Value("\${grpc.server.port}")
//    var port = 0
//    private val methodsByName: MutableMap<String, MethodDescriptor<Any, Any>> = HashMap()
//    @Post(value = "/grpc/**", produces = arrayOf("application/json"))
//    // TODO @ResponseBody
//    @Throws(IOException::class)
//    fun grpcProxy(@Body json: String, request: HttpServletRequest): String {
//        val grpcMethodName: String = request.requestURI.replace("/grpc/", "")
//        return grcpCall(grpcMethodName, json)
//    }
//
//    @Throws(IOException::class)
//    protected fun grcpCall(methodName: String, jsonRequest: String): String {
//        log.info("Performing grpc call to method {}", methodName)
//        log.debug("Performing grpc call to method {} with payload {}", methodName, jsonRequest)
//        val method: MethodDescriptor<Any, Any> = methodsByName[methodName]!!
//        val requestMarshaller = method.requestMarshaller as PrototypeMarshaller<*>
//        val responseMarshaller = method.responseMarshaller as PrototypeMarshaller<*>
//        val jsonRequestMarshaller = this.jsonMarshaller(requestMarshaller.messagePrototype)
//        val jsonResponseMarshaller = this.jsonMarshaller(responseMarshaller.messagePrototype)
//        val requestGrpcObject = jsonRequestMarshaller.parse(ByteArrayInputStream(jsonRequest.toByteArray()))
//        val responseGrpcObject = blockingUnaryCall(method, requestGrpcObject)
//        val responseStream = jsonResponseMarshaller.stream(responseGrpcObject as Nothing?)
//        val jsonResponse = IOUtils.toString(responseStream, Charset.defaultCharset())
//        log.debug("Returning grpc call to method {} with response {}", jsonResponse)
//        return jsonResponse
//    }
//
//    protected fun blockingUnaryCall(method: MethodDescriptor<Any, Any>?, req: Any): Any {
//        val channel: Channel = if (StringUtils.isNotBlank(inProcessServerName)) InProcessChannelBuilder.forName(inProcessServerName).directExecutor().build() else NettyChannelBuilder.forAddress("localhost", port).directExecutor().usePlaintext().build()
//        val callOptions = CallOptions.DEFAULT
//        return ClientCalls.blockingUnaryCall(channel, method, callOptions, req)
//    }
//
//    @EventListener
//    fun handleEvent(event: GrpcEmbeddedServer) {
//        log.info("Reading grpc configured method descriptors")
//        event.server.services.forEach { service -> service.methods.forEach { method -> methodsByName.put(method.getMethodDescriptor().getFullMethodName(), method.methodDescriptor as MethodDescriptor<Any, Any>) } }
//        log.debug("MethodDescriptors: {}", methodsByName.keys)
//    }
//
//    protected fun jsonMarshaller(defaultInstance: Any?): Marshaller<*> {
//        val parser: Parser = JsonFormat.parser()
//        val printer: Printer = JsonFormat.printer()
//        return this.jsonMarshaller(defaultInstance as Message?, parser, printer)
//    }
//
//    protected fun <T : Message?> jsonMarshaller(defaultInstance: T, parser: Parser,
//                                                printer: Printer): Marshaller<T> {
//        val charset = Charset.forName("UTF-8")
//        return object : Marshaller<T> {
//            override fun stream(value: T): InputStream {
//                return try {
//                    ByteArrayInputStream(printer.print(value).toByteArray(charset))
//                } catch (e: InvalidProtocolBufferException) {
//                    throw Status.INTERNAL
//                            .withCause(e)
//                            .withDescription("Unable to print json proto")
//                            .asRuntimeException()
//                }
//            }
//
//            override fun parse(stream: InputStream): T {
//                val builder = defaultInstance!!.newBuilderForType()
//                val reader: Reader = InputStreamReader(stream, charset)
//                val proto: T
//                try {
//                    parser.merge(reader, builder)
//                    proto = builder.build() as T
//                    reader.close()
//                } catch (e: InvalidProtocolBufferException) {
//                    throw Status.INTERNAL.withDescription("Invalid protobuf byte sequence")
//                            .withCause(e)
//                            .asRuntimeException()
//                } catch (e: IOException) {
//                    // Same for now, might be unavailable
//                    throw Status.INTERNAL.withDescription("Invalid protobuf byte sequence")
//                            .withCause(e)
//                            .asRuntimeException()
//                }
//                return proto
//            }
//        }
//    }
//
//    companion object {
//        private val log = LoggerFactory.getLogger(GrpcHttpProxyController::class.java)
//    }
//}
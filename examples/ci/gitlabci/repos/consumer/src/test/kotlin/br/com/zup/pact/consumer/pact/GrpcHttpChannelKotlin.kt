package br.com.zup.pact.consumer.pact

import com.google.protobuf.InvalidProtocolBufferException
import com.google.protobuf.Message
import com.google.protobuf.util.JsonFormat
import io.grpc.*
import io.grpc.MethodDescriptor.Marshaller
import io.grpc.MethodDescriptor.PrototypeMarshaller
import io.grpc.internal.GrpcUtil
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.nio.charset.Charset

open class GrpcHttpChannelKotlin(private val host: String, private val port: Int) : Channel() {
    private val authority: String = GrpcUtil.authorityFromHostAndPort(host, port)
    override fun <ReqT, RespT> newCall(
            method: MethodDescriptor<ReqT, RespT>, callOptions: CallOptions): ClientCall<ReqT, RespT> {
        val reqT = (method.requestMarshaller as PrototypeMarshaller<ReqT>).messagePrototype
        val respT = (method.responseMarshaller as PrototypeMarshaller<RespT>).messagePrototype
        val requestMarshaller: Marshaller<ReqT> = this.jsonMarshaller(reqT) as Marshaller<ReqT>
        val responseMarshaller: Marshaller<RespT> = this.jsonMarshaller(respT) as Marshaller<RespT>
        return object : ClientCall<ReqT, RespT>() {
            private var responseListener: Listener<RespT>? = null
            private val headers: Metadata? = null
            override fun start(responseListener: Listener<RespT>, headers: Metadata) {
                this.responseListener = responseListener
            }

            override fun request(numMessages: Int) {}
            override fun cancel(message: String?, cause: Throwable?) {}
            override fun halfClose() {}
            override fun sendMessage(message: ReqT) {
                log.debug("sendMessage")
                val url = getUrl(method)
                val jsonRequest = asString(requestMarshaller.stream(message))
                val jsonResponse = post(url, jsonRequest, "application/json", null)
                val response = responseMarshaller.parse(ByteArrayInputStream(jsonResponse.toByteArray()))
                responseListener!!.onMessage(response)
                responseListener!!.onClose(Status.OK, headers)
            }
        }
    }

    private fun <ReqT, RespT> getUrl(method: MethodDescriptor<ReqT, RespT>): URL {
        return try {
            URL("http", host, port, "/grpc/" + method.fullMethodName)
        } catch (e: MalformedURLException) {
            throw RuntimeException("Error creating URL for grpc method call", e)
        }
    }

    protected fun post(url: URL, body: String?, contentType: String?,
                       headers: Map<String?, String?>?): String {
        return try {
            doPost(url, body, contentType, headers)
        } catch (e: Exception) {
            throw RuntimeException("Error sending http request to $url", e)
        }
    }

    @Throws(Exception::class)
    protected fun doPost(url: URL, body: String?, contentType: String?,
                         headers: Map<String?, String?>?): String {
        log.debug("Sending post request to {} with payload {}", url, body)
        val con = url.openConnection() as HttpURLConnection
        AutoCloseable { con.disconnect() }.use { conc ->
            con.requestMethod = "POST"
            if (StringUtils.isNotBlank(contentType)) {
                con.setRequestProperty("Content-Type", contentType)
            }
            if (headers != null && !headers.isEmpty()) {
                for ((key, value) in headers) {
                    con.setRequestProperty(key, value)
                }
            }
            if (StringUtils.isNotBlank(body)) {
                con.doOutput = true
                val out = DataOutputStream(con.outputStream)
                out.writeBytes(body)
                out.flush()
                out.close()
            }
            val status = con.responseCode
            val `is` = if (status > 299) con.errorStream else con.inputStream
            val response = IOUtils.toString(`is`, con.contentEncoding)
            log.debug("Response with statuc code {} and payload {}", status, response)
            `is`.close()
            con.disconnect()
            return response
        }
    }

    override fun authority(): String {
        return authority
    }

    protected fun jsonMarshaller(defaultInstance: Any?): Marshaller<*> {
        val parser = JsonFormat.parser()
        val printer = JsonFormat.printer()
        return this.jsonMarshaller(defaultInstance as Message?, parser, printer)
    }

    protected fun <T : Message?> jsonMarshaller(defaultInstance: T, parser: JsonFormat.Parser,
                                                printer: JsonFormat.Printer): Marshaller<T> {
        val charset = Charset.forName("UTF-8")
        return object : Marshaller<T> {
            override fun stream(value: T): InputStream {
                return try {
                    ByteArrayInputStream(printer.print(value).toByteArray(charset))
                } catch (e: InvalidProtocolBufferException) {
                    throw Status.INTERNAL
                            .withCause(e)
                            .withDescription("Unable to print json proto")
                            .asRuntimeException()
                }
            }

            override fun parse(stream: InputStream): T {
                val builder = defaultInstance!!.newBuilderForType()
                val reader: Reader = InputStreamReader(stream, charset)
                val proto: T
                try {
                    parser.merge(reader, builder)
                    proto = builder.build() as T
                    reader.close()
                } catch (e: InvalidProtocolBufferException) {
                    throw Status.INTERNAL.withDescription("Invalid protobuf byte sequence")
                            .withCause(e)
                            .asRuntimeException()
                } catch (e: IOException) {
                    // Same for now, might be unavailable
                    throw Status.INTERNAL.withDescription("Invalid protobuf byte sequence")
                            .withCause(e)
                            .asRuntimeException()
                }
                return proto
            }
        }
    }

    private fun asString(stream: InputStream): String {
        return try {
            IOUtils.toString(stream, "UTF-8")
        } catch (e: IOException) {
            throw RuntimeException("Error converting stream to string", e)
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(GrpcHttpChannelKotlin::class.java)
    }

}
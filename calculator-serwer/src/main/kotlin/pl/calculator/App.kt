package pl.calculator



import io.vertx.core.Vertx
import io.vertx.core.json.Json
import io.vertx.ext.web.Router
import io.vertx.ext.web.Session
import io.vertx.ext.web.handler.BodyHandler
import org.slf4j.LoggerFactory
import pl.calculator.api.Calculator
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.io.File


fun main(args: Array<String>) {
    val vertx = Vertx.vertx()
    val port = 8000
    val router = Router.router(vertx)
    val calculator = Calculator("new")
    val log = LoggerFactory.getLogger("App.kt")
    //hierarchicznie jest
    router.get("/calc").handler{
        calculator.processData(it.request().getParam("op"))
        println(calculator.result)
        it.response().end()
    }
    router.get("/result").handler{
        it.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(calculator.result));
    }
    router.get("/getplugins").handler {
        it.response()
                .setStatusCode(200)
                .putHeader("content-type","appliaction/json; charset=utf-8")
                .end(Json.encodePrettily(calculator.pluginListString))
    }
    router.post("/form").handler(BodyHandler.create().setMergeFormAttributes(true).setUploadsDirectory("plugins"))
    router.post("/form")
            .handler { routingContext ->
                log.info("Mamy nowy plik")
                val fileUploadSet = routingContext.fileUploads()
                val fileUploadIterator = fileUploadSet.iterator()
                while (fileUploadIterator.hasNext()) {
                    val fileUpload = fileUploadIterator.next()
                    println(fileUpload.uploadedFileName())
                    // To get the uploaded file do
                    val uploadedFile = vertx.fileSystem().readFileBlocking(fileUpload.uploadedFileName())
                    //println(uploadedFile)
                    // Uploaded File Name

                    try {
                        val fileName = URLDecoder.decode(fileUpload.fileName(), "UTF-8")
                        println(fileName)
                        File(fileUpload.uploadedFileName()).renameTo(File("plugins/new/"+fileName))
                        calculator.updateMods();
                        //File(fileUpload.uploadedFileName()).delete()

                    } catch (e: UnsupportedEncodingException) {
                        e.printStackTrace()
                    }

                    // Use the Event Bus to dispatch the file now
                    // Since Event Bus does not support POJOs by default so we need to create a MessageCodec implementation
                    // and provide methods for encode and decode the bytes
                }

                routingContext.reroute("/")
            }

    router.route("/").handler{
        it.response().sendFile("index.html")
    }



    val server = vertx.createHttpServer()
    server.requestHandler { router.accept(it) }.listen(port) {
        if (it.succeeded()) println("Server listening at $port")
        else println(it.cause())
    }

}


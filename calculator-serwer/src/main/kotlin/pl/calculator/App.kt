package pl.calculator



import io.vertx.core.Vertx
import io.vertx.core.json.Json
import io.vertx.ext.web.Router
import io.vertx.ext.web.Session
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import org.slf4j.LoggerFactory
import pl.calculator.api.Calculator
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.io.File
import io.vertx.ext.web.Router.router




fun main(args: Array<String>) {
    val vertx = Vertx.vertx()
    val port = 8000
    val router = Router.router(vertx)
    router.route().handler(CookieHandler.create())
    router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));


    val calculator = Calculator("new")
    val log = LoggerFactory.getLogger("App.kt")
    //hierarchicznie jest
    router.get("/calc").handler{
        println(it.request().getParam("op"))
        it.session().get<Calculator>("calc").processData(it.request().getParam("op"))
        println(it.session().get<Calculator>("calc").result)
        it.response().end()
    }
    router.get("/result").handler{
        it.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(it.session().get<Calculator>("calc").result));
    }
    router.get("/getplugins").handler {
        it.response()
                .setStatusCode(200)
                .putHeader("content-type","appliaction/json; charset=utf-8")
                .end(Json.encodePrettily(it.session().get<Calculator>("calc").pluginListString))
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
                        val name=routingContext.session().id()

                        File(fileUpload.uploadedFileName()).renameTo(File("plugins/"+name+"/"+fileName))
                        routingContext.session().get<Calculator>("calc").updateMods()
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
        val session = it.session()
        val cc=Calculator(it.session().id())
        session.put("calc",cc)
        it.response().sendFile("index.html")
    }

    router.route("/session").handler { routingContext ->

        val session = routingContext.session()

        var cnt: Int? = session.get<Int>("hitcount")
        cnt = (cnt ?: 0) + 1

        session.put("hitcount", cnt)

        routingContext.response().putHeader("content-type", "text/html")
                .end("<html><body><h1>Hitcount: $cnt</h1></body></html>")
    }

    val server = vertx.createHttpServer()
    server.requestHandler { router.accept(it) }.listen(port) {
        if (it.succeeded()) println("Server listening at $port")
        else println(it.cause())
    }

}


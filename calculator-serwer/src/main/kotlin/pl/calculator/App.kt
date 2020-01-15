package pl.calculator


import io.vertx.core.Vertx
import io.vertx.core.json.Json
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import org.slf4j.LoggerFactory
import pl.calculator.api.Calculator
import java.net.URLDecoder
import java.io.File
import java.io.UnsupportedEncodingException as UnsupportedEncodingException1


fun main(args: Array<String>) {
    val vertx = Vertx.vertx()
    val port = 8080
    val router = Router.router(vertx)
    router.route().handler(CookieHandler.create())
    router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));


    val log = LoggerFactory.getLogger("App.kt")

    router.get("/calc").handler {
        it.session().get<Calculator>("calc").processData(it.request().getParam("op"))
        it.response().end()
    }
    router.get("/result").handler {
        it.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(it.session().get<Calculator>("calc").result))
    }
    router.get("/getplugins").handler {
        it.response()
                .setStatusCode(200)
                .putHeader("content-type", "appliaction/json; charset=utf-8")
                .end(Json.encodePrettily(it.session().get<Calculator>("calc").pluginListStringJson))
    }
    router.get("/getmess").handler {
        it.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(it.session().get<Calculator>("calc").mess))
    }
    router.get("/gethistory").handler {
        it.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(it.session().get<Calculator>("calc").history))
    }

    router.post("/form").handler(BodyHandler.create().setMergeFormAttributes(true).setUploadsDirectory("plugins"))
    router.post("/form")
            .handler { routingContext ->
                log.info("Mamy nowy plik")
                val fileUploadSet = routingContext.fileUploads()
                //println(fileUploadSet);
                val fileUploadIterator = fileUploadSet.iterator()
                while (fileUploadIterator.hasNext()) {
                    val fileUpload = fileUploadIterator.next()
                    try {
                        val fileName = URLDecoder.decode(fileUpload.fileName(), "UTF-8")
                        val name = routingContext.session().id()
                        File(fileUpload.uploadedFileName()).renameTo(File("plugins/" + name + "/" + fileName))
                        routingContext.session().get<Calculator>("calc").updateMods()
                        //File(fileUpload.uploadedFileName()).delete()

                    } catch (e: UnsupportedEncodingException1) {
                        log.error("Error, Kotlin, wgrywanie pluginu: " + e.message + "\n Dokładny:" + e.printStackTrace())
                    }
                }

                routingContext.reroute("/")

            }

    router.route("/").handler {
        if (it.session().isEmpty) {
            val cc = Calculator(it.session().id())
            it.session().put("calc", cc)
        }

        it.response().sendFile("index.html")
    }


    val server = vertx.createHttpServer()
    server.requestHandler { router.accept(it) }.listen(port) {
        if (it.succeeded()) {
            println("Server listening at $port")
            log.info("Server listening at $port")
        } else {
            println("Serwer nie wstał bo się zmęczył: " + it.cause())
            log.error("Serwer nie wstał bo się zmęczył: " + it.cause())
        }
    }

}


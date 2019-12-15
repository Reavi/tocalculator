package pl.calculator


import io.vertx.core.Vertx
import io.vertx.ext.web.Router

fun main(args: Array<String>) {
    val vertx = Vertx.vertx()
    val port = 8000
    val router = Router.router(vertx)
    //hierarchicznie jest
    router.get("/calc").handler{
        it.response().end("Yey")
    }
    router.route().handler{
        it.response().sendFile("index.html")
    }
    router.get("/calc").handler{
        it.response().end("Yey")
    }


    val server = vertx.createHttpServer()
    server.requestHandler { router.accept(it) }.listen(port) {
        if (it.succeeded()) println("Server listening at $port")
        else println(it.cause())
    }
}

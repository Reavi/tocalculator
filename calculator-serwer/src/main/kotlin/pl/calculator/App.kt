package pl.calculator


import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import pl.calculator.api.Calculator

fun main(args: Array<String>) {
    val vertx = Vertx.vertx()
    val port = 8000
    val router = Router.router(vertx)
    val calculator = Calculator()
    //hierarchicznie jest
    router.get("/calc").handler{
        println(calculator.processData(it.request().getParam("op")))
        it.response().sendFile("index.html")
    }
    router.route().handler{
        it.response().sendFile("index.html")
    }



    val server = vertx.createHttpServer()
    server.requestHandler { router.accept(it) }.listen(port) {
        if (it.succeeded()) println("Server listening at $port")
        else println(it.cause())
    }
}

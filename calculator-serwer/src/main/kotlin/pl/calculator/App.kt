package pl.calculator



import io.vertx.core.Vertx
import io.vertx.core.json.Json
import io.vertx.ext.web.Router
import pl.calculator.api.Calculator

fun main(args: Array<String>) {
    val vertx = Vertx.vertx()
    val port = 8000
    val router = Router.router(vertx)
    val calculator = Calculator()
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
    router.route("/").handler{
        it.response().sendFile("index.html")
    }



    val server = vertx.createHttpServer()
    server.requestHandler { router.accept(it) }.listen(port) {
        if (it.succeeded()) println("Server listening at $port")
        else println(it.cause())
    }

}


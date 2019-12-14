package pl.calculator

import io.vertx.core.Vertx

import io.vertx.kotlin.core.http.HttpServerOptions

fun main(args: Array<String>) {

    val vertx= Vertx.vertx()
    val options = HttpServerOptions(
            maxWebsocketFrameSize = 1000000)
    vertx.createHttpServer(options).requestHandler({ request ->
        request.response().end("Hello world")
    }).listen(8080)

}


package dev.donam.practice.vertx

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler

class MockBigqueryVerticle : AbstractVerticle() {
    override fun start() {
        vertx.eventBus().consumer<JsonObject>("mockbigquery.address") { message ->
            // 받은 메시지를 처리하고 응답을 반환하는 로직
            val responseJson = JsonObject().put("result", "some_data")
            message.reply(responseJson)
        }
    }
}

object VertxEcho {

    @JvmStatic
    fun main(args: Array<String>) {
        val vertx = Vertx.vertx()

        val router = Router.router(vertx)
        router.route().handler(BodyHandler.create()) // BodyHandler를 사용해 요청 본문을 처리

        // POST 요청을 /api/v1/alive 경로에서 처리
        router.post("/api/v1/alive").handler { context ->
            println("POST")
            handlePostRequest(vertx, context)
        }

        router.get("/api/v1/alive").handler { context ->
            println("GET")
            handlePostRequest(vertx, context)
        }

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8080)

        vertx.deployVerticle(MockBigqueryVerticle())

    }

    private fun handlePostRequest(vertx:Vertx, context: RoutingContext) {
        val json = context.bodyAsJson

        //테스트를 위해 임시로 처리
        val sampleJson = JsonObject()
        sampleJson.put("key1", "value1")
        sampleJson.put("key2", "value2")

        vertx.eventBus().request<JsonObject>("mockbigquery.address", sampleJson) { reply ->
            if (reply.succeeded()) {
                val responseJson = reply.result().body()
                context.response()
                    .setStatusCode(201)
                    .putHeader("Content-Type", "application/json")
                    .end(responseJson.encodePrettily())
            } else {
                context.response().setStatusCode(500).end()
            }
        }
    }
}


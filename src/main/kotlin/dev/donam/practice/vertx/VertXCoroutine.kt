package dev.donam.practice.vertx

import io.vertx.core.DeploymentOptions
import io.vertx.core.ThreadingModel
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.kotlin.coroutines.CoroutineEventBusSupport
import io.vertx.kotlin.coroutines.CoroutineRouterSupport
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.coroutineEventBus
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.kotlin.Logging

class MockBigqueryVerticleCoroutine: CoroutineVerticle(), CoroutineEventBusSupport,Logging {
    override suspend fun start() {
        coroutineEventBus {

        }

//        vertx.eventBus().consumer<JsonObject>("mockbigquery.address") { message ->
//            // 받은 메시지를 처리하고 응답을 반환하는 로직
//            val responseJson = JsonObject().put("result", "some_data")
//            message.reply(responseJson)
//        }
    }
}

class HttpVerticleCoroutine: CoroutineVerticle(), CoroutineRouterSupport, Logging {

    override suspend fun start() {

        val router = Router.router(vertx)
        router.route().handler(BodyHandler.create())

        router.post("/api/v1/alive").handler { context ->
            println("POST")
            handlePostRequest(vertx, context)
        }

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8080)
    }

    private fun handlePostRequest(vertx:Vertx, context: RoutingContext) {
        val json = context.bodyAsJson

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

object VertxCoroutine {
    @JvmStatic
    fun main(args: Array<String>): Unit = runBlocking {
        val vertx = Vertx.vertx()

        vertx.deployVerticle(MockBigqueryVerticleCoroutine::class.java.name, DeploymentOptions().apply {
            threadingModel = ThreadingModel.WORKER
        })
        vertx.deployVerticle(HttpVerticleCoroutine::class.java.name)
    }
}


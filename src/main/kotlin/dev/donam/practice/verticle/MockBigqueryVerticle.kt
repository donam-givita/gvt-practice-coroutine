package dev.donam.practice.verticle

import io.vertx.core.eventbus.Message
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.coroutines.CoroutineEventBusSupport
import io.vertx.kotlin.coroutines.CoroutineVerticle
import org.apache.logging.log4j.kotlin.Logging

class MockBigqueryVerticle : CoroutineVerticle(), CoroutineEventBusSupport, Logging {
    override suspend fun start() {
        val bus = vertx.eventBus()
        bus.coConsumer("AliveMock"){
            postAliveMock(it)
        }
    }

    private fun postAliveMock(message: Message<JsonObject>) {
        println("== postAliveMock start ==")

        val convertRes = Json.decodeValue(message.body().toString(), AliveMockReq::class.java)
        logger.info(convertRes)

        val result = JsonObject(Json.encode(convertRes))
        message.reply(result)
    }
}

class AliveMockReq(
    val id: Long,
    val text: String
)

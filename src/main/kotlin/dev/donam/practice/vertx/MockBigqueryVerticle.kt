package dev.donam.practice.vertx

import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.coroutines.CoroutineEventBusSupport
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.coroutineEventBus
import kotlinx.coroutines.delay
import org.apache.logging.log4j.kotlin.Logging

class MockBigqueryVerticle: CoroutineVerticle(), Logging {
    companion object {
        val path = this::class.java.toString().lowercase()
    }

    override suspend fun start() {
        logger.info("start")

        coroutineEventBus {
            vertx.eventBus().coConsumer("${path}.createUser") {
                createUser(it)
            }
        }
    }

    private suspend fun createUser(message: Message<JsonObject>) {
        logger.info("coConsumer received: ${message.body()}")

        delay(100)

        message.reply(message.body())
    }
}

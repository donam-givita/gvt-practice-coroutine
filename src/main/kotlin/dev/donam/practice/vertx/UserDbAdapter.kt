package dev.donam.practice.vertx

import io.vertx.core.eventbus.Message
import io.vertx.kotlin.coroutines.CoroutineEventBusSupport
import io.vertx.kotlin.coroutines.CoroutineVerticle
import kotlinx.coroutines.delay
import org.apache.logging.log4j.kotlin.Logging

class UserDbAdapter: CoroutineVerticle(), CoroutineEventBusSupport, Logging {
    companion object {
        val path = this::class.java.toString().lowercase()
    }

    override suspend fun start() {
        logger.info("start")

        vertx.eventBus().coConsumer("${path}.selectUser") {
            selectUser(it)
        }
    }

    private suspend fun selectUser(message: Message<String>) {
        logger.info("coConsumer received: ${message.body()}")

        delay(100)

        val userId = message.body().toLong()

        message.reply("{\"userId\": \"${userId}\", \"name\": \"이민섭+${userId}\"}")
    }
}

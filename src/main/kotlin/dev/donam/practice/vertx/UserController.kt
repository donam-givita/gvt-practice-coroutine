package dev.donam.practice.vertx

import io.vertx.core.eventbus.Message
import io.vertx.kotlin.coroutines.CoroutineEventBusSupport
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.coAwait
import org.apache.logging.log4j.kotlin.Logging

class UserController: CoroutineVerticle(), CoroutineEventBusSupport, Logging {
    companion object {
        val path = this::class.java.toString().lowercase()
    }

    override suspend fun start() {
        logger.info("start")

        vertx.eventBus().coConsumer("${path}.getUser") {
            getUser(it)
        }
    }

    private suspend fun getUser(message: Message<String>) {
        logger.info("coConsumer received: ${message.body()}")

        val userId = message.body()
        val response =
            vertx
                .eventBus()
                .request<String>("${MockBigqueryVerticle.path}.selectUser", userId)
                .coAwait()

        message.reply(response.body())
    }
}

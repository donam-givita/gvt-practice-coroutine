package dev.donam.practice.vertx

import io.vertx.core.Vertx
import io.vertx.core.impl.logging.LoggerFactory
import io.vertx.kotlin.coroutines.coAwait
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.kotlin.Logging
import org.apache.logging.log4j.kotlin.logger

fun main() = runBlocking {
    val logger = logger()
    logger.info("DEPLOY START")

    val verticleId = Vertx.vertx().deployVerticle(ApplicationVerticle()).coAwait()

    logger.info("verticleId=$verticleId")
    logger.info("DEPLOY END")
}

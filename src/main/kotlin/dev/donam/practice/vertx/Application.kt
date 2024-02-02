package dev.donam.practice.vertx

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.vertx.core.DeploymentOptions
import io.vertx.core.ThreadingModel
import io.vertx.core.Vertx
import io.vertx.core.json.jackson.DatabindCodec
import io.vertx.core.json.jackson.JacksonCodec
import io.vertx.kotlin.coroutines.coAwait
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.kotlin.logger

fun main() = runBlocking {
    val logger = logger()
    logger.info("DEPLOY START")
    println("========== fun main: ${Thread.currentThread().name} ==========")

    val vertx = Vertx.vertx()

    DatabindCodec.mapper()
        .registerModule(JavaTimeModule())
        .registerKotlinModule()

    val verticles = mutableListOf(
        vertx.deployVerticle(HttpVerticle::class.java.name),
        vertx.deployVerticle(
            MockBigqueryVerticle::class.java.name,
            DeploymentOptions().setThreadingModel(ThreadingModel.WORKER)
        ),
        vertx.deployVerticle(
            DbUserVerticle(),
            DeploymentOptions().setThreadingModel(ThreadingModel.WORKER)
        )
    )

    val ids = verticles.map { it.coAwait() }

    logger.info("verticles=$ids")
    logger.info("DEPLOY END")
}

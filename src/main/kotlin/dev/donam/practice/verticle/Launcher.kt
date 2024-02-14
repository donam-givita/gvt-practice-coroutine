package dev.donam.practice.verticle

import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.vertx.core.ThreadingModel
import io.vertx.core.Vertx
import io.vertx.core.json.jackson.DatabindCodec
import io.vertx.kotlin.core.deploymentOptionsOf

fun main() {
    val vertx = Vertx.vertx()
    DatabindCodec.mapper()
        .registerKotlinModule()

    vertx.deployVerticle(HttpVerticle::class.java.name)
    vertx.deployVerticle(
        MockBigqueryVerticle::class.java.name,
        deploymentOptionsOf(threadingModel = ThreadingModel.WORKER)
    )
    vertx.deployVerticle(
        PostgresVerticle::class.java.name,
        deploymentOptionsOf(threadingModel = ThreadingModel.WORKER)
    )
}

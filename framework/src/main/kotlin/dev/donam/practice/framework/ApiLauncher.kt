package dev.donam.practice.framework

import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import dev.donam.practice.framework.config.createContainer
import dev.donam.practice.framework.verticle.HttpServerVerticle
import io.vertx.core.Vertx
import io.vertx.core.json.jackson.DatabindCodec

fun main() {
    val container = createContainer()

    val vertx = container.get<Vertx>()
    DatabindCodec.mapper().registerKotlinModule()

    vertx.deployVerticle(container.get<HttpServerVerticle>())
    println("start...\uD83D\uDE80")
}

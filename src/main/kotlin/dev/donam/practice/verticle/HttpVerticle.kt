package dev.donam.practice.verticle

import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.kotlin.coroutines.CoroutineRouterSupport
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.coAwait
import org.apache.logging.log4j.kotlin.Logging


class HttpVerticle: CoroutineVerticle(), CoroutineRouterSupport, Logging {
    override suspend fun start() {
        logger.info( "Http Verticle Start: ${Thread.currentThread().name}" )
        vertx.createHttpServer()
            .requestHandler(router())
            .listen(8085)
            .coAwait()
    }

    private fun router(): Router = Router.router(vertx).apply {
        post("/api/v1/alive").coRespond {
            val req = it.request().body().coAwait()
            println( "HttpVerticle req: $req" )

            val eventBusRes = vertx.eventBus().request<JsonObject>("AliveMock", req.toJsonObject()).coAwait()
            println( "Received back: ${eventBusRes.body()}" )

            it.response().putHeader("contentType", "application/json").setStatusCode(201)
            it.response().end(eventBusRes.body().toBuffer())
        }

        get("/api/v1/postgres/:id").coRespond {
            val getEventBusRes = vertx.eventBus().request<JsonObject>("getPostgres", it.pathParam("id")).coAwait()
            it.response()
                .putHeader("content-type", "application/json;")
                .setStatusCode(200)
                .end(getEventBusRes.body().toString())
        }

        post("/api/v1/postgres").coRespond {
            val req = it.request().body().coAwait()

            val eventBusRes = vertx.eventBus().request<JsonObject>("insertPostgres", req.toJsonObject()).coAwait()
            println( "Received back: ${eventBusRes.body()}" )

            it.response()
                .putHeader("contentType", "application/json")
                .setStatusCode(201)
                .end(eventBusRes.body().toBuffer())
        }

        coErrorHandler(404) { rc ->
            logger.info { "HttpVerticle 404: ${Thread.currentThread().name}" }
            rc.response().setStatusCode(404).end()
        }
    }
}


package dev.donam.practice.vertx

import io.netty.handler.codec.http.HttpResponseStatus
import io.vertx.core.eventbus.ReplyException
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.coAwait
import io.vertx.kotlin.coroutines.coroutineRouter
import org.apache.logging.log4j.kotlin.Logging


class HttpVerticle: CoroutineVerticle(), Logging {
    override suspend fun start() {
        logger.info("start")

        vertx
            .createHttpServer()
            .requestHandler(createRouter())
            .listen(8089)
            .coAwait()
    }

    private suspend fun createRouter() = Router.router(vertx).apply {
        coroutineRouter {
            post("/api/v1/alive").coHandler { context ->
                logger.info("router alive: ${Thread.currentThread().name} ==========")

                val requestBody = context.request().body().coAwait()
                val result =
                    vertx
                        .eventBus()
                        .request<JsonObject>(
                            "${MockBigqueryVerticle.path}.createUser",
                            requestBody.toJsonObject()
                        )
                        .coAwait()

                context
                    .response()
                    .putHeader("content-type", "application/json;")
                    .setStatusCode(201)
                    .end(result.body().toString())
            }
            get("/api/v1/user/:userId").coHandler { context ->
                logger.info("router getUser: ${Thread.currentThread().name} ==========")

                try {
                    val result =
                        vertx
                            .eventBus()
                            .request<String>(
                                "${DbUserVerticle.path}.getUser",
                                context.pathParam("userId")
                            )
                            .coAwait()

                    context
                        .response()
                        .putHeader("content-type", "application/json;")
                        .setStatusCode(HttpResponseStatus.OK.code())
                        .end(result.body().toString())
                } catch (e: ReplyException) {
                    context
                        .response()
                        .setStatusCode(e.failureCode())
                        .end(e.message)
                }
            }
            post("/api/v1/user").coHandler { context ->
                logger.info("router postUser: ${Thread.currentThread().name} ==========")

                try {
                    val requestBody = context.request().body().coAwait()
                    val result =
                        vertx
                            .eventBus()
                            .request<JsonObject>(
                                "${DbUserVerticle.path}.createUser",
                                requestBody.toJsonObject()
                            )
                            .coAwait()

                    context
                        .response()
                        .putHeader("content-type", "application/json;")
                        .setStatusCode(HttpResponseStatus.CREATED.code())
                        .end(result.body().toString())
                } catch (e: ReplyException) {
                    context
                        .response()
                        .setStatusCode(e.failureCode())
                        .end(e.message)
                }
            }
            delete("/api/v1/user/:userId").coHandler { context ->
                logger.info("router deleteUser: ${Thread.currentThread().name} ==========")

                try {
                    val result =
                        vertx
                            .eventBus()
                            .request<String>(
                                "${DbUserVerticle.path}.deleteUser",
                                context.pathParam("userId")
                            )
                            .coAwait()

                    context
                        .response()
                        .putHeader("content-type", "application/json;")
                        .setStatusCode(HttpResponseStatus.NO_CONTENT.code())
                        .end(result.body().toString())
                } catch (e: ReplyException) {
                    context
                        .response()
                        .setStatusCode(e.failureCode())
                        .end(e.message)
                }
            }
        }
    }
}

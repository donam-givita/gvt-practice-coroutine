package dev.donam.practice.vertx

import io.vertx.ext.web.Router
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.coAwait
import io.vertx.kotlin.coroutines.coroutineRouter
import kotlinx.coroutines.async
import org.apache.logging.log4j.kotlin.Logging


class ApplicationVerticle: CoroutineVerticle(), Logging {
    private val verticleIds = mutableListOf<String>()

    override suspend fun start() {
        logger.info("start")

        verticleIds.add(vertx.deployVerticle(UserController()).coAwait())
        verticleIds.add(vertx.deployVerticle(UserDbAdapter()).coAwait())

        val router = async { createRouter() }.await()

        vertx
            .createHttpServer()
            .requestHandler(router)
            .listen(8089)
            .coAwait()
    }

    override suspend fun stop() {
        println("stop")

        verticleIds.forEach {
            vertx.undeploy(it)
        }
    }

    private suspend fun createRouter(): Router {
        val router = Router.router(vertx)

        coroutineRouter {
            router.get("/user/:userId").coRespond { context ->
                context
                    .response()
                    .putHeader("content-type", "application/json; charset=utf-8;")

                val userId = context.pathParam("userId")

                val response =
                    vertx
                        .eventBus()
                        .request<String>("${UserController.path}.getUser", userId)
                        .coAwait()

                "${response.body()}"
            }
        }

        return router
    }
}

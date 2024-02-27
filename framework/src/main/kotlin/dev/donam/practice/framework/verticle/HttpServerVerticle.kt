package dev.donam.practice.framework.verticle

import dev.donam.practice.api.UserController
import io.vertx.ext.web.Router
import io.vertx.kotlin.coroutines.CoroutineRouterSupport
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.coAwait
import org.apache.logging.log4j.kotlin.Logging

class HttpServerVerticle(
    private val userController: UserController
): CoroutineVerticle(), CoroutineRouterSupport, Logging {
    override suspend fun start() {
        println( "Http Server Verticle Start: ${Thread.currentThread().name}" )

        vertx.createHttpServer()
            .requestHandler(router())
            .listen(8085)
            .coAwait()
    }

    private suspend fun router(): Router = Router.router(vertx).apply {
        this.route(userController.path)
            .produces("application/json")
            .subRouter(userApiRouter())
    }

    private suspend fun userApiRouter(): Router = Router.router(vertx).apply {
        this.get("/:userId").coRespond {
            userController.getUser(it)
        }

        this.post().coRespond {
            userController.createUser(it)
        }

        coErrorHandler(404) { rc ->
            logger.info { "no search url 404: ${Thread.currentThread().name}" }
            rc.response().setStatusCode(404).end()
        }
    }
}

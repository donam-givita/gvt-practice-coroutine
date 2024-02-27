package dev.donam.practice.api

import dev.donam.practice.application.port.`in`.CreateUserReq
import  dev.donam.practice.application.port.`in`.UserUseCase
import io.vertx.core.json.Json
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.coroutines.coAwait
import org.apache.logging.log4j.kotlin.Logging

class UserController(
    private val userUseCase: UserUseCase,
): Logging {
    val path = "/api/v1/user/*"

    suspend fun getUser(rc: RoutingContext){
        val userId = rc.pathParam("userId").toLong()
        val user = userUseCase.getUser(userId)

        rc.response()
            .putHeader("contentType", "application/json")
            .setStatusCode(200)
            .end(Json.encode(user))
    }
    suspend fun createUser(rc: RoutingContext){
        val req = Json.decodeValue(rc.request().body().coAwait(), CreateUserReq::class.java)

        val user = userUseCase.createUser(req)

        rc.response()
            .putHeader("contentType", "application/json")
            .setStatusCode(201)
            .end(Json.encode(user))
    }
}

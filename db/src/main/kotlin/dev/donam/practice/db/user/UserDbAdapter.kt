package dev.donam.practice.db.user

import dev.donam.practice.application.port.`in`.CreateUserReq
import dev.donam.practice.application.port.`in`.User
import dev.donam.practice.application.port.out.UserDbPort
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.pgclient.PgBuilder
import io.vertx.pgclient.PgConnectOptions
import io.vertx.sqlclient.PoolOptions
import io.vertx.sqlclient.SqlClient
import io.vertx.sqlclient.Tuple

class UserDbAdapter(): UserDbPort {

    private val connectOptions: PgConnectOptions = PgConnectOptions()
        .setPort(5432)
        .setHost("localhost")
        .setDatabase("vertx_test")
        .setUser("postgres")
        .setPassword("pgsql")

    private val client: SqlClient = PgBuilder
        .client()
        .with(PoolOptions().setMaxSize(4))
        .connectingTo(connectOptions)
        .build()

    override suspend fun getUser(userId: Long): User {
        var userJson = ""
        client.preparedQuery("SELECT * FROM user WHERE id = $1")
            .execute(Tuple.of(userId))
            .onComplete {
                if(it.result().size() == 0) throw RuntimeException("no insert result")
                userJson = it.result().first().toString()
            }

        client.close()
        return Json.decodeValue(userJson, User::class.java)
    }

    override suspend fun createUser(req: CreateUserReq): User {
        var userJson = ""
        client.preparedQuery("INSERT INTO user VALUES ($1, $2)")
            .execute(Tuple.of(req.id, req.name))
            .onComplete {
                if(it.result().size() == 0) throw RuntimeException("no insert result")
                userJson = it.result().first().toString()
            }
        client.close()
        return Json.decodeValue(userJson, User::class.java)
    }

}

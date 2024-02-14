package dev.donam.practice.verticle

import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.coroutines.CoroutineEventBusSupport
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.pgclient.PgBuilder
import io.vertx.pgclient.PgConnectOptions
import io.vertx.sqlclient.PoolOptions
import io.vertx.sqlclient.SqlClient
import io.vertx.sqlclient.Tuple
import org.apache.logging.log4j.kotlin.Logging


class PostgresVerticle : CoroutineVerticle(), CoroutineEventBusSupport, Logging {
    override suspend fun start() {
        logger.info("Postgres Verticle Start: ${Thread.currentThread().name}")

        val connectOptions = PgConnectOptions()
            .setPort(5432)
            .setHost("localhost")
            .setDatabase("vertx_test")
            .setUser("postgres")
            .setPassword("pgsql")

        val client = PgBuilder
            .client()
            .with(PoolOptions().setMaxSize(4))
            .connectingTo(connectOptions)
            .using(vertx)
            .build()

        val bus = vertx.eventBus()
        bus.coConsumer("getPostgres"){
            getPostgres(it, client)
        }
        bus.coConsumer("insertPostgres"){
            insertPostgres(it, client)
        }
    }

    private fun getPostgres(message: Message<String>, client: SqlClient) {
        try {
            client.preparedQuery("SELECT * FROM insert_test WHERE id = $1")
                .execute(Tuple.of(message.body().toInt()))
                .onComplete { ar ->
                    val result = ar.result()

                    result.map {
                        message.reply(it.toJson())
                    }

                    client.close()
                }
        } catch (e: Exception) {
            logger.error("에러남")
            throw RuntimeException(e)
        }
    }

    private fun insertPostgres(message: Message<JsonObject>, client: SqlClient) {
        try {
            val json = message.body()

            client.preparedQuery("INSERT INTO insert_test VALUES ($1, $2)")
                .execute(Tuple.of(json.getInteger("id"), json.getValue("text")))
                .onComplete { ar ->
                    val result = ar.result()

                    message.reply(JsonObject().put("row", result.rowCount()))

                    client.close()
                }

        } catch (e: Exception) {
            logger.error("에러남")
            throw RuntimeException(e)
        }
    }
}


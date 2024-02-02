package dev.donam.practice.vertx

import io.netty.handler.codec.http.HttpResponseStatus
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.coroutines.coAwait
import io.vertx.kotlin.coroutines.coroutineEventBus
import io.vertx.sqlclient.Tuple
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

class DbUserVerticle: AbstractPostgreSqlVerticle() {
    companion object {
        val path = this::class.java.toString().lowercase()
    }

    override suspend fun start() {
        logger.info("start")

        coroutineEventBus {
            val eventBus = vertx.eventBus()
            eventBus.coConsumer("${path}.getUser") {
                logger.info("coConsumer getUser: ${Thread.currentThread().name} ==========")

                getUser(it)
            }

            eventBus.coConsumer("${path}.createUser") {
                logger.info("coConsumer createUser: ${Thread.currentThread().name} ==========")

                createUser(it)
            }

            eventBus.coConsumer("${path}.deleteUser") {
                logger.info("coConsumer deleteUser: ${Thread.currentThread().name} ==========")

                deleteUser(it)
            }
        }
    }

    private suspend fun getUser(message: Message<String>) {
        val client = pgClient()

        try {
            val result = client
                .query("""
                SELECT id
                     , email
                     , name
                  FROM public.user
                 WHERE id = ${message.body()}
                """.trimIndent())
                .execute()
                .coAwait()

            logger.info("fun getUser: ${Thread.currentThread().name} ==========")

            if (result.rowCount() == 1) {
                message.reply(
                    jsonMapper.writeValueAsString(
                        result.map {
                            UserEntity(
                                it.getLong(0),
                                it.getString(1),
                                it.getString(2)
                            )
                        }.first()
                    )
                )
            } else {
                message.fail(HttpResponseStatus.NOT_FOUND.code(), "not found user")
            }
        } catch (e: Exception) {
            e.printStackTrace()

            message.fail(HttpResponseStatus.INTERNAL_SERVER_ERROR.code(), e.message)
        } finally {
            client.close()
        }
    }

    private suspend fun createUser(message: Message<JsonObject>) {
        val client = pgClient()

        try {
            val jObj = message.body()

            val result = client
                .preparedQuery("""
                INSERT INTO public.user (
                      email
                    , name
                    , created_at
                    , updated_at
                )
                VALUES (
                      $1
                    , $2
                    , $3
                    , $4
                )
                """.trimIndent())
                .execute(
                    Tuple.of(
                        jObj.getString("email"),
                        jObj.getString("name"),
                        ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toOffsetDateTime(),
                        ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toOffsetDateTime(),
                    )
                )
                .coAwait()

            logger.info("fun createUser: ${Thread.currentThread().name} ==========")

            if (result.rowCount() == 1) {
                message.reply(jObj)
            } else {
                message.fail(HttpResponseStatus.INTERNAL_SERVER_ERROR.code(), "insert fail")
            }
        } catch (e: Exception) {
            e.printStackTrace()

            message.fail(HttpResponseStatus.INTERNAL_SERVER_ERROR.code(), e.message)
        } finally {
            client.close()
        }
    }

    private suspend fun deleteUser(message: Message<String>) {
        val client = pgClient()

        try {
            val result = client
                .query("""
                DELETE FROM public.user WHERE id = ${message.body()} RETURNING id
                """.trimIndent())
                .execute()
                .coAwait()

            logger.info("fun deleteUser: ${Thread.currentThread().name} ==========")

            if (result.rowCount() == 1) {
                val deleteId = result.map {
                    it.getLong(0)
                }.first().toString()

                message.reply(deleteId)
            } else {
                message.fail(HttpResponseStatus.INTERNAL_SERVER_ERROR.code(), "delete fail")
            }
        } catch (e: Exception) {
            e.printStackTrace()

            message.fail(HttpResponseStatus.INTERNAL_SERVER_ERROR.code(), e.message)
        } finally {
            client.close()
        }
    }
}

class UserEntity(
    var id: Long,
    var email: String,
    var name: String
)

package dev.donam.practice.vertx

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.vertx.kotlin.coroutines.CoroutineEventBusSupport
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.pgclient.PgBuilder
import io.vertx.pgclient.PgConnectOptions
import io.vertx.sqlclient.PoolOptions
import org.apache.logging.log4j.kotlin.Logging

abstract class AbstractPostgreSqlVerticle: CoroutineVerticle(), CoroutineEventBusSupport, Logging {
    private val connectOptions = PgConnectOptions()
        .setPort(5432)
        .setHost("localhost")
        .setDatabase("vertx_user")
        .setUser("postgres")
        .setPassword("pgsql")

    private val poolOptions: PoolOptions = PoolOptions().setMaxSize(5)

    protected fun pgClient() = PgBuilder
            .client()
            .with(poolOptions)
            .connectingTo(connectOptions)
            .using(vertx)
            .build()

    protected val jsonMapper =
        jacksonObjectMapper()
            .registerModule(JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
}

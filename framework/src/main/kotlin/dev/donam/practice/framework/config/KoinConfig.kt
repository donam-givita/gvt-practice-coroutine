package dev.donam.practice.framework.config

import dev.donam.practice.framework.verticle.HttpServerVerticle
import io.vertx.core.Vertx
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.dsl.module
import dev.donam.practice.api.UserController
import dev.donam.practice.application.port.`in`.UserUseCase
import dev.donam.practice.application.port.out.UserDbPort
import dev.donam.practice.application.service.UserService
import dev.donam.practice.db.user.UserDbAdapter

fun createContainer(): Koin = startKoin {
    modules(
        module {
            single { Vertx.vertx() }
            factory { HttpServerVerticle(get()) }
        }, userControllerModule()
    )
}.koin

fun userControllerModule() = module(createdAtStart = true) {
    single{ UserController(get()) }
    single<UserUseCase> { UserService(get()) }
    single<UserDbPort> { UserDbAdapter() }
}

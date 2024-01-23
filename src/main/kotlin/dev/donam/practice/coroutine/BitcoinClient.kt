package dev.donam.practice.coroutine

import kotlinx.coroutines.delay
import kotlin.random.Random

data class Bitcoin(val exchangeId: Int, val price: Int)

suspend fun getBitcoin(): Bitcoin {
    println("[${Thread.currentThread().name}] query an exchange")
    delay(Random.nextLong(80, 120))
    val bitcoin = Bitcoin(
        Random.nextInt(1, 100),
        Random.nextInt(50000, 60000)
    )
    println("[${Thread.currentThread().name}] get a $bitcoin")

    return bitcoin
}

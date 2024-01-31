package dev.donam.practice.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("[${Thread.currentThread().name}] start")

    val quotation1 = async(Dispatchers.IO) { getBitcoin() }
    val quotation2 = async(Dispatchers.IO) { getBitcoin() }
    val quotation3 = async(Dispatchers.IO) { getBitcoin() }

//    val quotations = listOf<Any>() // TODO: wait all quotations
    val quotations = awaitAll(quotation1, quotation2, quotation3)
    println("[${Thread.currentThread().name}] got ${quotations.size} quotation(s)")

//    val bestPrice = Any() // TODO: get lower price of bitcoin
    val bestPrice = quotations.minBy { it.price }
    println("[${Thread.currentThread().name}] the best quotation is $bestPrice")
    println("[${Thread.currentThread().name}] end")
}

package dev.donam.practice.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("[${Thread.currentThread().name}] start")

    val quotation1 = async(Dispatchers.IO) { getBitcoin() }
    val quotation2 = async(Dispatchers.IO) { getBitcoin() }
    val quotation3 = async(Dispatchers.IO) { getBitcoin() }


    val quotations = listOf<Any>() // TODO: wait all quotations
    println("[${Thread.currentThread().name}] got ${quotations.size} quotation(s)")

    val bestPrice = Any() // TODO: get lower price of bitcoin
    println("[${Thread.currentThread().name}] the best quotation is $bestPrice")
    println("[${Thread.currentThread().name}] end")
}

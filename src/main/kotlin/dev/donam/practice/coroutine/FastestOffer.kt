package dev.donam.practice.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

fun main() = runBlocking {
    println("[${Thread.currentThread().name}] start")

    val quotation1 = async(Dispatchers.IO) { getBitcoin() }
    val quotation2 = async(Dispatchers.IO) { getBitcoin() }
    val quotation3 = async(Dispatchers.IO) { getBitcoin() }

    val offers = select<Bitcoin> {
        listOf(quotation1, quotation2, quotation3).forEach {defferred ->
            defferred.onAwait{it}
        }
    }

    println("[${Thread.currentThread().name}] the fastest offer is $offers")
    println("[${Thread.currentThread().name}] end")
}

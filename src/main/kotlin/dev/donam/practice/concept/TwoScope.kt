package dev.donam.practice.concept

import dev.donam.practice.concept.Breakfast.applyButter
import dev.donam.practice.concept.Breakfast.applyJam
import kotlinx.coroutines.selects.select
import kotlin.system.measureTimeMillis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

suspend fun main() = runBlocking {
    val time = measureTimeMillis {

        val deferredFist = CoroutineScope(Dispatchers.IO).async {
            val time = measureTimeMillis {
                println("========== fun main: ${Thread.currentThread().name} ==========")
                val cupOfCoffee: Deferred<Coffee> = async(Dispatchers.IO) { BreakfastAsync.pourCoffee() }
                println("> coffee is ready: $cupOfCoffee")

                val eggs: Deferred<Egg> = async(Dispatchers.IO) { BreakfastAsync.fryEggs(2) }
                println("> eggs are ready: $eggs")

                awaitAll(cupOfCoffee, eggs)
            }

            println("> BlockingBreakfast is ready! deferredFist")
            println("\n\n>>> deferredFist took ${time}ms\n\n")

            time
        }

        val deferredSecond = CoroutineScope(Dispatchers.IO).async {
            val time = measureTimeMillis {
                val bacon: Deferred<Bacon> = async(Dispatchers.IO) { BreakfastAsync.fryBacon(3) }
                println("> bacon is ready: $bacon")

                val toast: Deferred<Toast> = async(Dispatchers.IO) { BreakfastAsync.toastBread(2)
                    .applyButter()
                    .applyJam()}
                println("> toast is ready: $toast")

                val cupOfOrangeJuice: Deferred<Juice> = async(Dispatchers.IO) { BreakfastAsync.pourOrangeJuice() }
                println("> orange juice is ready: $cupOfOrangeJuice")
            }
            println("> BlockingBreakfast is ready! deferredSecond")
            println("\n\n>>> deferredSecond took ${time}ms\n\n")
        }

        val result = select {
            deferredFist.onAwait{}
            deferredSecond.onAwait{}
        }

        println("result > ${result}")
    }
    println(">two scope async is ready!")
    println("\n\n>>> took ${time}ms\n\n")
}

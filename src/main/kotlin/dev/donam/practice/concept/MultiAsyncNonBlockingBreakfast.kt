package dev.donam.practice.concept

import dev.donam.practice.concept.BreakfastAsync.applyButter
import dev.donam.practice.concept.BreakfastAsync.applyJam
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val time = measureTimeMillis {
        println("========== fun main: ${Thread.currentThread().name} ==========")

        val deferred1 = CoroutineScope(Dispatchers.IO).async {
            val deferredTime = measureTimeMillis {
                val coffeeDeferredItem = async { BreakfastAsync.pourCoffee() }
                val eggsDeferred = async { BreakfastAsync.fryEggs(2) }
                val baconDeferred = async { BreakfastAsync.fryBacon(3) }

                val response = awaitAll(coffeeDeferredItem, eggsDeferred, baconDeferred)

                println("> coffee is ready: ${response[0]}")
                println("> eggs are ready: ${response[1]}")
                println("> bacon is ready: ${response[2]}")
            }

            deferredTime
        }

        val deferred2 = CoroutineScope(Dispatchers.IO).async {
            val deferredTime = measureTimeMillis {
                val toastDeferred = async {
                    BreakfastAsync.toastBread(2)
                        .applyButter()
                        .applyJam()
                }

                val cupOfOrangeJuiceDeferred = async { BreakfastAsync.pourOrangeJuice() }

                val response = awaitAll(toastDeferred, cupOfOrangeJuiceDeferred)

                println("> toast is ready: ${response[0]}")
                println("> orange juice is ready: ${response[1]}")
            }

            deferredTime
        }

        val response = select {
            deferred1.onAwait { "win deferred1 took ${it}ms" }
            deferred2.onAwait { "win deferred2 took ${it}ms " }
        }.also {
            coroutineContext.cancelChildren()
        }
        println("Select: $response")
    }

    println("> MultiAsyncNonBlockingBreakfast is ready!")
    println("\n\n>>> took ${time}ms\n\n")
}

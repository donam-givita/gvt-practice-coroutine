package dev.donam.practice.concept

import dev.donam.practice.concept.BreakfastAsync.applyButter
import dev.donam.practice.concept.BreakfastAsync.applyJam
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val time = measureTimeMillis {
        println("========== fun main: ${Thread.currentThread().name} ==========")

        Executors.newFixedThreadPool(2).asCoroutineDispatcher().use { dispatcher ->
            withContext(dispatcher) {
                // pourCoffee
                async(coroutineContext) {
                    val cupOfCoffee: Coffee = BreakfastAsync.pourCoffee()
                    println("> coffee is ready: $cupOfCoffee")
                }

                // fryEggs
                async(coroutineContext) {
                    val eggs: Egg = BreakfastAsync.fryEggs(2)
                    println("> eggs are ready: $eggs")
                }

                // fryBacon
                async(coroutineContext) {
                    val bacon: Bacon = BreakfastAsync.fryBacon(3)
                    println("> bacon is ready: $bacon")
                }

                // toastBread
                // applyButter
                // applyJam
                async {
                    val toast: Toast = BreakfastAsync.toastBread(2)
                        .applyButter()
                        .applyJam()
                    println("> toast is ready: $toast")
                }

                // pourOrangeJuice
                async {
                    val cupOfOrangeJuice: Juice = BreakfastAsync.pourOrangeJuice()
                    println("> orange juice is ready: $cupOfOrangeJuice")
                }
            }
        }
    }
    println("> MultiAsyncNonBlockingBreakfast is ready!")
    println("\n\n>>> took ${time}ms\n\n")
}

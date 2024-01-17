package dev.donam.practice.concept

import dev.donam.practice.concept.BreakfastAsync.applyButterAsync
import dev.donam.practice.concept.BreakfastAsync.applyJamAsync
import dev.donam.practice.concept.BreakfastAsync.fryBacon
import dev.donam.practice.concept.BreakfastAsync.fryEggs
import dev.donam.practice.concept.BreakfastAsync.pourCoffee
import dev.donam.practice.concept.BreakfastAsync.pourOrangeJuice
import dev.donam.practice.concept.BreakfastAsync.toastBread
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val time = measureTimeMillis {
        println("========== fun main: ${Thread.currentThread().name} ==========")

        val cupOfCoffee: Job = launch { pourCoffee() }
        println("> coffee is ready: $cupOfCoffee")

        val eggs: Job = launch { fryEggs(2) }
        println("> eggs are ready: $eggs")

        val bacon: Job = launch { fryBacon(3) }
        println("> bacon is ready: $bacon")

        val toast: Job = launch { toastBread(2)
                                    .applyButterAsync()
                                    .applyJamAsync() }
        println("> toast is ready: $toast")

        val cupOfOrangeJuice: Job = launch { pourOrangeJuice() }
        println("> orange juice is ready: $cupOfOrangeJuice")

        cupOfCoffee.join()
        eggs.join()
        bacon.join()
        toast.join()
        cupOfOrangeJuice.join()
    }
    println("> single nonBlockingBreakfast is ready!")
    println("\n\n>>> took ${time}ms\n\n")
}


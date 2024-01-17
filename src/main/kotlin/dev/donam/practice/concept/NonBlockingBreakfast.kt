package dev.donam.practice.concept

import dev.donam.practice.concept.Breakfast.applyButter
import dev.donam.practice.concept.Breakfast.applyJam
import dev.donam.practice.concept.Breakfast.fryBacon
import dev.donam.practice.concept.Breakfast.fryEggs
import dev.donam.practice.concept.Breakfast.pourCoffee
import dev.donam.practice.concept.Breakfast.pourOrangeJuice
import dev.donam.practice.concept.Breakfast.toastBread
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
                                    .applyButter()
                                    .applyJam() }
        println("> toast is ready: $toast")

        val cupOfOrangeJuice: Job = launch { pourOrangeJuice() }
        println("> orange juice is ready: $cupOfOrangeJuice")

        cupOfCoffee.join()
        eggs.join()
        bacon.join()
        toast.join()
        cupOfOrangeJuice.join()
    }
    println("> NonBlockingBreakfast is ready!")
    println("\n\n>>> took ${time}ms\n\n")
}

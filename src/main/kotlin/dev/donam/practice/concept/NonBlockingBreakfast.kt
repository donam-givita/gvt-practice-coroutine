package dev.donam.practice.concept

import dev.donam.practice.concept.Breakfast.applyButter
import dev.donam.practice.concept.Breakfast.applyJam
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val time = measureTimeMillis {
        println("========== fun main: ${Thread.currentThread().name} ==========")
        val cupOfCoffee: Deferred<Coffee> = async(Dispatchers.IO){ BreakfastAsync.pourCoffee() }
        println("> coffee is ready: $cupOfCoffee")

        val eggs: Deferred<Egg> = async(Dispatchers.IO){ BreakfastAsync.fryEggs(2) }
        println("> eggs are ready: $eggs")

        val bacon: Deferred<Bacon> = async(Dispatchers.IO){ BreakfastAsync.fryBacon(3) }
        println("> bacon is ready: $bacon")

        val toast: Deferred<Toast> = async(Dispatchers.IO){ BreakfastAsync.toastBread(2)
            .applyButter()
            .applyJam()
        }
        println("> toast is ready: $toast")

        val cupOfOrangeJuice: Deferred<Juice> = async(Dispatchers.IO){ BreakfastAsync.pourOrangeJuice() }
        println("> orange juice is ready: $cupOfOrangeJuice")

        awaitAll(cupOfCoffee, eggs, bacon, toast, cupOfOrangeJuice)

    }
    println("> NonBlockingBreakfast is ready!")
    println("\n\n>>> took ${time}ms\n\n")
}


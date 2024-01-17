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

suspend fun main() = runBlocking {
    val time = measureTimeMillis {
        println("========== fun main: ${Thread.currentThread().name} ==========")

        val cupOfCoffee: Job = launch(Dispatchers.Default) { pourCoffee() }
        println("> coffee is ready: $cupOfCoffee")

        val eggs: Job = launch(Dispatchers.Default) { fryEggs(2) }
        println("> eggs is ready: $eggs")

        val bacon: Job = launch(Dispatchers.Default) { fryBacon(3) }
        println("> bacon is ready: $bacon")

        val toast: Job = launch(Dispatchers.Default) { toastBread(2).applyButterAsync().applyJamAsync() }
        println("> toast is ready: $toast")

        val cupOfOrangeJuice: Job = launch(Dispatchers.Default) { pourOrangeJuice() }
        println("> orange juice is ready: $cupOfOrangeJuice")

        cupOfCoffee.join()
        eggs.join()
        bacon.join()
        toast.join()
        cupOfOrangeJuice.join()
    }
    println("> multi Thread non-BlockingBreakfast is ready!")
    println("\n\n>>> took ${time}ms\n\n")
}


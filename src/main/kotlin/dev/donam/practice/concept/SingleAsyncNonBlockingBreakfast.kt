package dev.donam.practice.concept

import dev.donam.practice.concept.BreakfastAsync.applyButter
import dev.donam.practice.concept.BreakfastAsync.applyJam
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

suspend fun main() = runBlocking {
    val time = measureTimeMillis {
        println("========== fun main: ${Thread.currentThread().name} ==========")

        // pourCoffee
        val pourCoffeeDeferred = async {
            val cupOfCoffee: Coffee = BreakfastAsync.pourCoffee()
            println("> coffee is ready: $cupOfCoffee")
        }
        println("# pourCoffee async")

        // fryEggs
        val eggsDeferred = async {
            val eggs: Egg = BreakfastAsync.fryEggs(2)
            println("> eggs are ready: $eggs")
        }
        println("# eggsDeferred async")

        // fryBacon
        val baconDeferred = async {
            val bacon: Bacon = BreakfastAsync.fryBacon(3)
            println("> bacon is ready: $bacon")
        }
        println("# baconDeferred async")

        // toastBread
        // applyButter
        // applyJam
        val toastDeferred = async {
            val toast: Toast = BreakfastAsync.toastBread(2)
                .applyButter()
                .applyJam()
            println("> toast is ready: $toast")
        }
        println("# toastDeferred async")

        // pourOrangeJuice
        val cupOfOrangeJuiceDeferred = async {
            val cupOfOrangeJuice: Juice = BreakfastAsync.pourOrangeJuice()
            println("> orange juice is ready: $cupOfOrangeJuice")
        }
        println("# cupOfOrangeJuiceDeferred async")

        awaitAll(pourCoffeeDeferred, eggsDeferred, baconDeferred, toastDeferred, cupOfOrangeJuiceDeferred)
    }
    println("> SingleAsyncNonBlockingBreakfast is ready!")
    println("\n\n>>> took ${time}ms\n\n")
}

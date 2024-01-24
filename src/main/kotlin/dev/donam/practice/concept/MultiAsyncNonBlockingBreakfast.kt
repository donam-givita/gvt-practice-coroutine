package dev.donam.practice.concept

import dev.donam.practice.concept.Breakfast.applyButter
import dev.donam.practice.concept.Breakfast.applyJam
import kotlinx.coroutines.*
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

fun main() = runBlocking {

    Executors.newFixedThreadPool(2).asCoroutineDispatcher().use { dispatcher ->
        withContext( dispatcher ) {
            val time = measureTimeMillis {
                println("========== fun main: ${Thread.currentThread().name} ==========")
                val cupOfCoffee: Deferred<Coffee> = async { BreakfastAsync.pourCoffee() }
                println("> coffee is ready: $cupOfCoffee")

                val eggs: Deferred<Egg> = async { BreakfastAsync.fryEggs(2) }
                println("> eggs are ready: $eggs")

                val bacon: Deferred<Bacon> = async { BreakfastAsync.fryBacon(3) }
                println("> bacon is ready: $bacon")

                val toast: Deferred<Toast> = async { BreakfastAsync.toastBread(2)
                    .applyButter()
                    .applyJam()}
                println("> toast is ready: $toast")

                val cupOfOrangeJuice: Deferred<Juice> = async { BreakfastAsync.pourOrangeJuice() }
                println("> orange juice is ready: $cupOfOrangeJuice")

                cupOfCoffee.await()
                eggs.await()
                bacon.await()
                toast.await()
                cupOfOrangeJuice.await()
            }
            println("> BlockingBreakfast is ready!")
            println("\n\n>>> took ${time}ms\n\n")
        }
    }

}



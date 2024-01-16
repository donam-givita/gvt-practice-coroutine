package dev.donam.practice.concept

import dev.donam.practice.concept.Breakfast.applyButter
import dev.donam.practice.concept.Breakfast.applyJam
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val time = measureTimeMillis {
        println("========== fun main: ${Thread.currentThread().name} ==========")
        val jobs = mutableListOf<Job>()

        // pourCoffee
        jobs.add(launch(Dispatchers.Default) {
            val cupOfCoffee: Coffee = Breakfast.pourCoffee()
            println("> coffee is ready: $cupOfCoffee")
        })

        // fryEggs
        jobs.add(launch(Dispatchers.Default) {
            val eggs: Egg = Breakfast.fryEggs(2)
            println("> eggs are ready: $eggs")
        })

        // fryBacon
        jobs.add(launch(Dispatchers.Default) {
            val bacon: Bacon = Breakfast.fryBacon(3)
            println("> bacon is ready: $bacon")
        })

        // toastBread
        // applyButter
        // applyJam
        jobs.add(launch(Dispatchers.Default) {
            val toast: Toast = Breakfast.toastBread(2)
                .applyButter()
                .applyJam()
            println("> toast is ready: $toast")
        })

        // pourOrangeJuice
        jobs.add(launch(Dispatchers.Default) {
            val cupOfOrangeJuice: Juice = Breakfast.pourOrangeJuice()
            println("> orange juice is ready: $cupOfOrangeJuice")
        })

        jobs.forEach { it.join() }
    }
    println("> NoneBlockingBreakfast is ready!")
    println("\n\n>>> took ${time}ms\n\n")
}

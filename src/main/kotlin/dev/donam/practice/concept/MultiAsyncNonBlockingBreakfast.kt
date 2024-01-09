package dev.donam.practice.concept

import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis {
        println("========== fun main: ${Thread.currentThread().name} ==========")
        // pourCoffee

        // fryEggs

        // fryBacon

        // toastBread
        // applyButter
        // applyJam

        // pourOrangeJuice
    }
    println("> BlockingBreakfast is ready!")
    println("\n\n>>> took ${time}ms\n\n")
}


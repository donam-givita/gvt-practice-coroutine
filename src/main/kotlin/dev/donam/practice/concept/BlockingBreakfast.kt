package dev.donam.practice.concept

import dev.donam.practice.concept.Breakfast.applyButter
import dev.donam.practice.concept.Breakfast.applyJam
import dev.donam.practice.concept.Breakfast.fryBacon
import dev.donam.practice.concept.Breakfast.fryEggs
import dev.donam.practice.concept.Breakfast.pourCoffee
import dev.donam.practice.concept.Breakfast.pourOrangeJuice
import dev.donam.practice.concept.Breakfast.toastBread
import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis {
        println("========== fun main: ${Thread.currentThread().name} ==========")
        val cupOfCoffee: Coffee = pourCoffee()
        println("> coffee is ready: $cupOfCoffee")

        val eggs: Egg = fryEggs(2)
        println("> eggs are ready: $eggs")

        val bacon: Bacon = fryBacon(3)
        println("> bacon is ready: $bacon")

        val toast: Toast = toastBread(2)
            .applyButter()
            .applyJam()
        println("> toast is ready: $toast")

        val cupOfOrangeJuice: Juice = pourOrangeJuice()
        println("> orange juice is ready: $cupOfOrangeJuice")
    }
    println("> BlockingBreakfast is ready!")
    println("\n\n>>> took ${time}ms\n\n")
}

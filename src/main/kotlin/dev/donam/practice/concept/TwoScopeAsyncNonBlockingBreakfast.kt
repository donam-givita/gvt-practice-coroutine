package dev.donam.practice.concept

import dev.donam.practice.concept.BreakfastAsync.applyButterAsync
import dev.donam.practice.concept.BreakfastAsync.applyJamAsync
import dev.donam.practice.concept.BreakfastAsync.fryBacon
import dev.donam.practice.concept.BreakfastAsync.fryEggs
import dev.donam.practice.concept.BreakfastAsync.pourCoffee
import dev.donam.practice.concept.BreakfastAsync.pourOrangeJuice
import dev.donam.practice.concept.BreakfastAsync.toastBread
import kotlinx.coroutines.*
import kotlinx.coroutines.selects.select
import java.util.concurrent.Executors
import kotlin.system.measureTimeMillis

suspend fun main() = runBlocking {
    val time = measureTimeMillis {
        val dispatcher1 = Executors.newFixedThreadPool(3).asCoroutineDispatcher()
        val dispatcher2 = Executors.newFixedThreadPool(3).asCoroutineDispatcher()

        val scope1 = CoroutineScope(dispatcher1).async {
            val cupOfCoffee = async { pourCoffee() }
            val eggs = async { fryEggs(2) }
            val bacon = async { fryBacon(3) }

            val result1 = awaitAll(cupOfCoffee, eggs, bacon)
            println("> coffee is ready: ${result1[0]}")
            println("> eggs is ready: ${result1[1]}")
            println("> bacon is ready: ${result1[2]}")
        }

        val scope2 = CoroutineScope(dispatcher2).async {
            val toast = async { toastBread(2).applyButterAsync().applyJamAsync() }
            val cupOfOrangeJuice = async { pourOrangeJuice() }

            select<Any> {
                listOf(toast, cupOfOrangeJuice).forEach {
                    it.onAwait {
                        println("> select is ready: $it")
                    }
                }
            }
        }

        scope1.join()
        scope2.join()
    }
    println(">two scope async non-BlockingBreakfast is ready!")
    println("\n\n>>> took ${time}ms\n\n")
}

package dev.donam.practice.comparison

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    println("Start coroutine scalability")
    val time = measureTimeMillis {
        val result = AtomicInteger(0)
        val jobs = mutableListOf<Job>()

        for (i in 1..TASK_NO) {
            jobs.add(launch(Dispatchers.IO) {
                for (x in 1..LOOPS) {
                    delay(WAIT_MS)
                }
                result.getAndIncrement()
            })
        }

        jobs.forEach { it.join() }
        println("result: ${result.get()}")
    }
    println(">> Took: $time ms")
}

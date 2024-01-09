package dev.donam.practice.comparison

import java.lang.Thread.sleep
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

const val TASK_NO = 10_000
const val LOOPS = 500
const val WAIT_MS = 10L

fun main() {
    println("Start thread scalability")
    val time = measureTimeMillis {
        val result = AtomicInteger(0)
        val threads = mutableListOf<Thread>()

        for (i in 1..TASK_NO) {
            threads.add(thread {
                for (x in 1..LOOPS) {
                    sleep(WAIT_MS)
                }
                result.getAndIncrement()
            })
        }

        threads.forEach { it.join() }
        println("> Result: ${result.get()}")
    }
    println(">> Took: $time ms")
}

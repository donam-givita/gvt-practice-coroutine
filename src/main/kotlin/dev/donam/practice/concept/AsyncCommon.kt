package dev.donam.practice.concept

import kotlinx.coroutines.delay

object BreakfastAsync {
    suspend fun pourCoffee(): Coffee {
        println("========== pourCoffee: ${Thread.currentThread().name} ==========")
        (0..2).forEach {
            println("Pouring coffee")
            delay(1000)
        }
        println("filled with coffee")

        return Coffee()
    }

    suspend fun fryEggs(count: Int): Egg {
        println("========== fryEggs: ${Thread.currentThread().name} ==========")
        (0..2).forEach {
            println("Warming the egg pan...")
            delay(1000)
        }
        println("cracking $count eggs")
        (0..3).forEach {
            println("cooking the eggs...")
            delay(1000)
        }
        println("Put eggs on plate")

        return Egg()
    }

    suspend fun fryBacon(slices: Int): Bacon {
        println("========== fryBacon: ${Thread.currentThread().name} ==========")
        println("putting $slices slices of bacon in the pan")
        (0..4).forEach {
            println("cooking first side of bacon...")
            delay(1000)
        }
        println("flipping first side of bacon(s)")
        (0..4).forEach {
            println("cooking the second side of bacon...")
            delay(1000)
        }
        println("Put bacon on plate")

        return Bacon()
    }

    suspend fun toastBread(slices: Int): Toast {
        println("========== toastBread: ${Thread.currentThread().name} ==========")
        println("Putting ${slices} slices of bread in the toaster")
        (0..4).forEach {
            println("toasting...")
            delay(1000)
        }
        println("Remove toast from toaster")

        return Toast()
    }

    suspend fun Toast.applyJam(): Toast {
        println("========== applyJam: ${Thread.currentThread().name} ==========")
        println("Putting jam on the toast")
        delay(1000)
        println("filled with jam")
        return Toast()
    }

    suspend fun Toast.applyButter(): Toast {
        println("========== applyButter: ${Thread.currentThread().name} ==========")
        println("Putting butter on the toast")
        delay(1000)
        println("filled with butter")
        return Toast()
    }


    suspend fun pourOrangeJuice(): Juice {
        println("========== pourOrangeJuice: ${Thread.currentThread().name} ==========")
        (0..2).forEach {
            println("Pouring orange juice")
            delay(1000)
        }
        println("filled with orange juice")
        return Juice()
    }
}

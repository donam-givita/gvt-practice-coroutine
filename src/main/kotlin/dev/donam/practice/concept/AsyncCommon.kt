package dev.donam.practice.concept
import kotlinx.coroutines.delay

val WAIT_MS_VALUE:Long = 1000

object BreakfastAsync {
    suspend fun pourCoffee(): Coffee {
        println("========== pourCoffee: ${Thread.currentThread().name} ==========")
        repeat((0..2).count()) {
            println("Pouring coffee")
            delay(WAIT_MS_VALUE)
        }
        println("filled with coffee")
        return Coffee()
    }

    suspend fun fryEggs(count: Int): Egg {
        println("========== fryEggs: ${Thread.currentThread().name} ==========")
        repeat((0..2).count()) {
            println("Warming the egg pan...")
            delay(WAIT_MS_VALUE)
        }
        println("cracking $count eggs")
        repeat((0..3).count()) {
            println("cooking the eggs...")
            delay(WAIT_MS_VALUE)
        }
        println("Put eggs on plate")

        return Egg()
    }

    suspend fun fryBacon(slices: Int): Bacon {
        println("========== fryBacon: ${Thread.currentThread().name} ==========")
        println("putting $slices slices of bacon in the pan")
        repeat((0..4).count()) {
            println("cooking first side of bacon...")
            delay(WAIT_MS_VALUE)
        }
        println("flipping first side of bacon(s)")
        repeat((0..4).count()) {
            println("cooking the second side of bacon...")
            delay(WAIT_MS_VALUE)
        }
        println("Put bacon on plate")

        return Bacon()
    }

    suspend fun toastBread(slices: Int): Toast {
        println("========== toastBread: ${Thread.currentThread().name} ==========")
        println("Putting ${slices} slices of bread in the toaster")
        repeat((0..4).count()) {
            println("toasting...")
            delay(WAIT_MS_VALUE)
        }
        println("Remove toast from toaster")

        return Toast()
    }

    suspend fun Toast.applyJam(): Toast {
        println("========== applyJam: ${Thread.currentThread().name} ==========")
        println("Putting jam on the toast")
        delay(WAIT_MS_VALUE)
        println("filled with jam")
        return Toast()
    }

    suspend fun Toast.applyButter(): Toast {
        println("========== applyButter: ${Thread.currentThread().name} ==========")
        println("Putting butter on the toast")
        delay(WAIT_MS_VALUE)
        println("filled with butter")
        return Toast()
    }


    suspend fun pourOrangeJuice(): Juice {
        println("========== pourOrangeJuice: ${Thread.currentThread().name} ==========")
        repeat((0..2).count()) {
            println("Pouring orange juice")
            delay(WAIT_MS_VALUE)
        }
        println("filled with orange juice")
        return Juice()
    }
}

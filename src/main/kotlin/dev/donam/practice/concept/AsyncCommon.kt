package dev.donam.practice.concept

object BreakfastAsync {
    fun pourCoffee(): Coffee {
        println("========== pourCoffee: ${Thread.currentThread().name} ==========")
        println("Pouring coffee")
        println("filled with coffee")
        TODO()
    }

    fun fryEggs(count: Int): Egg {
        println("========== fryEggs: ${Thread.currentThread().name} ==========")
        println("Warming the egg pan...")
        println("cracking $count eggs")
        println("cooking the eggs...")
        println("Put eggs on plate")
        TODO()
    }

    fun fryBacon(slices: Int): Bacon {
        println("========== fryBacon: ${Thread.currentThread().name} ==========")
        println("putting $slices slices of bacon in the pan")
        println("cooking first side of bacon...")
        println("flipping first side of bacon(s)")
        println("cooking the second side of bacon...")
        println("Put bacon on plate")
        TODO()
    }

    fun toastBread(slices: Int): Toast {
        println("========== toastBread: ${Thread.currentThread().name} ==========")
        println("Putting ${slices} slices of bread in the toaster")
        println("toasting...")
        println("Remove toast from toaster")
        TODO()
    }

    fun Toast.applyJam(): Toast {
        println("========== applyJam: ${Thread.currentThread().name} ==========")
        println("Putting jam on the toast")
        println("filled with jam")
        TODO()
    }

    fun Toast.applyButter(): Toast {
        println("========== applyButter: ${Thread.currentThread().name} ==========")
        println("Putting butter on the toast")
        println("filled with butter")
        TODO()
    }


    fun pourOrangeJuice(): Juice {
        println("========== pourOrangeJuice: ${Thread.currentThread().name} ==========")
        println("Pouring orange juice")
        println("filled with orange juice")
        TODO()
    }
}

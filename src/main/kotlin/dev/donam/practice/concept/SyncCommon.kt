package dev.donam.practice.concept

object Breakfast {
    fun pourCoffee(): Coffee {
        println("========== pourCoffee: ${Thread.currentThread().name} ==========")
        (0..2).forEach {
            println("Pouring coffee")
            Thread.sleep(1000)
        }
        println("filled with coffee")
        return Coffee()
    }

    fun fryEggs(count: Int): Egg {
        println("========== fryEggs: ${Thread.currentThread().name} ==========")
        (0..2).forEach {
            println("Warming the egg pan...")
            Thread.sleep(1000)
        }
        println("cracking $count eggs")
        (0..3).forEach {
            println("cooking the eggs...")
            Thread.sleep(1000)
        }
        println("Put eggs on plate")

        return Egg()
    }

    fun fryBacon(slices: Int): Bacon {
        println("========== fryBacon: ${Thread.currentThread().name} ==========")
        println("putting $slices slices of bacon in the pan")
        (0..4).forEach {
            println("cooking first side of bacon...")
            Thread.sleep(1000)
        }
        println("flipping first side of bacon(s)")
        (0..4).forEach {
            println("cooking the second side of bacon...")
            Thread.sleep(1000)
        }
        println("Put bacon on plate")

        return Bacon()
    }

    fun toastBread(slices: Int): Toast {
        println("========== toastBread: ${Thread.currentThread().name} ==========")
        println("Putting ${slices} slices of bread in the toaster")
        (0..4).forEach {
            println("toasting...")
            Thread.sleep(1000)
        }
        println("Remove toast from toaster")

        return Toast()
    }

    fun Toast.applyJam(): Toast {
        println("========== applyJam: ${Thread.currentThread().name} ==========")
        println("Putting jam on the toast")
        Thread.sleep(1000)
        println("filled with jam")
        return Toast()
    }

    fun Toast.applyButter(): Toast {
        println("========== applyButter: ${Thread.currentThread().name} ==========")
        println("Putting butter on the toast")
        Thread.sleep(1000)
        println("filled with butter")
        return Toast()
    }


    fun pourOrangeJuice(): Juice {
        println("========== pourOrangeJuice: ${Thread.currentThread().name} ==========")
        (0..2).forEach {
            println("Pouring orange juice")
            Thread.sleep(1000)
        }
        println("filled with orange juice")
        return Juice()
    }
}

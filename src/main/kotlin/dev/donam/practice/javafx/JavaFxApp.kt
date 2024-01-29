package dev.donam.practice.javafx

import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


class HelloFX : Application() {
    override fun start(primaryStage: Stage) {
        primaryStage.title = "JavaFX Example"

        val textArea = TextArea()

        val btnMakeBreakfast = Button("make breakfast")
        btnMakeBreakfast.onAction = EventHandler {

            val deffer = CoroutineScope(Dispatchers.IO).async {
                println("Await Start")
                delay(3000)
                println("Await END")

                textArea.appendText("TODO Something \n")
                true
            }

            println("END $deffer ")
        }
        val btnRandomUuid = Button("gen UUID")
        btnRandomUuid.onAction = EventHandler {
            textArea.appendText("${UUID.randomUUID()}\n")
        }
        val buttonGroup = HBox(btnMakeBreakfast, btnRandomUuid)

        val vbox = VBox(buttonGroup, textArea)
        val scene = Scene(vbox)

        primaryStage.scene = scene
        primaryStage.show()
    }
}

fun main() {
    Application.launch(HelloFX::class.java)
}

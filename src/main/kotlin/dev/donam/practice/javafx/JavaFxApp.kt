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

class HelloFX : Application() {
    override fun start(primaryStage: Stage) {
        primaryStage.title = "JavaFX Example"

        val textArea = TextArea()

        val btnMakeBreakfast = Button("make breakfast")
        btnMakeBreakfast.onAction = EventHandler {
            Thread.sleep(3000)
            textArea.appendText("TODO Something\n")
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
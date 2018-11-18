import render.Renderer
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {

    val startTime = System.nanoTime()
    Renderer().render()
    println("Frame rendered in ${TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime) / 1000.0} seconds.")
}

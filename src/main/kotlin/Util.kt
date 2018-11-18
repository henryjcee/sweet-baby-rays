import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import render.shader.Material
import java.awt.Color

fun Double.toRadians(): Double = (this * (Math.PI / 180))

fun Double.toDegrees(): Double = (this * (180 / Math.PI))

fun <A> Iterable<A>.parallelForEach(action: (A) -> Unit) {
    runBlocking {
        this@parallelForEach.forEach {
            launch(Dispatchers.Default) { action(it) }
        }
    }
}

fun Color.toMaterial(): Material = Material(this)

fun Float.clip(max: Float = 1.0F) = if (this > max) max else if (this < 0.0F) 0.0F else this

//fun List<Color>.colourMapAndCombine() = fold(Triple(0, 0, 0)) { sum, sample ->
//    Triple(sum.first + sample.red, sum.second + sample.green, sum.third + sample.blue)
//}.let { Color(it.first / size, it.second / size, it.third / size) }

//fun List<Illumination>.colourMapAndCombine() = reduce { a, b -> a + b }
//    .let { Illumination(it.red / size, it.green / size, it.blue / size) }
//    .let { calculateColor(it) }

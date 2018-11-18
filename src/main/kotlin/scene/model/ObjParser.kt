package scene.model

import scene.geometry.primitive.Point
import scene.geometry.primitive.Polygon
import java.io.File

fun parseObj(file: String): List<Polygon> {

    println("Parsing $file...")

    val lines = File(file).readLines()
    val vertices = lines
        .filter { it.startsWith("v") }
        .map {
            val elements = it.split(" ")
            Point(elements[1].toDouble(), elements[2].toDouble(), elements[3].toDouble())
        }
    return lines
        .filter { it.startsWith("f") }
//        .filter { Math.random() < 0.3 }
        .map {
            val elements = it.split(" ")
            Polygon(vertices[elements[3].toInt() - 1], vertices[elements[2].toInt() - 1], vertices[elements[1].toInt() - 1])
        }
}

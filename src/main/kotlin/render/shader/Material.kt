package render.shader

import clip
import render.Bounce
import java.awt.Color

class Material(
    val color: Color = Color.GRAY,
    val reflectivity: Double = 0.0
)

fun calculateColor(bounce: Bounce, illumination: Illumination): Color {
    return Color(
        (bounce.polyInfo!!.obj.material.color.red / 255.0F * illumination.red.toFloat()).clip(),
        (bounce.polyInfo.obj.material.color.green / 255.0F * illumination.green.toFloat()).clip(),
        (bounce.polyInfo.obj.material.color.blue / 255.0F * illumination.blue.toFloat()).clip()
    )
}

fun List<Color>.average(): Color {
    return this
        .fold(Triple(0, 0, 0)) { a, b -> Triple(a.first + b.red, a.second + b.green, a.third + b.blue) }
        .let { Color(it.first / this.size, it.second / this.size, it.third / this.size) }
}

operator fun Color.plus(other: Color) = Color(this.red + other.red, this.green + other.green, this.blue + other.blue)

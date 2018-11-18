package scene

import scene.geometry.primitive.Point
import java.awt.Color

class Light(
    val position: Point,
    val intensity: Double = 100_000.0,
    val color: Color = Color.WHITE
) {
    fun decay(p: Point) = intensity * Math.pow(0.5, position.distance(p))
}

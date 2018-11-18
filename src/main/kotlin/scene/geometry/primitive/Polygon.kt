package scene.geometry.primitive

import scene.geometry.Geometry

data class Polygon(
    val a: Point,
    val b: Point,
    val c: Point
): Geometry {

    val ab: Point = b - a
    val bc: Point = c - b
    val ca: Point = a - c

    fun normalP(): Point {
        return (ab cross ca).normalize()
    }

    fun normal(): Line {
        return Line(Point((a.x + b.x + c.x) / 3, (a.y + b.y + c.y) / 3, (a.z + b.z + c.z) / 3), normalP())
    }
}

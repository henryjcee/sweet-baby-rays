package render

import scene.geometry.primitive.Line
import scene.geometry.primitive.Point
import scene.geometry.Transform

class Ray(
    origin: Point,
    direction: Point
) : Line(origin, direction) {

    override infix fun apply(t: Transform): Ray {
        return Ray(
            Point(
                origin.x + t.a4,
                origin.y + t.b4,
                origin.z + t.c4),
            Point(
                direction.x * t.a1 + direction.y * t.a2 + direction.z * t.a3,
                direction.x * t.b1 + direction.y * t.b2 + direction.z * t.b3,
                direction.x * t.c1 + direction.y * t.c2 + direction.z * t.c3
            )
        )
    }
}

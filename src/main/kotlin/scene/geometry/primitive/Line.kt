package scene.geometry.primitive

import scene.geometry.Geometry
import scene.geometry.Transform

open class Line(val origin: Point, direction: Point) : Geometry {

    val direction: Point = direction.normalize()
    val invDirection: Point = Point(1.0 / direction.x, 1.0 / direction.y, 1.0 / direction.z).normalize()

    open infix fun apply(t: Transform): Line {
        return Line(
            Point(origin.x + t.a4, origin.y + t.b4, origin.z + t.c4),
            Point(
                direction.x * t.a1 + direction.y * t.a2 + direction.z * t.a3,
                direction.x * t.b1 + direction.y * t.b2 + direction.z * t.b3,
                direction.x * t.c1 + direction.y * t.c2 + direction.z * t.c3
            )
        )
    }

    infix fun pointAt(t: Double): Point = origin + direction * t

    //    Negative values of r mean no intersection
    infix fun intersectPlane(plane: Line): Pair<Double, Point> {

        val prod1 = plane.direction dot (plane.origin - this.origin)
        val prod2 = plane.direction dot this.direction
        val r = prod1 / prod2

        return r to (this pointAt r)
    }

    override fun toString(): String {
        return "Line ( origin: $origin, direction: $direction )"
    }

}

fun intersectPolygonOneSided(line: Line, poly: Polygon): Pair<Double, Point>? = intersectPolygon(line, poly).takeIf { poly.normal().direction dot line.direction <= 0 }

fun intersectPolygon(line: Line, poly: Polygon): Pair<Double, Point>? {

    val intersection = line.intersectPlane(poly.normal())
    val p = intersection.second
    val pn = poly.normal().direction

    return if (
        intersection.first >= 0 &&
        pn dot (poly.ab cross (p - poly.a)) <= 0 &&
        pn dot (poly.bc cross (p - poly.b)) <= 0 &&
        pn dot (poly.ca cross (p - poly.c)) <= 0) intersection
    else null
}

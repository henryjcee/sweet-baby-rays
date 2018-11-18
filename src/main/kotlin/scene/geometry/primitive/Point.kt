package scene.geometry.primitive

import scene.geometry.Geometry
import scene.geometry.Transform

data class Point(
    val x: Double = 0.0,
    val y: Double = 0.0,
    val z: Double = 0.0
): Geometry {

    constructor (x: Number, y: Number, z: Number) : this(x.toDouble(), y.toDouble(), z.toDouble())

    fun norm(): Double {
        return Math.sqrt((x * x + y * y + z * z))
    }

    fun normalize(): Point {
        val norm = norm()
        return Point(x / norm, y / norm, z / norm)
    }

    infix fun dot(other: Point): Double {
        return this.x * other.x + this.y * other.y + this.z * other.z
    }

    infix fun cross(other: Point): Point {
        return Point(
            this.y * other.z - other.y * this.z,
            this.z * other.x - other.z * this.x,
            this.x * other.y - other.x * this.y
        )
    }

    operator fun times(d: Double): Point {
        return Point(x * d, y * d, z * d)
    }

    operator fun plus(other: Point): Point {
        return Point(this.x + other.x, this.y + other.y, this.z + other.z)
    }

    operator fun minus(other: Point): Point {
        return Point(this.x - other.x, this.y - other.y, this.z - other.z)
    }

    infix fun distance(other: Point): Double {
        return (this - other).norm()
    }

    infix fun apply(transform: Transform): Point {
        return transform.apply(this)
    }

    override fun toString(): String {
        return "( $x, $y, $z )"
    }

    companion object {
        val ZERO = Point(0, 0, 0)
        val ONE = Point(1, 1, 1)
        val UP = Point(0, 0, 1)
        val DOWN = Point(0, 0, -1)
        val X = Point(1, 0, 0)
        val Xm = Point(-1, 0, 0)
        val Y = Point(0, 1, 0)
        val Ym = Point(0, -1, 0)
        val Z = Point(0, 0, 1)
        val Zm = Point(0, 0, -1)
    }
}

infix fun Point.pathTo(to: Point): Line = Line(this, to - this)
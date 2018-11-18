package scene.geometry

import scene.geometry.primitive.Point
import toDegrees
import toRadians

class Transform {

    val a1: Double
    val a2: Double
    val a3: Double
    val a4: Double
    val b1: Double
    val b2: Double
    val b3: Double
    val b4: Double
    val c1: Double
    val c2: Double
    val c3: Double
    val c4: Double
    val d1: Double
    val d2: Double
    val d3: Double
    val d4: Double

    constructor(
        x: Double = 0.0, y: Double = 0.0, z: Double = 0.0,
        u: Double = 0.0, v: Double = 0.0, w: Double = 0.0
    ) {

        val cosu = Math.cos(u.toRadians())
        val sinu = Math.sin(u.toRadians())
        val cosv = Math.cos(v.toRadians())
        val sinv = Math.sin(v.toRadians())
        val cosw = Math.cos(w.toRadians())
        val sinw = Math.sin(w.toRadians())

        a1 = cosv * cosw
        a2 = cosv * sinw
        a3 = if (v == 0.0) 0.0 else -sinv // To avoid -0.0
        a4 = x
        b1 = sinu * sinv * cosw - cosu * sinw
        b2 = sinu * sinv * sinw + cosu * cosw
        b3 = sinu * cosv
        b4 = y
        c1 = cosu * sinv * cosw + sinu * sinw
        c2 = cosu * sinv * sinw - sinu * cosw
        c3 = cosu * cosv
        c4 = z
        d1 = 0.0
        d2 = 0.0
        d3 = 0.0
        d4 = 1.0
    }

    constructor(x: Double, y: Double, z: Double) : this(x, y, z, 0.0, 0.0, 0.0)

    constructor(
        a1: Double, a2: Double, a3: Double, a4: Double,
        b1: Double, b2: Double, b3: Double, b4: Double,
        c1: Double, c2: Double, c3: Double, c4: Double,
        d1: Double, d2: Double, d3: Double, d4: Double
    ) {
        this.a1 = a1
        this.a2 = a2
        this.a3 = a3
        this.a4 = a4
        this.b1 = b1
        this.b2 = b2
        this.b3 = b3
        this.b4 = b4
        this.c1 = c1
        this.c2 = c2
        this.c3 = c3
        this.c4 = c4
        this.d1 = d1
        this.d2 = d2
        this.d3 = d3
        this.d4 = d4
    }

    infix fun apply(a: Point): Point {
        return Point(
            a.x * a1 + a.y * a2 + a.z * a3 + a4,
            a.x * b1 + a.y * b2 + a.z * b3 + b4,
            a.x * c1 + a.y * c2 + a.z * c3 + c4
        )
    }

    operator fun plus(t: Transform): Transform {
        return Transform(
            a1 * t.a1 + a2 * t.b1 + a3 * t.c1 + a4 * d1, a1 * t.a2 + a2 * t.b2 + a3 * t.c2 + a4 * t.d2, a1 * t.a3 + a2 * t.b3 + a3 * t.c3 + a4 * t.d3, a1 * t.a4 + a2 * t.b4 + a3 * t.c4 + a4 * t.d4,
            b1 * t.a1 + b2 * t.b1 + b3 * t.c1 + b4 * d1, b1 * t.a2 + b2 * t.b2 + b3 * t.c2 + b4 * t.d2, b1 * t.a3 + b2 * t.b3 + b3 * t.c3 + b4 * t.d3, b1 * t.a4 + b2 * t.b4 + b3 * t.c4 + b4 * t.d4,
            c1 * t.a1 + c2 * t.b1 + c3 * t.c1 + c4 * d1, c1 * t.a2 + c2 * t.b2 + c3 * t.c2 + c4 * t.d2, c1 * t.a3 + c2 * t.b3 + c3 * t.c3 + c4 * t.d3, c1 * t.a4 + c2 * t.b4 + c3 * t.c4 + c4 * t.d4,
            d1 * t.a1 + d2 * t.b1 + d3 * t.c1 + d4 * d1, d1 * t.a2 + d2 * t.b2 + d3 * t.c2 + d4 * t.d2, d1 * t.a3 + d2 * t.b3 + d3 * t.c3 + d4 * t.d3, d1 * t.a4 + d2 * t.b4 + d3 * t.c4 + d4 * t.d4
        )
    }

    fun u(): Double = Math.atan2(c2, c3).toDegrees()
    fun v(): Double = Math.atan2(-c1, Math.sqrt(c2*c2 + c3*c3)).toDegrees()
    fun w(): Double = Math.atan2(b1, a1).toDegrees()

    override fun toString(): String {
        return "( $a1, $a2, $a3, $a4 \n  $b1, $b2, $b3, $b4 \n  $c1, $c2, $c3, $c4 \n  $d1, $d2, $d3, $d4 )"
    }

    companion object {
        val ZERO = Transform()
    }
}

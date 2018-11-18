package scene.geometry.volume

import scene.geometry.PolyInfo
import scene.geometry.primitive.Line
import scene.geometry.primitive.Point
import scene.geometry.primitive.Polygon

class AABB(
    val first: Point,
    val second: Point,
    val contents: List<PolyInfo> = emptyList(),
    override val children: List<AABB> = emptyList()
): Volume {

    private val centre = Point(
        first.x + (second.x - first.x) / 2.0,
        first.y + (second.y - first.y) / 2.0,
        first.z + (second.z - first.z) / 2.0
    )

    private val halfWidths = Point(
        Math.abs(centre.x - first.x),
        Math.abs(centre.y - first.y),
        Math.abs(centre.z - first.z)
    )

    val normals: List<Line> = listOf(
        Line(first, Point.Xm),
        Line(first, Point.Ym),
        Line(first, Point.Zm),
        Line(second, Point.X),
        Line(second, Point.Y),
        Line(second, Point.Z)
    )

    fun containsPoint(point: Point): Boolean {
        return point.x >= this.first.x &&
            point.x <= this.second.x &&
            point.y >= this.first.y &&
            point.y <= this.second.y &&
            point.z >= this.first.z &&
            point.z <= this.second.z
    }

    fun containsAABB(aabb: AABB): Boolean {

        if (Math.abs(aabb.centre.x - centre.x) > (aabb.halfWidths.x + halfWidths.x)) return false
        if (Math.abs(aabb.centre.y - centre.y) > (aabb.halfWidths.y + halfWidths.y)) return false
        if (Math.abs(aabb.centre.z - centre.z) > (aabb.halfWidths.z + halfWidths.z)) return false

        return true
    }

    fun retrieve(line: Line): List<PolyInfo> {
        return when {
            line intersects this -> contents.plus(children.flatMap { it.retrieve(line) })
            else -> emptyList()
        }
    }
}

// Slab method for line-AABB intersection
infix fun Line.intersects(aabb: AABB): Boolean {

    val tx1 = (aabb.first.x - origin.x) * invDirection.x
    val tx2 = (aabb.second.x - origin.x) * invDirection.x
    val xMin = minOf(tx1, tx2)
    val xMax = maxOf(tx1, tx2)

    val ty1 = (aabb.first.y - origin.y) * invDirection.y
    val ty2 = (aabb.second.y - origin.y) * invDirection.y
    val yMin = minOf(ty1, ty2)
    val yMax = maxOf(ty1, ty2)

    val tz1 = (aabb.first.z - origin.z) * invDirection.z
    val tz2 = (aabb.second.z - origin.z) * invDirection.z
    val zMin = minOf(tz1, tz2)
    val zMax = maxOf(tz1, tz2)

    val min = maxOf(minOf(xMin, xMax), minOf(yMin, yMax), minOf(zMin, zMax))
    val max = minOf(maxOf(xMin, xMax), maxOf(yMin, yMax), maxOf(zMin, zMax))

    return max >= min
}

fun Polygon.aabb(): AABB = AABB(
    Point(
        minOf(a.x, b.x, c.x),
        minOf(a.y, b.y, c.y),
        minOf(a.z, b.z, c.z)
    ),
    Point(
        maxOf(a.x, b.x, c.x),
        maxOf(a.y, b.y, c.y),
        maxOf(a.z, b.z, c.z)
    )
)

fun Collection<PolyInfo>.aabb(): AABB {

    var minX = Double.POSITIVE_INFINITY
    var minY = Double.POSITIVE_INFINITY
    var minZ = Double.POSITIVE_INFINITY
    var maxX = Double.NEGATIVE_INFINITY
    var maxY = Double.NEGATIVE_INFINITY
    var maxZ = Double.NEGATIVE_INFINITY

    this.forEach {
        listOf(it.poly.a, it.poly.b, it.poly.c).forEach {
            minX = if (it.x < minX) it.x else minX
            minY = if (it.y < minY) it.y else minY
            minZ = if (it.z < minZ) it.z else minZ
            maxX = if (it.x > maxX) it.x else maxX
            maxY = if (it.y > maxY) it.y else maxY
            maxZ = if (it.z > maxZ) it.z else maxZ
        }
    }

    return AABB(Point(minX, minY, minZ), Point(maxX, maxY, maxZ))
}


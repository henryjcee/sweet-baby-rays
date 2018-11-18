package scene.geometry.accelerator

import scene.geometry.*
import scene.geometry.primitive.Line
import scene.geometry.primitive.Point
import scene.geometry.volume.AABB
import scene.geometry.volume.aabb

class Octree(
    polys: List<PolyInfo>,
    depth: Int = 5,
    volume: AABB = polys.aabb()
) : Accelerator(
    polys
) {

    private val volume: AABB = build(polys, depth, volume)

    override fun shortlist(line: Line): List<PolyInfo> {
        return volume.retrieve(line)
    }

    private fun build(polys: List<PolyInfo>, depth: Int, volume: AABB): AABB {

        if (polys.isEmpty()) throw IllegalArgumentException("Octree called with no polygons")

        return when (depth) {
            0 -> AABB(volume.first, volume.second, polys, emptyList())
            else -> {
                val children = split(volume)
                    .mapNotNull { child ->
                        val childPolys = polys.filter { child.containsAABB(it.poly.aabb()) }
                        if (childPolys.isEmpty()) null
                        else build(childPolys, depth - 1, child)
                    }
                AABB(volume.first, volume.second, emptyList(), children)
            }
        }
    }

    private fun split(aabb: AABB): List<AABB> {

        val min = aabb.first
        val max = aabb.second

        val xMid = min.x + (max.x - min.x) / 2.0
        val yMid = min.y + (max.y - min.y) / 2.0
        val zMid = min.z + (max.z - min.z) / 2.0

        return listOf(
            AABB(Point(min.x, min.y, min.z), Point(xMid, yMid, zMid)),
            AABB(Point(xMid, min.y, min.z), Point(max.x, yMid, zMid)),
            AABB(Point(min.x, yMid, min.z), Point(xMid, max.y, zMid)),
            AABB(Point(xMid, yMid, min.z), Point(max.x, max.y, zMid)),
            AABB(Point(min.x, min.y, zMid), Point(xMid, yMid, max.z)),
            AABB(Point(xMid, min.y, zMid), Point(max.x, yMid, max.z)),
            AABB(Point(min.x, yMid, zMid), Point(xMid, max.y, max.z)),
            AABB(Point(xMid, yMid, zMid), Point(max.x, max.y, max.z))
        )
    }
}

package render

import scene.geometry.PolyInfo
import scene.geometry.accelerator.Accelerator
import scene.geometry.primitive.*

data class Bounce(
    val line: Line,
    val r: Double = Double.POSITIVE_INFINITY,
    val point: Point? = null,
    val polyInfo: PolyInfo? = null
)

fun findBounce(
    line: Line,
    accelerator: Accelerator,
    intersectTest: (Line, Polygon) -> Pair<Double, Point>? = ::intersectPolygon,
    polyFrom: Polygon? = null
): Bounce? {
    return accelerator.shortlist(line)
        .fold(Bounce(line = line)) { bounce, info ->
            intersectTest(line, info.poly)?.let {
                if (polyFrom !== info.poly && it.first < bounce.r) {
                    return@fold Bounce(line, it.first, it.second, info)
                }
            }
            bounce
        }.let { if (it.point == null) null else it }
}

fun findBounceSingleSided(
    line: Line,
    accelerator: Accelerator,
    polyFrom: Polygon? = null
): Bounce? = findBounce(line, accelerator, ::intersectPolygonOneSided, polyFrom)

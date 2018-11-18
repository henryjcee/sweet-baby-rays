package scene.geometry.accelerator

import scene.geometry.primitive.Line
import scene.geometry.PolyInfo

/**
 * Represents an acceleration structure used to increase performance of intersection calculations. Presently limited to
 * Line - Polygon intersections.
 */
abstract class Accelerator(
    val polys: List<PolyInfo>
) {
    abstract fun shortlist(line: Line): List<PolyInfo>
}

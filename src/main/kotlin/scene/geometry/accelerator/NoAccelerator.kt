package scene.geometry.accelerator

import scene.geometry.primitive.Line
import scene.geometry.PolyInfo

/**
 * Trivial implementation of Accelerator that returns the entire set of geometry on every call to shortlist.
 */
class NoAccelerator(
    polys: List<PolyInfo>
): Accelerator(
    polys
) {
    override fun shortlist(line: Line): List<PolyInfo> = polys
}

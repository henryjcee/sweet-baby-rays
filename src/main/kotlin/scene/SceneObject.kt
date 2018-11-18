package scene

import render.shader.Material
import scene.geometry.PolyInfo
import scene.geometry.primitive.Polygon
import scene.geometry.Transform

data class SceneObject(
    val polys: List<Polygon>,
    val orientation: Transform,
    val material: Material = Material(),
    val children: List<SceneObject> = emptyList(),
    val visible: Boolean = true
) {

    fun transformAbsolute(): List<PolyInfo> {

        val transformedPolys = polys
            .map { PolyInfo(Polygon(it.a apply orientation, it.b apply orientation, it.c apply orientation), this) }

        return children
            .flatMap { it.transformAbsolute() }
            .plus(if (visible) transformedPolys else emptyList())
    }
}

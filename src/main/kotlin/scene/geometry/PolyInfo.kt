package scene.geometry

import scene.SceneObject
import scene.geometry.primitive.Polygon

data class PolyInfo(
    val poly: Polygon,
    val obj: SceneObject
)

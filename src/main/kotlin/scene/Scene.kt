package scene

import scene.geometry.PolyInfo

data class Scene(
    val sceneObjects: List<SceneObject>,
    val lights: List<Light>,
    val camera: Camera
) {
    fun transformPolys(): List<PolyInfo> = sceneObjects.flatMap { it.transformAbsolute() }
}

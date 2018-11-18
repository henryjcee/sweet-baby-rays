package scene

import scene.geometry.Transform
import scene.geometry.primitive.Point
import scene.model.backdrop
import scene.model.mlem
import scene.model.teapot
import toMaterial
import java.awt.Color

fun initScene(): Scene {
    return Scene(
        sceneObjects = listOf(
            SceneObject(
                polys = teapot(),
                orientation = Transform(u = -90.0),
                material = Color(0.95F, 0.4F, 0.6F).toMaterial()
            ),
            SceneObject(
                backdrop(),
                Transform(x = -5.0, y = 5.0, z = 0.0),
                Color(0.8F, 0.8F, 0.8F).toMaterial(),
                visible = true
            )
        ),
        lights = listOf(Light(Point(z = 15.0, x = 20.0, y = 0.1))),
        camera = Camera(
            transform = Transform(w = -30.0) + Transform(u = 10.0) + Transform(x = 0.4, y = -12.0, z = 1.4),
            horizontalFOV = 48.0
        )
    )
}



package render.shader

import render.Bounce
import render.findBounce
import scene.Light
import scene.Scene
import scene.geometry.accelerator.Accelerator
import scene.geometry.primitive.Line
import scene.geometry.primitive.pathTo

data class Illumination(val red: Double = 0.0, val green: Double = 0.0, val blue: Double = 0.0) {
    constructor(red: Number, green: Number, blue: Number) : this(red.toDouble(), green.toDouble(), blue.toDouble())
    constructor(i: Double) : this(i, i, i)
}

operator fun Illumination.plus(other: Illumination) =
    Illumination(this.red + other.red, this.green + other.green, this.blue + other.blue)

fun directIllumination(bounce: Bounce, scene: Scene, accelerator: Accelerator): Illumination {

    val point = bounce.point!!

    return scene.lights.map { light ->
        findBounce(
            line = point pathTo light.position,
            accelerator = accelerator,
            polyFrom = bounce.polyInfo!!.poly
        )?.let { if (it.r < point distance light.position) return@map 0.0 }
        return@map lambertianReflectance(bounce, light)
    }.let { Illumination(i = it.sum()) }

}

fun lambertianReflectance(bounce: Bounce, light: Light) = bounce.polyInfo!!.poly.normal().direction dot (Line(bounce.point!!, light.position).direction)

fun indirectIllumination(bounce: Bounce, scene: Scene, accelerator: Accelerator): Illumination = Illumination()

package render

import parallelForEach
import render.shader.average
import render.shader.calculateColor
import render.shader.directIllumination
import render.shader.indirectIllumination
import render.shader.plus
import scene.Scene
import scene.geometry.accelerator.Accelerator
import scene.geometry.accelerator.Octree
import scene.initScene
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class Renderer {

    fun render(xSize: Int = 640, ySize: Int = 480, scene: Scene = initScene()) {

        println("Rendering frame at [$xSize x $ySize]...")

        val rayGenerator = RayGenerator(xSize, ySize, scene.camera)
        val target = BufferedImage(xSize, ySize, 1)
        val accelerator: Accelerator = Octree(scene.transformPolys())

        println("${accelerator.polys.size} polys in scene...")

        (0 until xSize * ySize).parallelForEach {
            rayGenerator.sampleCameraRays(it % xSize, it / xSize, samples = 50)
                .mapNotNull { ray -> findBounceSingleSided(ray, accelerator) }
                .map { bounce -> (bounce to directIllumination(bounce, scene, accelerator) + indirectIllumination(bounce, scene, accelerator) )}
                .map { (bounce, illumination) -> calculateColor(bounce, illumination) }
                .let { subPixels -> target.setRGB(it % xSize, ySize - 1 - it / xSize, subPixels.average().rgb) }
        }

        ImageIO.write(target, "png", File("voodoo.png"))
    }
}

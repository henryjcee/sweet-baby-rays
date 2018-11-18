package render

import scene.Camera
import scene.geometry.primitive.Point
import toRadians
import kotlin.math.tan

class RayGenerator(
    private val xSize: Int,
    private val ySize: Int,
    private val camera: Camera
) {

    private val aspectRatio = xSize / ySize.toDouble()
    private val gateWidth = tan(camera.horizontalFOV.toRadians() / 2.0) * 2.0
    private val gateHeight = gateWidth / aspectRatio

//    Always draws the first sample from the center of the pixel
    fun sampleCameraRays(xPixel: Int, yPixel: Int, samples: Int = 1): List<Ray> {
        return (1..samples).map { sampleCameraRay(xPixel, yPixel, if (it == 1) 0.0 else 1.0) }
    }

    private fun sampleCameraRay(xPixel: Int, yPixel: Int, dispersion: Double): Ray {

        val xSample = xPixel + Math.random() * dispersion - dispersion / 2.0
        val ySample = yPixel + Math.random() * dispersion - dispersion / 2.0

//        Find the point on the screen grid that the line needs to pass through
        val xPoint = Point(x = (gateWidth * (xSample / xSize.toDouble())) - gateWidth / 2.0)
        val yPoint = Point(z = (gateHeight * (ySample / ySize.toDouble())) - gateHeight / 2.0)

        return Ray(
            origin = Point.ZERO,
            direction = (Point.Y + xPoint + yPoint)
        ) apply camera.transform
    }
}

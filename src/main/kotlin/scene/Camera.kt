package scene

import scene.geometry.Transform

class Camera(
    val transform: Transform = Transform.ZERO,
    val horizontalFOV: Double = 60.0
)

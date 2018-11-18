package scene.geometry.volume

/**
 * Represents a hierarchical spatial volume
 */
interface Volume {
    val children: List<Volume>
}

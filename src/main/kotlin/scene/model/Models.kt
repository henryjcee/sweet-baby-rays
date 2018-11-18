package scene.model

import scene.geometry.primitive.Point
import scene.geometry.primitive.Polygon

fun backdrop(): List<Polygon> = listOf(
    Polygon(
        Point(0, 0, 0),
        Point(50, 0, 0),
        Point(0, -50, 0)
    ),
    Polygon(
        Point(0, 0, 0),
        Point(0, -50, 0),
        Point(0, 0, 50)
    ),
    Polygon(
        Point(0, 0, 0),
        Point(0, 0, 50),
        Point(50, 0, 0)
    )
)

fun mlem(): List<Polygon> = listOf(
    Polygon(
        Point(1, 0, 0),
        Point(0, -1, 0),
        Point(0, 0, 1)
    ),
    Polygon(
        Point(0, -1, 0),
        Point(1, 0, 0),
        Point(0, 0, -1)
    ),
    Polygon(
        Point(-1, 0, 0),
        Point(0, 0, 1),
        Point(0, -1, 0)
    ),
    Polygon(
        Point(0, 0, -1),
        Point(-1, 0, 0),
        Point(0, -1, 0)
    ),
    Polygon(
        Point(0, 1, 0),
        Point(0, 0, 1),
        Point(-1, 0, 0)
    ),
    Polygon(
        Point(0, 1, 0),
        Point(-1, 0, 0),
        Point(0, 0, -1)
    ),
    Polygon(
        Point(0, 0, 1),
        Point(0, 1, 0),
        Point(1, 0, 0)
    ),
    Polygon(
        Point(1, 0, 0),
        Point(0, 1, 0),
        Point(0, 0, -1)
    )
)

fun teapot(): List<Polygon> = parseObj("teapot.obj")

fun main(args: Array<String>) {
    doAthing(1)
}

fun doAthing(x: Int, op: (Int) -> Boolean = noop): Boolean {
    return op(x)
}

fun noop(x: Int) = false

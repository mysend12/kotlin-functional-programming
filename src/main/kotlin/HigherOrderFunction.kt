fun highOrder(func: (Int, Int) -> Int, x: Int, y: Int): Int = func(x, y)

fun higherOrderFunction1(func: () -> Unit): Unit {
    func()
}

fun higherOrderFunction2(): () -> Unit {
    return { println("Hello, Kotlin") }
}
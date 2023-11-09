interface Calcable {
    fun calc(x: Int, y: Int): Int
}

class Sum: Calcable {
    override fun calc(x: Int, y: Int): Int = x + y
}

class Minus: Calcable {
    override fun calc(x: Int, y: Int): Int = x - y
}

class Product: Calcable {
    override fun calc(x: Int, y: Int): Int = x * y
}

class TwiceSum: Calcable {
    override fun calc(x: Int, y: Int): Int = (x + y) * 2
}


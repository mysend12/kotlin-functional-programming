fun main() {
    val calcSum = Sum()
    val calcMinus = Minus()
    val calcProduct = Product()
    val calcTwiceSum = TwiceSum()

//    println(calcSum.calc(1, 5))
//    println(calcMinus.calc(5, 2))
//    println(calcProduct.calc(4, 2))
//    println(calcTwiceSum.calc(8, 2))

    val sum: (Int, Int) -> Int = { x, y -> x + y }
    val minus: (Int, Int) -> Int = { x, y -> x - y }
    val product: (Int, Int) -> Int = { x, y -> x * y }
    val twiceSum: (Int, Int) -> Int = { x, y -> (x + y) * 2 }

//    println(highOrder(sum, 1, 5))
//    println(highOrder(minus, 5, 2))
//    println(highOrder(product, 4, 2))
//    println(highOrder(twiceSum, 8, 2))

    val over10Values: ArrayList<Int> = ArrayList()
    val ints: List<Int> = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

//    for (element in ints) {
//        val twiceInt = element * 2
//        if (twiceInt > 10) {
//            over10Values.add(twiceInt)
//        }
//    }

//    println(over10Values)

    val result = ints.map { value -> value * 2 }
        .filter { value -> value > 10 }

    println(result)

}



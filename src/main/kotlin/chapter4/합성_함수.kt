package chapter4

import kotlin.math.abs

/**
 * 합성 함수: 함수를 매개변수로 받고, 함수르 ㄹ반환할 수 있는 고차 함수를 이용해서
 * 두 개의 함수를 결합하는 것
 */

fun main() {
    /**
     * 포인트 프리 스타일 프로그래밍
     * 함수 합성을 사용해서 매개변수나 타입 선언 없이 함수를 만드는 방식
     */
    infix fun <F, G, R> ((F) -> R).compose(g: (G) -> F): (G) -> R = { gInput: G -> this(g(gInput)) }
    fun addThree(i: Int) = i + 3
    fun twice(i: Int) = i * 2
//    fun composed(i: Int) = addThree(twice(i))

//    val addThree = { i: Int -> i + 3 }
//    val twice = { i: Int -> i * 2 }
//    val composedFunc = addThree compose twice
//    println(composedFunc(3))

    val absolute = { i: List<Int> -> i.map { abs(it) } }
    val negative = { i: List<Int> -> i.map { -it } }
    val minimum = { i: List<Int> -> i.min() }

//    println(minimum(negative(absolute(listOf(3, -1, 5, -2, -4, 8, 14)))))

    val composed = minimum compose negative compose absolute
    val result2 = composed(listOf(3, -1, 5, -2, -4, 8, 14))
//    println(result2)


    /**
     * 연습문제 4-5
     * 숫자(Int)의 리스트를 받아서 최댓값의 제곱을 구하는 함수
     * max함수와 power함수를 합성하여 만들어라.
     */
    val power = { x: Int -> x * x }
    val max = { i: List<Int> -> i.max() }
//    println(
//        power(
//            max(
//                listOf(
//                    1, 2, 3
//                )
//            )
//        )
//    )

    /**
     * 연습문제 4-6
     * 위 함수를 compose 함수를 사용해서 다시 작성해보자.
     */
    val maxPowerComposed = power compose max
    val maxPowerComposedResult = maxPowerComposed(listOf(1, 2, 3, 4))
//    println(maxPowerComposedResult)
//    println(
//        ({ x: Int -> x * x } compose { i: List<Int> -> i.max() })(listOf(1, 2))
//    )

    tailrec fun gcd(m: Int, n: Int): Int = when (n) {
        0 -> m
        else -> gcd(n, m % n)
    }

    tailrec fun power(x: Double, n: Int, acc: Double = 1.0): Double = when (n) {
        0 -> acc
        else -> power(x, n - 1, x * acc)
    }

    /**
     * 코드 4-31
     * 하나 이상의 매개변수를 받는 함수의 합성
     */
    val powerOfTwo = { x: Int -> power(x.toDouble(), 2).toInt() }
    val gcdPowerOfTwo = { x1: Int, x2: Int -> gcd(powerOfTwo(x1), powerOfTwo(x2)) }
//    println(gcdPowerOfTwo(25, 5))

    /**
     * 코드 4-32
     * compose를 사용해서 제곱 함수와 합성 -> 잘못된 코드
     * ::는 함수의 레퍼런스를 얻기 위한 키워드이다. gcd.curried()와 같은 식으로 호출되지 않는다.
     * ::gcd는 {(m: Int, n: Int) -> gcd(m, n)} 과 동일하다.
     */
    // 잘못된 코드
    val curriedGcd1 = ::gcd.curried()
    val composedGcdPowerOfTwo1 = curriedGcd1 compose powerOfTwo
//    println(composedGcdPowerOfTwo1(25)(5))

    /**
     * 코드 4-33
     * compose를 사용해서 제곱 함수와 합성 -> 원하는 코드
     *
     * 이는 위 32의 문제가 해결되었지만, 좋은 코드라고 보긴 어렵다.
     * 여러 개의 매개변수에 동일한 함수를 적용해야 할 때, 함수 합성을 사용하는 것은 적합하지 않다.
     * 차라리 4-30과 같이 일반적인 고차 함수로 연결하는 것이 좋다.
     */
    val curriedGcd2 = { m: Int, n: Int -> gcd(m, powerOfTwo(n)) }.curried()
    val composedGcdPowerOfTwo2 = curriedGcd2 compose powerOfTwo
    println(composedGcdPowerOfTwo2(25)(5))
}




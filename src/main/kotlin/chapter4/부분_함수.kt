package chapter4

/**
 * 부분함수: 모든 가능한 입력 중, 일부 입력에 대한 결과만 정의한 함수
 *
 * 물론, 부분함수를 만들어야 하는 상황을 만들지 않는 것이 가장 좋다.
 * - 함수형 프로그래밍에서는 가급적 모든 입력에 대한 결과를 정의하는것이 좋다.
 * - 특정 입력에 대해 예외를 만들면 프로그램의 동작을 예측하기 어려우며 컴파일된 코드가 실제로 동작하지 않을 가능성이 있다.
 *
 * 장점:
 * - 호출하는 쪽에서 함수가 호출 전, 함수가 정상적으로 동작하는지 미리 확인 가능
 * - 호출자가 함수가 던지는 예외나 오류값에 대해 몰라도 된다.
 * - 부분 함수의 조합으로 부분 함수 자체를 재사용하거나 확장할 수 있다.
 *
 * -------------------------
 *
 * 부분 적용함수: 부분함수와는 관계가 없다.
 * 매개변수의 일부만 전달할 수도 있고, 아예 전달하지 않을 수도 있다.
 * 이렇게 매개변수의 일부만 전달받았을때, 제공받은 매개변수만 가지고 부분 적용 함수를 생성한다.
 *
 * 부분 적용함수의 필요성
 * - 코드를 재사용하기 위해서 사용할 수도 있지만, 커링 함수를 위해서도 필요한 개념이다.
 */
class PartialFunction<in P, out R>(
    private val condition: (P) -> Boolean,
    private val f: (P) -> R,
) : (P) -> R {
    override fun invoke(p: P): R = when {
        condition(p) -> f(p)
        else -> throw IllegalArgumentException("$p isn't supported.")
    }

    fun isDefinedAt(p: P): Boolean = condition(p)
    fun invokeOrElse(p: P, default: @UnsafeVariance R): R = when {
        condition(p) -> f(p)
        else -> default
    }

    fun orElse(p: P, that: PartialFunction<@UnsafeVariance P, @UnsafeVariance R>): PartialFunction<P, R> = when {
        condition(p) -> this
        else -> that
    }
}

fun <P, R> ((P) -> R).toPartialFunction(definedAt: (P) -> Boolean)
        : PartialFunction<P, R> = PartialFunction(definedAt, this)

fun <P1, P2, R> ((P1, P2) -> R).partial1(p1: P1): (P2) -> R {
    return { p2 -> this(p1, p2) }
}

fun <P1, P2, R> ((P1, P2) -> R).partial2(p2: P2): (P1) -> R {
    return { p1 -> this(p1, p2) }
}

fun main() {
//    val condition: (Int) -> Boolean = { it in 1..3 }
//    val body: (Int) -> String = {
//        when (it) {
//            1 -> "One"
//            2 -> "Two"
//            3 -> "Three"
//            else -> throw IllegalArgumentException()
//        }
//    }
//
//    val oneTwoThree = chapter4.PartialFunction(condition, body)
//    if (oneTwoThree.isDefinedAt(3))
//        print(oneTwoThree(3))
//    else
//        print("isDefinedAt(x) return false")

//    println()

//    val isEven = chapter4.PartialFunction<Int, String>({ it % 2 == 0 }, { "$it is even" })
//    if (isEven.isDefinedAt(100))
//        print(isEven(100))
//    else
//        print("isDefinedAt(x) return false")

//    val condition: (Int) -> Boolean = { it.rem(2) == 0 }
//    val body: (Int) -> String = { "$it is even" }
//
//    val isEven = body.chapter4.toPartialFunction(condition)
//
//    if (isEven.isDefinedAt(101))
//        print(isEven(101))
//    else
//        print("isDefinedAt(x) return false")

    val func = { a: String, b: String -> a + b }

    val partiallyAppliedFunc1 = func.partial1("Hello")
    val result1 = partiallyAppliedFunc1("World")

    println(result1)

    val partiallyAppliedFunc2 = func.partial2("World")
    val result2 = partiallyAppliedFunc2("Hello")

    println(result2)
}

class 부분_함수 {

    fun twice(x: Int) = x * 2

    // 100보다 작은 경우에만 두 배 -> x 값은 twice 함수의 x 값의 부분 집합 -> partialTwice는 twice의 부분 함수
    fun partialTwice(x: Int): Int =
        if (x < 100) {
            x * 2
        } else {
            throw IllegalArgumentException()
        }

    // 모든 입력값에 대한 결과를 정의 -> 부분함수가 아님.
    fun sayNumber1(x: Int): String = when (x) {
        1 -> "One"
        2 -> "Two"
        3 -> "Three"
        else -> "Not between 1 and 3"
    }

    // 1, 2, 3 이외의 값은 예외 -> 부분함수
    fun sayNumber2(x: Int): String = when (x) {
        1 -> "One"
        2 -> "Two"
        3 -> "Three"
        else -> throw IllegalArgumentException()
    }
}
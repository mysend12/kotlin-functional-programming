/**
 * 커링함수: 여러 개의 매개변수를 받는 함수를 분리하여,
 *        단일 매개변수를 받는 부분 적용 함수의 체인으로 만드는 방법
 *
 * 장점: 부분 적용 함수를 다양하게 재사용할 수 있다.
 * 마지막 매개변수가 입력될 때까지 함수의 실행을 늦출 수 있다.
 *
 */

fun multiThree(a: Int, b: Int, c: Int): Int = a * b * c
fun multiThree(a: Int) = { b: Int -> { c: Int -> a * b * c } }

/**
 * 연습문제 4-3
 * 두 개의 매개변수를 받아서 큰 값을 반환하는 max 함수를,
 * 커링을 사용할 수 있도록 구현하라.
 */
fun max(a: Int) = { b: Int -> a.coerceAtLeast(b) }

fun <P1, P2, R> ((P1, P2) -> R).curried(): (P1) -> (P2) -> R =
    { p1: P1 -> { p2: P2 -> this(p1, p2) } }

fun <P1, P2, P3, R> ((P1, P2, P3) -> R).curried(): (P1) -> (P2) -> (P3) -> R =
    { p1: P1 -> { p2: P2 -> { p3: P3 -> this(p1, p2, p3) } } }

fun <P1, P2, P3, R> ((P1) -> (P2) -> (P3) -> R).unCurried(): (P1, P2, P3) -> R =
    { p1: P1, p2: P2, p3: P3 -> this(p1)(p2)(p3) }

fun <P1, P2, R> ((P1) -> (P2) -> R).unCurried(): (P1, P2) -> R =
    { p1: P1, p2: P2 -> this(p1)(p2) }


fun main() {
    println(multiThree(1, 2, 3))

    val partial1 = multiThree(1)
    println(partial1)
    val partial2 = partial1(2)
    println(partial2)
    val partial3 = partial2(3)
    println(partial3)

    println(multiThree(1)(2)(3))
    println("------------------------------")

    // 연습문제 4-3
    println("연습문제 4-3")
    val mavVal = max(3)
    val max2 = mavVal(4)
    println(max2)
    println("------------------------------")

    val multiThree = { a: Int, b: Int, c: Int -> a * b * c }
    val curried = multiThree.curried()
    println(curried(1)(2)(3))

    val unCurried = curried.unCurried()
    println(unCurried(1, 2, 3))
    println("------------------------------")


    /**
     * 연습문제 4-4
     * 두 개의 매개변수를 받아서 작은 값을 반환하는 min 함수를,
     * curried 함수를 사용해서 작성하라.
     */
    println("연습문제 4-4")
    val min = { a: Int, b: Int -> a.coerceAtMost(b) }
    val curriedMin = min.curried()

    println(curriedMin(10)(20))
    val unCurriedMin = curriedMin.unCurried()
    println(unCurriedMin(10, 20))
    println("------------------------------")


}

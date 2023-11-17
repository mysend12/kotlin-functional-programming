package chapter4

import head
import tail

fun main() {
    /**
     * zipWith 함수
     * 코드를 작성할 때 자주 사용되는 패턴을 추상화하기 위해 고차 함수를 사용한다.
     */

    tailrec fun <P1, P2, R> zipWith(
        func: (P1, P2) -> R, list1: List<P1>, list2: List<P2>, acc: List<R> = listOf()
    ): List<R> = when {
        list1.isEmpty() || list2.isEmpty() -> acc
        else -> {
            val zipList = acc + listOf(func(list1.head(), list2.head()))
            zipWith(func, list1.tail(), list2.tail(), zipList)
        }
    }
//
//    val list1 = listOf(6, 3, 2, 1, 4)
//    val list2 = listOf(7, 4, 2, 6, 3)
//
//    val add = { p1: Int, p2: Int -> p1 + p2 }
//    val result1 = zipWith(add, list1, list2)
//    println(result1)
//
//    val max = { p1: Int, p2: Int -> p1.coerceAtLeast(p2) }
//    val result2 = zipWith(max, list1, list2)
//    println(result2)
//
//    val strcat = { p1: String, p2: String -> p1 + p2 }
//    val result3 = zipWith(strcat, listOf("a", "b"), listOf("c", "d"))
//    println(result3)
//
//    val product = { p1: Int, p2: Int -> p1 * p2 }
//    val result4 = zipWith(product, list1, (1..4).toList())
//    println(result4)

    /**
     * 연습문제 4-7
     * 리스트의 값을 조건 함수에 적용했을 때, 결괏값이 참인 값의 리스트를 반환하는 takeWhile 함수를 꼬리 재귀로 작성해보자.
     * 리스트가 1, 2, 3, 4, 5일 때, 조건 함수가 3보다 작은 값이면 1과 2로 구성된 리스트를 반환한다.
     */
//    tailrec fun <P> takeWhile(
//        func: (P) -> Boolean,
//        list: List<P>,
//        acc: List<P> = listOf()
//    ): List<P> = when {
//        list.isEmpty() || !func(list.head()) -> acc
//        else -> {
//            takeWhile(
//                func, list.tail(),
//                acc + list.head()
//            )
//        }
//    }
//    takeWhile({ i: Int -> i < 3 }, listOf(1, 2, 3, 4, 5)).forEach { println(it) }

    /**
     * 연습문제 4-8. takeWhile 을 수정하여, 무한대를 입력받을 수 있는 takeWhile 을 꼬리 재귀로 작성해보자.
     */
    tailrec fun <P> takeWhile(
        predicate: (P) -> Boolean, sequence: Sequence<P>, acc: List<P> = listOf()
    ): List<P> = when {
        sequence.none() || !predicate(sequence.head()) -> acc
        else -> {
            takeWhile(
                predicate, sequence.tail(), acc + sequence.head()
            )
        }
    }
    println(takeWhile({ p -> p < 10 }, generateSequence(1) { it + 1 }))   // [1, 2]

    /**
     * 코드 4-37. 고차 함수와 커링을 사용하여 콜백지옥 개선
     */
    val callback: (String) -> (String) -> (String) -> (String) -> (String) -> String = { v1 ->
        { v2 -> { v3 -> { v4 -> { v5 -> v1 + v2 + v3 + v4 + v5 } } } }
    }
    val result = callback("1")("2")("3")("4")("5")
    println(result)

    /**
     * 코드 4-38. 부분 적용 함수를 만들어서 재사용한 예
     */
    val partialApplied = callback("prefix")(":")

    println(partialApplied("1")("2")("3"))
    println(partialApplied("a")("b")("c"))
}


package chapter5

/**
 * 명령형 방식과 함수형 방식의 기능 비교
 */





fun main() {
//    println(
//        imperativeWay(listOf(1, 2, 3, 4, 5))
//    )
//
//    println(
//        functionWay(listOf(1, 2, 3, 4, 5))
//    )
    /**
     * 명령형 방식과 함수형 방식의 성능 비교
     *
     * 명령형으로 처리한 함수가 좋은 성능을 보인다.
     * imperativeWay 함수는 제곱 연산 1회, 비교 연산 1회를 하고 바로 1 반환
     * functionalWay 함수는 5개의 값에 대해서 모두 map을 수행한 후, filter를 수행하고 first 함수를 실행한다.
     * 즉, 11회의 연산이 수행된다.
     *
     * 코틀린의 모든 컬렉션에서 동일한 성능 문제가 발생하므로, 다음과 같은 상황에서는 컬렉션을 사용하면 안 된다.
     * - 성능에 민감한 프로그램을 작성할 때
     * - 컬렉션의 크기가 고정되어 있지 않을 때
     * - 고정된 컬렉션 크기가 매우 클 때
     *
     * 코틀린의 컬렉션은 기본적으로 값이 즉시 평가된다. 게으른 평가로 실행되지 않기 때문에 성능이 떨어진다.
     * 따라서, 게으른 평가를 위한 컬렉션을 코틀린에서는 시퀀스(sequence)로 제공한다.
     */
    fun imperativeWay(intList: List<Int>): Int {
        for (value in intList) {
            val doubleValue = value * value
            if (doubleValue < 10) {
                return doubleValue
            }
        }
        throw NoSuchElementException()
    }
    val bigIntList = (1..100000000).toList()
    var start = System.currentTimeMillis()
    imperativeWay(bigIntList)
    println("${System.currentTimeMillis() - start} ms")

    fun functionWay(intList: List<Int>): Int = intList.map { n -> n * n }.first { n -> n < 10 }
    start = System.currentTimeMillis()
    functionWay(bigIntList)
    println("${System.currentTimeMillis() - start} ms")

    /**
     * 시퀀스로 작성한 함수의 성능 비교
     * realFunctionalWay 함수는 functionWay 보다는 imperativeWay 함수와 성능이 비슷하다.
     */
    fun realFunctionalWay(intList: List<Int>): Int = intList.asSequence()
        .map { n -> n * n }
        .filter { n -> n < 10 }
        .first()
    start = System.currentTimeMillis()
    realFunctionalWay(bigIntList)
    println("${System.currentTimeMillis() - start} ms")
}


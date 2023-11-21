package chapter5

/**
 * 연습문제 5-13. FunList 에 zip 함수를 작성해보자.
 */
tailrec fun <T, R> FunList<T>.zip(
    other: FunList<R>, acc: FunList<Pair<T, R>> = FunList.Nil
): FunList<Pair<T, R>> = when {
    this == FunList.Nil && other == FunList.Nil -> acc.reverse()
    else -> getTail().zip(other.getTail(), acc.addHead(getHead() to other.getHead()))
}

/**
 * 코드 5-30. zipWith 함수
 */
tailrec fun <T1, T2, R> FunList<T1>.zipWith(
    f: (T1, T2) -> R, list: FunList<T2>, acc: FunList<R> = FunList.Nil
): FunList<R> = when {
    this === FunList.Nil || list === FunList.Nil -> acc.reverse()
    else -> getTail().zipWith(
        f, list.getTail(), acc.addHead(
            f(
                getHead(), list.getHead()
            )
        )
    )
}

/**
 * 연습문제 5-14. zip 함수는 리스트와 리스트를 조합해서 리스트를 생성하는 함수다.
 * 리스트의 값을 입력받은 조합 함수에 의해서 연관 자료구조인 맵을 생성하는 associate 함수를 작성해보자.
 */
fun <T, R> FunList<T>.associate(f: (T) -> Pair<T, R>): Map<T, R> = foldRight(mapOf()) { value, acc ->
    acc.plus(f(value))
}

/**
 * 연습문제 5-15. FunList 의 값들을 입력받은 키 생성 함수를 기준으로 맵을 생성하는 groupBy 함수를 작성해보자.
 */
fun <T, K> FunList<T>.groupBy(f: (T) -> K): Map<K, FunList<T>> = foldRight(emptyMap()) { value, acc ->
    acc.plus(
        f(value) to (acc.getOrElse(f(value)) { funListOf() }.addHead(value))
    )
}

fun main() {

    /**
     * 코드 5-31. zipWith 함수 사용 예
     */
    val intList = funListOf(1, 2, 3)
    val intList2 = funListOf(1, 3, 10)
    val lowerCharList = funListOf('a', 'b', 'c')


}




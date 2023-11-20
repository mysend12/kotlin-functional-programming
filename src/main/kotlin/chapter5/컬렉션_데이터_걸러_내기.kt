package chapter5

/**
 * 명령형 방식 vs 함수혀여 방식
 *
 */

// 리스트 내의 값을 걸러 내는 명령형 방식의 예
fun imperativeFilter(numList: List<Int>): List<Int> {
    val newList = mutableListOf<Int>()
    for (num in newList) {
        if (num % 2 == 0) {
            newList.add(num)
        }
    }

    return newList
}

// 리스트 내의 값을 걸러 내는 함수형 방식의 예
fun functionalFilter(numList: List<Int>) = numList.filter { it % 2 == 0 }

/**
 * 함수형 방식의 장점
 *
 * 코드가 간결해져 가독성이 좋다.
 * 결괏값을 저장하기 위해 별도의 리스트를 생성할 필요가 없다.
 * 비즈니스 로직에 집중할 수 있다.
 * 버그가 발생할 확률이 적다.
 * 테스트가 용이하다.
 * 유지보수가 용이하다.
 */

/**
 * FunList 에 filter 함수 추가
 */
tailrec fun <T> FunList<T>.filter(acc: FunList<T> = FunList.Nil, p: (T) -> Boolean): FunList<T> = when (this) {
    FunList.Nil -> acc.reverse()
    is FunList.Cons -> if (p(head)) {
        tail.filter(acc.addHead(head), p)
    } else {
        tail.filter(acc, p)
    }
}

/**
 * 연습문제 5-4
 * 주어진 리스트에서 앞의 값이 n개 제외된 리스트를 반환하는 drop 함수를 구현하자.
 * 원본 리스트가 바뀌지 않아야 하고, 새로운 리스트를 반환할 때마다 리스트를 생성하면 안 된다.
 */
tailrec fun <T> FunList<T>.drop(n: Int): FunList<T> = when {
    n < 0 -> throw IllegalArgumentException()
    n == 0 || this == FunList.Nil -> this
    else -> getTail().drop(n - 1)
}

/**
 * 연습문제 5-5
 * dropWhile 함수를 구현하자. 타입 T를 입력받아 Boolean 을 반환하는 함수 p를 입력받는다.
 * 함수 p를 만족하기 전까지 drop 하고, 나머지 값들의 리스트를 반환한다.
 * 원본 리스트가 바뀌지 않아야 하고, 새로운 리스트를 반환할 때마다 리스트를 생성하면 안된다.
 */
tailrec fun <T> FunList<T>.dropWhile(p: (T) -> Boolean): FunList<T> = when (this) {
    FunList.Nil -> this
    is FunList.Cons -> if (p(head)) {
        this
    } else {
        tail.dropWhile(p)
    }
}

/**
 * 연습문제 5-6
 * 리스트의 앞에서부터 n개의 값을 가진 리스트를 반환하는 take 함수를 구현하자.
 * 원본 리스트가 바뀌지 않아야 하고, 새로운 리스트를 반환할 때마다 리스트를 생성하면 안된다.
 */
tailrec fun <T> FunList<T>.take(n: Int, acc: FunList<T> = FunList.Nil): FunList<T> = when {
    n < 0 -> throw IllegalArgumentException()
    n == 0 || this == FunList.Nil -> acc.reverse()
    else -> getTail().take(n - 1, acc.addHead(getHead()))
}

/**
 * 연습문제 5-7
 * 타입 T를 입력 받아 Boolean 을 반환하는 함수 p를 받는다.
 * 리스트의 앞에서부터 함수 p를 만족하는 값들의 리스트를 반환한다.
 * 모든 값이 함수 p를 만족하지 않는다면, 원본 List를 반환한다.
 * 원본 리스트가 바뀌지 않아야 하고, 새로운 리스트를 반환할 때마다 리스트를 생성하면 안된다.
 */
tailrec fun <T> FunList<T>.takeWhile(
    acc: FunList<T> = FunList.Nil,
    p: (T) -> Boolean
): FunList<T> = when (this) {
    FunList.Nil -> acc.reverse()
    is FunList.Cons -> when (p(head)) {
        true -> acc.reverse()
        false -> getTail().takeWhile(acc.addHead(head), p)
    }
}

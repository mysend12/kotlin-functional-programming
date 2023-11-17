package chapter5

sealed class FunList<out T> {
    object Nil : FunList<Nothing>()
    data class Cons<out T>(val head: T, val tail: FunList<T>) : FunList<T>()
}

fun <T> FunList<T>.addHead(head: T): FunList<T> = FunList.Cons(head, this)


/**
 * 꼬리 재귀로 작성한 appendTail 함수는 재귀를 수행하기 위해 한 번 리스트를 순회한다.
 * 리스트의 각 Cons를 순회할 때마다 addHead 함수를 사용한다.
 * addHead 함수는 O(1)에 수행이 가능하기 때문에 성능에 영향을 미치지 않는다.
 * reverse 함수도 수행이 완료되기 위해서 리스트를 한 바퀴 순회한다. O(n)
 * 그러나, appendTail 함수에서는 마지막에 한 번만 호출되기 때문에 총 수행 시간은 O(2n)이고, 결과적으로 O(n)이라고 할 수 있다.
 *
 * addHead를 사용해서 리스트를 거꾸로 만들고, 마지막에 reverse를 사용해서 뒤집는 방법은,
 * 스택에 안전하면서도 성능상에 손해가 없다.
 */
tailrec fun <T> FunList<T>.appendTail(value: T, acc: FunList<T> = FunList.Nil): FunList<T> =
    when (this) {
        FunList.Nil -> FunList.Cons(value, FunList.Nil)
        is FunList.Cons -> tail.appendTail(value, acc.addHead(head))
    }

tailrec fun <T> FunList<T>.reverse(acc: FunList<T> = FunList.Nil): FunList<T> = when (this) {
    FunList.Nil -> acc
    is FunList.Cons -> tail.reverse(acc.addHead(head))
}

fun <T> FunList<T>.getTail(): FunList<T> = when (this) {
    FunList.Nil -> throw NoSuchElementException()
    is FunList.Cons -> tail
}

/**
 * 연습문제 5-3
 * 리스트의 첫 번째 값을 가져오는 getHead 함수를 작성해보자.
 */
fun <T> FunList<T>.getHead(): T = when (this) {
    FunList.Nil -> throw NoSuchElementException()
    is FunList.Cons -> head
}

fun main() {
    /**
     * 연습문제 5-1
     * FunList를 사용해서 [1, 2, 3, 4, 5]를 가지는 intList를 생성하자.
     */
    val intList = FunList.Cons(1, FunList.Cons(2, FunList.Cons(3, FunList.Cons(4, FunList.Cons(5, FunList.Nil)))))

    /**
     * 연습문제 5-2
     * FunList를 사용해서 [1.0, 2.0, 3.0, 4.0, 5.0]을 사지는 doubleList를 생성하자.
     */
    val doubleList =
        FunList.Cons(1.0, FunList.Cons(2.0, FunList.Cons(3.0, FunList.Cons(4.0, FunList.Cons(5.0, FunList.Nil)))))
}



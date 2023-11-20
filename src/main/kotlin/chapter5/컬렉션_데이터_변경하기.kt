package chapter5

/**
 * 명령형 방식 vs 함수형 방식
 */
// 리스트 내의 값을 변경하는 명령형 방식의 예
fun imperativeMap(numList: List<Int>): List<Int> {
    val newList = mutableListOf<Int>()
    for (num in numList) {
        newList.add(num + 2)
    }
    return newList
}

// 리스트 내의 값을 변경하는 함수형 방식의 예
fun functionalMap(numList: List<Int>): List<Int> = numList.map { it + 2 }

fun add3(list: FunList<Int>): FunList<Int> = when (list) {
    FunList.Nil -> FunList.Nil
    is FunList.Cons -> FunList.Cons(list.head + 3, add3(list.tail))
}

fun product3(list: FunList<Double>): FunList<Double> = when (list) {
    FunList.Nil -> FunList.Nil
    is FunList.Cons -> FunList.Cons(list.head * 3, product3(list.tail))
}

tailrec fun <T, R> FunList<T>.map(acc: FunList<R> = FunList.Nil, f: (T) -> R): FunList<R> = when (this) {
    FunList.Nil -> acc.reverse()
    is FunList.Cons -> tail.map(acc.addHead(f(head)), f)
}

fun <T> funListOf(vararg elements: T): FunList<T> = elements.toFunList()

private fun <T> Array<out T>.toFunList(): FunList<T> = when {
    this.isEmpty() -> FunList.Nil
    else -> FunList.Cons(this[0], this.copyOfRange(1, this.size).toFunList())
}

fun main() {
    val intList = funListOf(1, 2, 3)
    val doubleList = funListOf(1.0, 2.0, 3.0)
}

/**
 * 연습문제 5-8
 * 앞서 작성한 map 함수에서 고차 함수가 값들의 순서값(index)도 같이 받아 올 수 있는 indexMap 함수를 만들자.
 */
tailrec fun <T, R> FunList<T>.indexedMap(
    index: Int = 0,
    acc: FunList<R> = FunList.Nil,
    f: (Int, T) -> R
): FunList<R> = when (this) {
    FunList.Nil -> acc.reverse()
    is FunList.Cons -> tail.indexedMap(index + 1, acc.addHead(f(index, head)), f)
}





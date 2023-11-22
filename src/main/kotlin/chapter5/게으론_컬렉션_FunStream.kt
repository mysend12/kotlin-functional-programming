package chapter5

import java.lang.IllegalArgumentException

/**
 * 게으른 평가가 가능한 FunStream
 * FunList와의 차이점은 입력 매개변수를 람다로 받았다는 것 뿐이다.
 * Cons가 생성되는 시점에 입력 매개변수는 평가되지 않는다.
 * 실제로 값이 평가되는 시점은, 그 값이 정말 필요할 때다.
 */
sealed class FunStream<out T> {
    object Nil : FunStream<Nothing>()

    //    data class Cons<out T>(val head: () -> T, val tail: () -> FunStream<T>) : FunStream<T>()
    data class Cons<out T>(val head: () -> T, val tail: () -> FunStream<T>) : FunStream<T>() {
        override fun equals(other: Any?): Boolean = if (other is Cons<*>) {
            if (head() == other.head()) {
                tail() == other.tail()
            } else {
                false
            }
        } else {
            false
        }

        override fun hashCode(): Int = 31 * head.hashCode() + tail.hashCode()
    }
}

fun <T> FunStream<T>.getHead(): T = when (this) {
    FunStream.Nil -> throw NoSuchElementException()
    is FunStream.Cons -> head()
}

fun <T> FunStream<T>.getTail(): FunStream<T> = when (this) {
    FunStream.Nil -> throw NoSuchElementException()
    is FunStream.Cons -> tail()
}

fun <T> funStreamOf(vararg elements: T): FunStream<T> = elements.toFunStream()

private fun <T> Array<out T>.toFunStream(): FunStream<T> = when {
    this.isEmpty() -> FunStream.Nil
    else -> FunStream.Cons({ this[0] }, { this.copyOfRange(1, this.size).toFunStream() })
}

tailrec fun <T, R> FunStream<T>.foldLeft(acc: R, f: (R, T) -> R): R = when (this) {
    FunStream.Nil -> acc
    is FunStream.Cons -> getTail().foldLeft(
        f(acc, getHead()), f
    )
}

/**
 * 연습문제 5-17. FunList 에서 작성했던 sum 함수를 FunStream 에도 추가하자.
 */
fun FunStream<Int>.sum(): Int = foldLeft(0) { acc, value -> acc + value }


/**
 * 연습문제 5-18. FunList 에서 작성했던 product 함수를 FunStream 에도 추가하자.
 */
fun FunStream<Int>.product(): Int = foldLeft(1) { acc, value -> acc * value }


/**
 * 연습문제 5-19. FunList 에서 작성했던 appendTail 함수를 FunStream 에도 추가하자.
 */
fun <T> FunStream<T>.appendTail(value: T): FunStream<T> = when (this) {
    FunStream.Nil -> FunStream.Cons({ value }, { this })
    is FunStream.Cons -> FunStream.Cons(head) { tail().appendTail(value) }
}

tailrec fun <T> FunStream<T>.dropWhile(f: (T) -> Boolean): FunStream<T> = when (this) {
    FunStream.Nil -> this
    is FunStream.Cons -> if (f(head())) {
        this
    } else {
        tail().dropWhile(f)
    }
}

/**
 * 연습문제 5-20. FunList에서 작성했던 filter 함수를 FunStream 에도 추가하자.
 */
fun <T> FunStream<T>.filter(p: (T) -> Boolean): FunStream<T> = when (this) {
    FunStream.Nil -> FunStream.Nil
    is FunStream.Cons -> {
        val first = dropWhile(p)
        if (first != FunStream.Nil) {
            FunStream.Cons({ first.getHead() }, { first.getTail().filter(p) })
        } else {
            FunStream.Nil
        }
    }
}

/**
 * 연습문제 5-21. FunList 에서 작성했던 map 함수를 FunList 에도 추가하자.
 */
fun <T, R> FunStream<T>.map(f: (T) -> R): FunStream<R> = when (this) {
    FunStream.Nil -> FunStream.Nil
    is FunStream.Cons -> FunStream.Cons({ f(head()) }, { tail().map(f) })
}

fun IntProgression.toFunStream(): FunStream<Int> = when {
    step > 0 -> when {
        first > last -> FunStream.Nil
        else -> FunStream.Cons({ first }, { ((first + step)..last step step).toFunStream() })
    }

    else -> when {
        first >= last -> {
            FunStream.Cons({ first },
                { IntProgression.fromClosedRange(first + step, last, step).toFunStream() })
        }

        else -> {
            FunStream.Nil
        }
    }
}

/**
 * 코드 5-42. FunStream 으로 무한대 값 만들기
 * FunList는 게으른 컬렉션이 아니기 때문에 무한대 값을 자료구조 안에 담을 수 없다.
 * 컬렉션이 어떤 값으로 평가되는 시점에는 메모리에 저장되어야 하는데, 무한대 값을 메모리에 저장할 수 없다.
 *
 * 반면, FunStream 은 게으른 컬렉션이기 때문에 생성된 후에도 값이 평가되기 전까지는 메모리에 올라가지 않는다.
 */
fun <T> generateFunStream(seed: T, generate: (T) -> T): FunStream<T> =
    FunStream.Cons({ seed }, { generateFunStream(generate(seed), generate) })

tailrec fun <T> FunStream<T>.forEach(f: (T) -> Unit): Unit = when (this) {
    FunStream.Nil -> Unit
    is FunStream.Cons -> {
        f(head())
        tail().forEach(f)
    }
}

/**
 * 연습문제 5-22. FunStream 에서 필요한 값을 가져오는 take 함수를 추가하자.
 * FunStream 은 무한대를 표현한 컬렉션이다. take 함수를 사용하여 값을 5개 가져온 후 합계를 구해보자.
 */
fun <T> FunStream<T>.take(n: Int): FunStream<T> = when {
    n < 0 -> throw IllegalArgumentException()
    n == 0 -> FunStream.Nil
    else -> when (this) {
        FunStream.Nil -> FunStream.Nil
        is FunStream.Cons -> FunStream.Cons(head) { tail().take(n - 1) }
    }
}

fun main() {

    /**
     * 코드 5-41. FunList 와 FunStream 성능 비교
     */
    fun funListWay(intList: FunList<Int>): Int = intList.map { n -> n * n }
        .filter { n -> n < 10000000 }
        .map { n -> n - 2 }
        .filter { n -> n < 1000 }
        .map { n -> n * 10 }
        .getHead()

    fun funStreamWay(intList: FunStream<Int>): Int = intList.map { n -> n * n }
        .filter { n -> n < 10000000 }
        .map { n -> n - 2 }
        .filter { n -> n < 1000 }
        .map { n -> n * 10 }
        .getHead()

    println(funStreamOf(1, 2, 3).getHead())
    println(funStreamOf(1, 2, 3).getTail())

    val bigIntList = (1..10000000).toFunList()
    var start = System.currentTimeMillis()
    funListWay(bigIntList)
    println("${System.currentTimeMillis() - start} ms")

    val bigIntStream = (1..10000000).toFunStream()
    start = System.currentTimeMillis()
    funStreamWay(bigIntStream)
    println("${System.currentTimeMillis() - start} ms")

    /**
     * 무한대 값을 담은 infiniteVal
     * 0에서부터 5씩 증가하는 무한대 값을 가지고 있다.
     * 평가가 일어나는 시점에 해당 값을 생성하기 위해 어떤 일들을 해야 하는지만 기록하고 있다.
     */
    val infiniteVal = generateFunStream(0) { it + 5 }
    infiniteVal.forEach { println(it) }


}


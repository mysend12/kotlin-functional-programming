package chapter5

/**
 * FunList 에 printFunList 함수 추가하기
 *
 * FunList 를 입력으로 받아서 구성 값들을 [1, 2, 3] 과 같은 형태로 출력
 * FunList<T> 의 확장 함수로 작성
 * 꼬리 재귀 호출로 작성
 *
 * 연습문제 5-23 FunList 에 toString 함수를 추가해보자.
 */
tailrec fun <T> FunList<T>.toString(acc: String): String = when (this) {
    FunList.Nil -> "[${acc.drop(2)}"
    is FunList.Cons -> tail.toString("$acc, $head")
}

/**
 * 코드 5-45 if 문을 사용해서 작성한 toString 함수
 */
tailrec fun <T> FunList<T>.toString1(acc: String = ""): String = when (this) {
    FunList.Nil -> "[$acc]"
    is FunList.Cons -> if (acc.isEmpty()) {
        tail.toString1("$head")
    } else {
        tail.toString1("$acc, $head")
    }
}

/**
 * 코드 5-46. 종료조건을 사용해서 개선한 toString 함수
 */
tailrec fun <T> FunList<T>.toString2(acc: String = ""): String = when (this) {
    FunList.Nil -> "[${acc.drop(2)}]"
    is FunList.Cons -> tail.toString2("$acc, $head")
}

/**
 * 코드 5-47. foldLeft 를 사용한 toString 함수
 */
fun <T> printFunList(list: FunList<T>) = list.toStringByFoldLeft()

fun <T> FunList<T>.toStringByFoldLeft(): String =
    "[${foldLeft("") { acc, x -> "$acc, $x" }.drop(2)}]"

/**
 * 연습문제 5-24.
 * 모든 자연수의 제곱근의 합이 1000을 넘으려면
 * 자연수가 몇 개 필요한지 계산하는 함수를 작성해보자.
 */
tailrec fun squareRoot(num: Int = 1, acc: Int = 0): Int = when {
    acc > 1000 -> num - 1
    else -> squareRoot(num + 1, num * num + acc)
}


fun main() {
    println(funListOf(1, 2, 3, 4).toString1())
    println(funListOf(1, 2, 3, 4).toString2())
    println(squareRoot())
}
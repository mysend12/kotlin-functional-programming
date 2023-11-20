    package chapter5

    /**
     * sum 함수: 내부적으로 원본 리스트의 값들을 하나의 값으로 줄이는 과정을 반복
     */
    //fun sum(list: FunList<Int>): Int = when (list) {
    //    FunList.Nil -> 0
    //    is FunList.Cons -> list.head + sum(list.tail)
    //}

    /**
     * foldLeft 함수 만들기
     * 폴드 함수: 하나의 값으로 줄이는 작업을 일반화하여 만든 고차 함수
     *
     * 컬렉션의 값들을 왼쪽에서부터 오른쪽으로 줄여 나가는 함수를 foldLeft 라 한다.
     */
    tailrec fun <T, R> FunList<T>.foldLeft(acc: R, f: (R, T) -> R): R = when (this) {
        FunList.Nil -> acc
        is FunList.Cons -> tail.foldLeft(
            f(acc, head), f
        )
    }

    fun sumByFoldLeft(list: FunList<Int>): Int = list.foldLeft(0) { acc, x -> acc + x }

    /**
     * 코드 5-21. 확장 함수로 작성한 sum 함수
     */
    fun FunList<Int>.sum(): Int = foldLeft(0) { acc, x -> acc + x }

    /**
     * 코드 5-23. toUpper 함수
     */
    fun toUpper(list: FunList<Char>): FunList<Char> = list.foldLeft(FunList.Nil) { acc: FunList<Char>, char: Char ->
        acc.appendTail(char.uppercaseChar())
    }

    /**
     * 코드 5-24. foldLeft 함수로 작성한 map 함수
     */
    fun <T, R> FunList<T>.mapByFoldLeft(f: (T) -> R): FunList<R> = this.foldLeft(FunList.Nil) { acc: FunList<R>, x ->
        acc.appendTail(f(x))
    }

    /**
     * 연습문제 5-9. 3장에서 작성한 maximum 함수를 foldLeft 함수를 사용해서 다시 작성해 보자.
     */
    fun FunList<Int>.maximumFoldLeft(): Int = foldLeft(0) { acc, elm ->
        if (acc > elm) acc
        else elm
    }

    /**
     * 연습문제 5-10. filter 함수를 foldLeft 함수를 사용해서 다시 작성해 보자.
     */
    fun <T> FunList<T>.filterByFoldLeft(p: (T) -> Boolean): FunList<T> = foldLeft(FunList.Nil) { acc: FunList<T>, elm ->
        if (p(elm)) acc.appendTail(elm)
        else acc
    }

    /**
     * 코드 5-25. foldRight 함수 만들기
     * 아래 foldRight 함수는 꼬리 재귀가 아니기 때문에, 스택에 안전하지 않다.
     */
    fun <T, R> FunList<T>.foldRight(acc: R, f: (T, R) -> R): R = when (this) {
        FunList.Nil -> acc
        is FunList.Cons -> f(head, tail.foldRight(acc, f))
    }

    /**
     * 연습문제 5-11. 3장에서 작성한 reverse 함수를 foldRight 함수를 사용해서 다시 작성해보자.
     */
    fun <T> FunList<T>.reverseByFoldRight(): FunList<T> =
        this.foldRight(FunList.Nil as FunList<T>) { value, acc ->
            acc.appendTail(value)
        }

    /**
     * 연습문제 5-12. filter 함수를 foldRight 함수를 사용해서 다시 작성해보자.
     */
    fun <T> FunList<T>.filterByFoldRight(p: (T) -> Boolean): FunList<T> =
        this.foldRight(FunList.Nil as FunList<T>) { value, acc ->
            if (p(value)) acc.addHead(value)
            else acc
        }

    /**
     * 코드 5-28. foldLeft, foldRight로 작성한 map 함수
     */
//    fun <T, R> FunList<T>.mapByFoldLeft(f: (T) -> R): FunList<R> = foldLeft(FunList.Nil) { acc: FunList<R>, x ->
//        acc.appendTail(f(x))
//    }
    fun <T, R> FunList<T>.mapByFoldRight(f: (T) -> R): FunList<R> = foldRight(FunList.Nil) { x, acc: FunList<R> ->
        acc.addHead(f(x))
    }


    fun main() {
        // foldLeft 함수 사용하기
        var intList = funListOf(1, 2, 3)

        // println(sum(intList))
        println(sumByFoldLeft(intList))

        /**
         * 코드 5-22. map과 filter 체인의 예
         */
        println(
            intList.map { it + 3 }.filter { it % 2 == 0 }.sum()
        )

        intList = funListOf(1, 3, 10)
        /**
         * 코드 5-26. foldRight 함수 사용 예
         */
        println(intList.foldRight(0) { x, acc -> x - acc })


    }
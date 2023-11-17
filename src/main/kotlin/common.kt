
fun List<Int>.head() = first()
fun List<Int>.tail() = drop(1)

fun String.head() = first()
fun String.tail() = drop(1)

fun <T> Sequence<T>.head() = first()
fun <T> Sequence<T>.tail() = drop(1)

fun <T> Set<T>.head() = first()
fun <T> Set<T>.tail() = drop(1).toSet()

fun <T> Collection<T>.head() = first()
fun <T> Collection<T>.tail() = drop(1)
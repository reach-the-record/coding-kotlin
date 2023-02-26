import java.time.LocalDate

/**
 * 컬렉션, 범위에 쓰이는 관례
 * in, rangeTo, iterator 등..
 */
class EtcConvention {
}

/**
 * in 관례
 * in - 객체가 컬렉션에 들어있는지 검사한다.
 * in 연산자와 대응하는 함수 - contains
 */
data class Point(val x: Int, val y: Int)

data class Rectangle(val upperLeft: Point, val lowerRight: Point)

operator fun Rectangle.contains(p: Point): Boolean {
    return p.x in upperLeft.x until lowerRight.x &&
            p.y in upperLeft.y until lowerRight.y
}

fun main40(args: Array<String>) {
    val rect = Rectangle(Point(10, 20), Point(50, 50))
    println(Point(20, 30) in rect)
    println(Point(5, 5) in rect)
}

/**
 * rangeTo 관례
 * 범위를 만들려면 .. 구문을 사용해야 한다.
 * ex) 1..10은 1부터 10까지 모든 수가 들어있는 범위를 가리킨다.
 */
fun main41(args: Array<String>) {
    val n = 9
    println(0..(n + 1))
    (0..n).forEach { print(it) }
}

/**
 * for 루프를 위한 iterator 관례
 * iterator 메소드를 확장 함수로 정의할 수 있다.
 * 덕분에 Java 문자열에 대한 for 루프가 가능하다.
 */
operator fun ClosedRange<LocalDate>.iterator(): Iterator<LocalDate> =
    object : Iterator<LocalDate> {
        var current = start

        override fun hasNext() =
            current <= endInclusive

        override fun next() = current.apply {
            current = plusDays(1)
        }
    }

fun main42(args: Array<String>) {
    val newYear = LocalDate.ofYearDay(2024, 1)
    val daysOff = newYear.minusDays(1)..newYear
    for (dayOff in daysOff) { println(dayOff) }
}

/**
 * 구조 분해 선언과 component 함수
 * 구조 분해를 사용하여 여러 변수를 한번에 초기화 가능.
 * 함수에서 여러 값을 반환할 때 유용하게 사용 가능.
 */
fun main43(args: Array<String>) {
    val p = Point(10, 20)
    val (x, y) = p
    println(x)
    println(y)
}

/**
 * 여러 값을 한꺼번에 반환해야 하는 함수가 있을 경우
 * 반환해야 하는 모든 값이 들어갈 데이터 클래스를 정의하고 함수의 반환 타입을 데이터 클래스로 변경한다.
 *
 * 표준 라이브러리의 Pair나 Triple 클래스를 사용하면 함수에서 여러 값을 더 간단하게 반환할 수 있다.
 * Pair나 Triple은 그 안에 담겨있는 원소의 의미를 말해주지 않으므로 경우에 따라 가독성은 떨어질 수 있으나
 * 직접 클래스를 작성할 필요가 없으므로 코드는 더 단순해진다.
 */
data class NameComponents(val name: String,
                          val extension: String)

fun splitFilename(fullName: String): NameComponents {
    val result = fullName.split('.', limit = 2)
    return NameComponents(result[0], result[1]) // 함수에서 데이터 클래스의 인스턴스를 반환한다.
}

fun main44(args: Array<String>) {
    val (name, ext) = splitFilename("example.kt") // 구조 분해 선언 구문을 사용해 데이터 클래스를 푼다.
    println(name)
    println(ext)
}

/**
 * 구조 분해 선언 - 루프
 * 변수 선언이 들어갈 수 있는 곳이라면 어디든 구조 분해 선언 사용이 가능하다.
 */
fun printEntries(map: Map<String, String>) {
    for ((key, value) in map) { //객체 구조 분해 선언, iteration 관례
        println("$key -> $value")
    }
}

fun main45(args: Array<String>) {
    val map = mapOf("Oracle" to "Java", "JetBrains" to "Kotlin")
    printEntries(map)

    /**
     * Kotlin 라이브러리는 Map.Entry에 대한 확장 함수로 component1과 component2를 제공한다.
     */
    for (entry in map.entries) {
        val key = entry.component1()    // key Component
        val value = entry.component2()  // value Component
    }
}

/**
 * 프로퍼티 접근자 로직 재활용 : 위임 프로퍼티
 * 위임 프로퍼티(delegated property)를 사용하면 값을 뒷받침하는 필드에 단순히 저장하는 것보다 더 복잡한 방식으로 작동하는 프로퍼티를 쉽게 구현할 수 있다.
 * 또한 그 과정에서 접근자 로직을 매번 재구현할 필요도 없다.
 * 위임은 객체가 직접 작업을 수행하지 않고 다른 도우미 객체가 그 작업을 처리하게 맡기는 디자인 패턴을 말한다.
 * 작업을 처리하는 도우미 객체를 위임 객체라고 부른다.
 *
 * 위임 프로퍼티 사용: by lazy()를 사용한 프로퍼티 초기화 지연
 * 지연 초기화(lazy initialization)는 객체의 일부분을 초기화하지 않고 남겨뒀다가 실제로 그 부분의 값이 필요할 경우 초기화할 때 흔히 쓰이는 패턴이다.
 * 초기화 과정에 자원을 많이 사용하거나 객체를 사용할 때마다 꼭 초기화하지 않아도 되는 프로퍼티에 대해 지연 초기화 패턴을 사용할 수 있다.
 */
data class Email(val email: String)
class Person5(val name: String) {
    private var _emails: List<Email>? = null // 데이터를 저장하고 emails의 위임 객체 역활을 하는 _emails 프로퍼티

    private fun loadEmails(): List<Email> {
        return listOf(Email("reachtherecord@gmail.com"))
    }

    val emails: List<Email>
        get() {
            if (_emails == null) {
                _emails = loadEmails() // 최초 접근 시 이메일을 가져온다.
            }
            return _emails!! // 저장해 둔 데이터가 있으면 그 데이터를 반환한다.
        }
}

fun main46(args: Array<String>) {
    val p = Person5("Alice")
    p.emails // 최초로 emails를 읽을 때 단 한번만 이메일을 가져온다.
    p.emails
}

/**
 * 위 코드를 만드는 일은 약간 성가시다.
 * 지연 초기화해야 하는 프로퍼티가 많아지면 코드가 어떻게 될까?
 * 게다가 이 구현은 스레드 안전하지 않아서 언제나 제대로 작동한다고 말할 수도 없다.
 * 위임 프로퍼티를 사용하면 훨씬 더 간편해진다.
 * lazy 함수는 코틀린 관례에 맞는 시그니처의 getValue 메소드가 들어있는 객체를 반환하므로 lazy를 by 키워드와 함께 사용해 위임 프로퍼티를 만들 수 있다.
 */
class Person6(val name: String) {
    val emails by lazy { loadEmails() }

    fun loadEmails():List<Email> {
        return listOf(Email("reachtherecord@gmail.com"))
    }
}






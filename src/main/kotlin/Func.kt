/**
 * 고차 함수
 * 파라미터와 반환 값으로 람다 사용
 */
class Func {
}

/**
 * 고차 함수 정의
 * 다른 함수를 인자로 받거나 함수를 반환하는 함수.
 * Kotlin에서는 람다나 함수 참조를 사용해 함수를 값으로 표현할 수 있다.
 * 고차 함수는 람다나 함수 참조를 인자로 넘길 수 있거나 람다나 함수 참조를 반환하는 함수다.
 *
 * 함수 타입
 * 함수 타입을 정의하려면 함수 파라미터의 타입을 괄호 안에 넣고, 그 뒤에 화살표(→)를 추가한 다음, 함수의 반환 타입을 지정하면 된다.
 * Unit 타입 - 의미 있는 값을 반환하지 않는 함수 반환 타입에 쓰는 특별한 타입.
 * 그냥 함수를 정의한다면 함수의 파라미터 목록 뒤에 오는 Unit 반환 타입 지정을 생략해도 되지만,
 * 함수 타입을 선언할 때는 반환 타입을 반드시 명시해야 하므로 Unit생략이 불가.
 */

/**
 * 인자로 받은 함수 호출
 */
fun twoAndThree(operation: (Int, Int) -> Int) {
    val result = operation(2, 3)
    println("The result is $result")
}

fun main50(args: Array<String>) {
    twoAndThree { a, b -> a + b }
    twoAndThree { a, b -> a * b }
}

/**
 * 디폴트 값을 지정한 함수 타입 파라미터나 Null이 될 수 있는 함수 타입 파라미터
 */
fun <T> Collection<T>.joinToString(
    separator: String = ", ",
    prefix: String = "",
    suffix: String = "",
    transform: (T) -> String = { it.toString() } // 함수 타입 파라미터를 선언하면서 람다를 디폴트 값으로 지정한다.
): String {
    val result = StringBuilder(prefix)

    for ((index, element) in this.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(transform(element)) // "transform" 파라미터로 받은 함수 호출
    }

    result.append(suffix)
    return result.toString()
}

fun main61(args: Array<String>) {
    val letters = listOf("Alpha", "Beta")
    println(letters.joinToString()) // 디폴트 변환 함수 사용 -> Alpha, Beta
    println(letters.joinToString { it.toLowerCase() }) // 람다를 인자로 전달한다. -> alpha, beta
    println(letters.joinToString(separator = "! ", suffix = "! ",
        transform = { it.toUpperCase() })) // 이름 붙은 인자 구문을 사용해 람다를 포함하는 여러 인자를 전달한다. -> ALPHA! BETA!
}

/**
 * 함수에서 함수를 반환
 * 다른 함수를 반환하는 함수를 정의하려면 함수의 반환 타입으로 함수 타입을 지정해야 한다.
 * 함수를 반환하려면 return 식에 람다나 멤버 참조나 함수 타입의 값을 계산하는 식 등을 넣으면 된다.
 */
data class Person10(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String?
)

class ContactListFilters {
    var prefix: String = ""
    var onlyWithPhoneNumber: Boolean = false

    fun getPredicate(): (Person10) -> Boolean { // 함수를 반환하는 함수를 정의한다.
        val startsWithPrefix = { p: Person10 ->
            p.firstName.startsWith(prefix) || p.lastName.startsWith(prefix)
        }
        if (!onlyWithPhoneNumber) {
            return startsWithPrefix // 함수 타입의 변수를 반환한다.
        }
        return { startsWithPrefix(it)
                && it.phoneNumber != null } // 람다를 반환한다.
    }
}

fun main62(args: Array<String>) {
    val contacts = listOf(Person10("Dmitry", "Jemerov", "123-4567"),
        Person10("Svetlana", "Isakova", null))
    val contactListFilters = ContactListFilters()
    with (contactListFilters) {
        prefix = "Dm"
        onlyWithPhoneNumber = true
    }
    println(contacts.filter(
        contactListFilters.getPredicate()))
}

/**
 * 람다를 활용한 중복 제거
 */
data class SiteVisit(
    val path: String,
    val duration: Double,
    val os: OS
)

enum class OS { WINDOWS, LINUX, MAC, IOS, ANDROID }

val log = listOf(
    SiteVisit("/", 34.0, OS.WINDOWS),
    SiteVisit("/", 22.0, OS.MAC),
    SiteVisit("/login", 12.0, OS.WINDOWS),
    SiteVisit("/signup", 8.0, OS.IOS),
    SiteVisit("/", 16.3, OS.ANDROID)
)

val averageWindowsDuration = log
    .filter { it.os == OS.WINDOWS }
    .map(SiteVisit::duration)
    .average()

/**
 * 함수를 활용하여 코드를 줄일 수 있다.
 * 하지만 이 함수는 충분히 강력하지 않다.
 */
fun List<SiteVisit>.averageDurationFor(os: OS) =
    filter { it.os == os }.map(SiteVisit::duration).average()

fun main63(args: Array<String>) {
    println(log.averageDurationFor(OS.WINDOWS))
    println(log.averageDurationFor(OS.MAC))
}

/**
 * 만약 모바일 디바이스(IOS, 안드로이드)의 평균 방문 시간을 구하고 싶다거나
 * IOS 사용자의 /signup 페이지 평균 방문 시간을 구하고 싶을 경우에는 고차 함수를 이용하여 함수를 확장할 수 있다.
 */
fun List<SiteVisit>.averageDurationFor(predicate: (SiteVisit) -> Boolean) =
    filter(predicate).map(SiteVisit::duration).average()

fun main64(args: Array<String>) {
    println(log.averageDurationFor {
        it.os in setOf(OS.ANDROID, OS.IOS) })
    println(log.averageDurationFor {
        it.os == OS.IOS && it.path == "/signup" })
}

/**
 * 고차 함수 안에서 흐름 제어
 * 람다 안의 return문: 람다를 둘러싼 함수로부터 반환
 */
val people = kotlin.collections.listOf(Person("Alice", 29), Person("Bob", 31))

/**
 * 실행 결과를 보면 이름이 Alice인 경우에 lookForAlice 함수로부터 반환된다는 사실을 분명히 알 수 있다.
 */
fun lookForAlice(people: List<Person>) {
    for (person in people) {
        if (person.name == "Alice") {
            println("Found!")
            return
        }
    }
    println("Alice is not found")
}

/**
 * 람다 안에서 return을 사용하면 람다로부터만 반환되는 게 아니라 그 람다를 호출하는 함수가 실행을 끝내고 반환된다.
 * 그렇게 자신을 둘러싸고 있는 블록보다 더 바깥에 있는 다른 블록을 반환하게 만드는 return 문을 넌로컬(non-local) return이라 부른다.
 * 이렇게 return이 바깥쪽 함수를 반환시킬 수 있는 때는 람다를 인자로 받는 함수가 인라인 함수인 경우뿐이다.
 * 예제에서 forEach는 인라인 함수이므로 람다 본문과 함께 인라이닝된다.
 * 따라서 return 식이 바깥쪽 함수(여기서는 lookForAlice)를 반환시키도록 쉽게 컴파일할 수 있다.
 */
fun lookForAlice2(people: List<Person>) {
    people.forEach {
        if (it.name == "Alice") {
            println("Found!")
            return
        }
    }
    println("Alice is not found")
}

/**
 * 람다 식에서도 로컬 return을 사용할 수 있다.
 * 람다 안에서 로컬 return은 for루프의 break와 비슷한 역할을 한다.
 * 로컬 return과 넌로컬 return을 구분하기 위해 레이블(label)을 사용해야 한다.
 */
fun lookForAlice3(people: List<Person>) {
    people.forEach label@{
        if (it.name == "Alice") return@label
    }
    println("Alice might be somewhere")
}

/**
 * 람다에 레이블을 붙여서 사용하는 대신 람다를 인자로 받는 인라인 함수의 이름을 return 뒤에 레이블로 사용해도 된다.
 */
fun lookForAlice4(people: List<Person>) {
    people.forEach {
        if (it.name == "Alice") return@forEach
    }
    println("Alice might be somewhere")
}

/**
 * 무명 함수 - 기본적으로 로컬 return
 * 무명 함수는 코드 블록을 함수에 넘길 때 사용할 수 있는 다른 방법이다.
 * 무명 함수는 일반 함수와 비슷하나 차이는 함수 이름이나 파라미터 타입을 생략할 수 있다는 점 뿐이다.
 */
fun lookForAlice5(people: List<Person>) {
    people.forEach(fun (person) {
        if (person.name == "Alice") return
        println("${person.name} is not Alice")
    })
}


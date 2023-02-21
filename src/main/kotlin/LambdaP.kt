/**
 * 람다 식과 멤버 참조
 * 클래스를 선언하고 그 클래스의 인스턴스를 함수에 넘기는 대신 함수형 언어에서는 함수를 직접 다른 함수에 전달할 수 있다.
 * 람다 식을 사용하면 코드가 더욱 더 간결해진다. 람다 식을 사용하면 함수를 선언할 필요가 없고 코드 블록을 직접 함수의 인자로 전달할 수 있다.
 */
class LambdaP{}

/**
 * 코드에서 중복을 제거하는 것은 프로그래밍 스타일을 개선하는 중요한 방법 중 하나다.
 * 람다가 없다면 컬렉션을 편리하게 처리할 수 있는 좋은 라이브러리를 제공하기 힘들다.
 */
data class Man(val name: String, val age: Int)

/* Java 방식 */
fun findTheOldest(men: List<Man>) {
    var maxAge = 0
    var theOldest: Man? = null
    for (man in men) {
        if (man.age > maxAge) {
            maxAge = man.age
            theOldest = man
        }
    }
    println(theOldest)
}

/**
 * 함수나 프로퍼티를 반환하는 역할을 수행하는 람다는 멤버 참조로 대치할 수 있다.
 * 람다나 멤버 참조를 인자로 받는 함수를 통해 개선한 코드는 더 짧고 더 이해하기 쉽다.
 */
fun mains(args: Array<String>) {
    val man = listOf(Man("Alice", 29), Man("Bob", 31))
    findTheOldest(man)

    /* Kotlin 방식 */
    val men = kotlin.collections.listOf(Man("Alice", 29), Man("Bob", 31))
    println(men.maxBy { it.age })
}

/**
 * 현재 영역에 있는 변수에 접근
 * 자바 메소드 안에서 무명 내부 클래스를 정의할 때 메소드의 로컬 변수를 무명 내부 클래스에서 사용할 수 있다.
 * 다음 리스트는 메시지의 목록을 받아 모든 메시지에 똑같은 접두사를 붙여서 출력해준다.
 */
fun printMessagesWithPrefix(messages: Collection<String>, prefix: String) {
    messages.forEach {
        println("$prefix $it")
    }
}

/**
 * Kotlin에서는 Java와 달리 람다에서 람다 밖 함수에 있는 파이널이 아닌 변수에 접근할 수 있고, 그 변수를 변경할 수도 있다.
 */
fun printProblemCounts(responses: Collection<String>) {
    var clientErrors = 0
    var serverErrors = 0
    responses.forEach {
        if (it.startsWith("4")) {
            clientErrors++
        } else if (it.startsWith("5")) {
            serverErrors++
        }
    }
    println("$clientErrors client errors, $serverErrors server errors")
}

/**
 * 파이널 변수를 포획한 경우에는 람다 코드를 변수 값과 함께 저장한다.
 * 파이널이 아닌 변수를 포획할 경우에는 변수를 특별한 래퍼로 감싸서 나중에 변경하거나 읽을 수 있게 한 다음,
 * 래퍼에 대한 참조를 람다 코드와 함께 저장한다.
 *
 * !!중요!!
 * 람다를 이벤트 핸들러나 다른 비동기적으로 실행되는 코드로 활용하는 경우
 * 함수 호출이 끝난 다음에 로컬 변수가 변경될 수도 있으니 주의.
 *
 * onClick 핸들러는 호출될 때마다 clicks의 값을 증가시키지만 그 값의 변경을 관찰할 수는없다.
 * 핸들러는 tryToCountButtonClicks가 clicks를 반환한 다음에 호출되기 때문이다.
 */
fun tryToCountButtonClicks(button: Button) : Int {
    var clicks = 0
    button.onClick { clicks++ }
    return clicks
}

/**
 * filter와 map
 * filter - 컬렉션에서 원치 않는 원소 제거. 원소를 변환할 수는 없음. 변환하기 위해서는 map함수를 사용해야 한다.
 * map - 주어진 람다를 각 원소에 적용한 결과를 모아 새 컬렉션을 만든다.
 */
fun main1(args: Array<String>) {
    val men = listOf(Man("Alice", 29), Man("Bob", 31))
    println(men.filter { it.age > 30 })
}

fun main2(args: Array<String>) {
    val men = listOf(Man("Alice", 29), Man("Bob", 31))
    println(men.map { it.name })
}

fun main3(args: Array<String>) {
    val numbers = mapOf(0 to "zero", 1 to "one")
    println(numbers.mapValues { it.value.toUpperCase() })
}

/**
 * all, any, count, find, findLast
 * any : 조건을 만족하는 원소가 1개 이상 존재
 * all : 모든 원소가 조건을 만족
 * none : 조건을 만족하는 원소가 없음
 * find : 탐색을 앞에서부터 시작하여 만족하는 원소가 있다면 반환, 없다면 null
 * findLast : 탐색을 뒤에서부터 시작하여 만족하는 원소가 있다면 반환, 없다면 null
 * count : 조건에 만족하는 원소 개수
 */
data class Woman(val name: String, val age: Int)

val canBeInClub27 = { w: Woman -> w.age <= 27 }

fun main4(args: Array<String>) {
    val women = listOf(Woman("Alice", 27), Woman("Bob", 31))
    println(women.all(canBeInClub27))
}

/**
 * flatMap과 flatten: 중첩된 컬렉션 안의 원소 처리
 * flatMap 함수는 먼저 인자로 주어진 람다를 컬렉션의 모든 객체에 적용하고
 * 람다를 적용한 결과 얻어지는 여러리스트를 한 리스트로 한데 모은다.
 */
fun main5(args: Array<String>) {
    val strings = listOf("abc", "def")
    println(strings.flatMap { it.toList() })
} // result [a, b, c, d, e, f]

data class Book(val name: String, val authors: List<String>)

fun main6(args: Array<String>) {
    val books = listOf(Book("Thursday Next", listOf("Jasper Fforde")),
        Book("Mort", listOf("Terry Pratchett")),
        Book("Good Omens", listOf("Terry Pratchett",
            "Neil Gaiman")))
    println(books.flatMap { it.authors }.toSet())
}
// result [Jasper Fforde, Terry Pratchett, Neil Gaiman]

/**
 * 지연 계산 컬렉션 연산
 * map이나 filter 같은 몇 가지 컬렉션 함수는 결과 컬렉션을 즉시 생성한다.
 * 컬렉션 함수를 연쇄하면 매 단계마다 계산 중간 결과를 새로운 컬렉션에 임시로 담는다는 뜻이다.
 * 시퀀스(sequence)를 사용하면 중간 임시 컬렉션을 사용하지 않고도 컬렉션 연산을 연쇄할 수 있다.
 */
fun main7(args: Array<String>) {
    listOf(1, 2, 3, 4).asSequence() // 원본 컬렉션을시퀀스로 변환한다.
        .map { print("map($it) "); it * it } // 시퀀스도 컬렉션과 똑같은 API를 제공한다.
        .filter { print("filter($it) "); it % 2 == 0 }
        .toList() // 결과 시퀀스를 다시 리스트로 변환한다.
}

/**
 * 코틀린 지연 계산 시퀀스는 Sequence 인터페이스에서 시작한다.
 * Sequence 안에는 iterator라는 단 하나의 메소드가 있다. 그 메소드를 통해 시퀀스로부터 원소 값을 얻을 수 있다.
 * 컬렉션보다 시퀀스가 훨씬 더 낫다면 그냥 시퀀스를 쓰는 편이 나을수도 있다. 하지만 "항상 그렇지는 않다".
 * 시퀀스의 원소를 차례로 이터레이션해야 한다면 시퀀스를 직접 써도 된다.
 * 하지만 시퀀스 원소를 인덱스를 사용해 접근하는 등의 다른 API 메소드가 필요하다면 시퀀스를 리스트로 변환해야 한다.
 */
fun main8(args: Array<String>) {
    listOf(1, 2, 3, 4).asSequence()
        .map { print("map($it) "); it * it }
        .filter { print("filter($it) "); it % 2 == 0 }
        .toList()
}// result > map(1) filter(1) map(2) filter(4) map(3) filter(9) map(4) filter(16)
/**
 * 시퀀스의 경우 모든 연산은 각 원소에 대해 순차적으로 적용된다.
 * 첫 번째 원소가 처리되고, 다시 두 번째 원소가 처리되며, 이런 처리가 모든 원소에 대해 적용된다.
 */


/**
 * SAM(Single Abstract Method) 생성자: 람다를 함수형 인터페이스로 명시적으로 변경 > Runnable
 * SAM 생성자는 람다를 함수형 인터페이스의 인스턴스로 변환할 수 있게 컴파일러가 자동으로 생성한 함수다.
 * 컴파일러가 자동으로 람다를 함수형 인터페이스 무명 클래스로 바꾸지 못하는 경우 SAM 생성자를 사용할 수 있다.
 * Java 식으로 인터페이스에 추상 메소드를 만들고 클래스에서 구현한 뒤 사용하려 하면 오류가 발생한다.
 * Kotlin에서는 적절한 함수 유형이 있기 때문에 함수를 자동 변환할 필요가 없어 지원하지 않는다.
 */
fun createAllDoneRunnable(): Runnable {
    return Runnable { println("All done!") }
}

// Java 식으로 인터페이스에 추상 메소드를 만들고
interface OnclickListenerA {
    abstract fun setOnclickListener(onClickListener: OnclickListenerA)
}

// 클래스에서 구현한 뒤
class Example: OnclickListenerA {
    override fun setOnclickListener(onClickListener: OnclickListenerA) {
        TODO("Not yet implemented")
    }
}

// 사용하려고 하면 오류가 발생한다.
fun main9(args: Array<String>) {
    val initSetOnClick = Example()
//    initSetOnClick.setOnclickListener{
//        println(it)
//    }
}

/**
 * Kotlin에서는 적절한 함수 유형이 이미 있어 함수를 자동 변환할 필요가 없어 지원하지 않는다고 한다.
 * 기존 코틀린의 interface 앞에 fun을 붙여주게 되면 앞의 자바 코드와 동일하게 실행된다.
 */
fun interface OnclickListenerB {
    abstract fun setOnclickListener(onClickListener: OnclickListenerB)
}

class Example2: OnclickListenerB {
    override fun setOnclickListener(onClickListener: OnclickListenerB) {
        TODO("Not yet implemented")
    }
}

fun main10(args: Array<String>) {
    val initSetOnClick = Example2()
    initSetOnClick.setOnclickListener{
        println(it)
    }
}

/**
 * 수신 객체 지정 람다: with와 apply
 * 수신 객체를 명시하지 않고 람다의 본문 안에서 다른 객체의 메소드를 호출할 수 있게 하는 것이다.
 * 그런 람다를 수신 객체 지정 람다라고 부른다.
 *
 * with -첫 번째 인자로 받은 객체를 두 번째 인자로 받은 람다의 수신 객체로 만든다.
 * 인자로 받은 람다 본문에서는 this를 사용해 그 수신 객체에 접근할 수 있다.
 * with가 반환하는 값은 람다 코드를 실행한 결과며, 그 결과는 람다 식의본문에 있는 마지막 식의 값이다.
 */
// with를 사용하지 않은 함수
fun alpha(): String {
    val result = StringBuilder()
    for (letter in 'A'..'Z') {
        result.append(letter)
    }
    result.append("\\nNow I know the alphabet!")
    return result.toString()
}

// with를 사용하여 중복된 변수명을 제거한 함수 사용
fun alpha2(): String {
    val stringBuilder = StringBuilder()
    return with(stringBuilder) {
        for (letter in 'A'..'Z') {
            this.append(letter)
        }
        append("\\nNow I know the alphabet!")
        this.toString()
    }
}

/**
 * apply - with와 거의 동일하나, 유일한 차이란 apply는 항상 자신에게 전달된 객체(즉 수신 객체)를 반환한다는 점뿐이다.
 */
fun alphabet() = StringBuilder().apply {
    for (letter in 'A'..'Z') {
        append(letter)
    }
    append("\\nNow I know the alphabet!")
}.toString()

/**
 * 표준 라이브러리의 buildString 함수를 사용하면 alphabet 함수를 더 단순화할 수 있다.
 * buildString 함수는 StringBuilder를 활용해 String을 만드는 경우 사용할 수 있는 방법.
 */
fun alphabet2() = buildString {
    for (letter in 'A'..'Z') {
        append(letter)
    }
    append("\\nNow I know the alphabet!")
}

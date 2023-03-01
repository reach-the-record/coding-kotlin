/**
 * 제네릭스
 */
class Generics {
}

/**
 * 제네릭 타입 파라미터
 * 제네릭 함수와 프로퍼티
 * 리스트를 다루는 함수를 작성한다면 어떤 특정 타입을 저장하는 리스트뿐 아니라 모든 리스트를 다룰 수 있는 함수를 원할 것이다.
 * 이럴때 제네릭 함수를 작성해야 한다.
 */
fun main70(args: Array<String>) {
    val letters = ('a'..'z').toList()
    println(letters.slice<Char>(0..2)) // 타입 인자를 명시적으로 지정한다.
    //[a, b, c]
    println(letters.slice(10..13)) // 컴파일러는 여기서 T가 Char라는 사실을 추론한다.
    //[k, l, m, n]
}

/**
 * 타입 파라미터 제약
 * 어떤 타입을 제네릭 타입의 타입 파라미터에 대한 상한(upper bound)으로 지정하면 그 제네릭 타입을
 * 인스턴스화할 때 사용하는 타입 인자는 반드시 그 상한 타입이거나 그 상한 타입의 하위 타입이어야 한다.
 * 타입 파라미터 T에 대한 상한을 정하고 나면 T 타입의 값을 그 상한 타입의 값으로 취급할 수 있다.
 */
fun <T : Number> oneHalf(value: T): Double { // Number를 타입 파라미터 상한으로 정한다.
    return value.toDouble() / 2.0 // Number 클래스에 정의된 메소드를 호출한다.
}

fun main71(args: Array<String>) {
    println(oneHalf(3))
    //1.5
}

/**
 * 타입 파라미터를 Null이 될 수 없는 타입으로 한정
 * 아무런 상한을 정하지 않은 타입 파라미터는 결과적으로 Any?를 상한으로 정한 파라미터와 같다.
 */
class Processor<T> {
    fun process(value: T) {
        value?.hashCode() // "value"는 Null이 될 수 있다. 따라서 안전한 호출을 사용해야 한다.
    }
}

/**
 * <T : Any>라는 제약은 T 타입이 항상 Null이 될 수 없는 타입이 되도록 보장한다.
 */
class Processor2<T : Any> { //또는 타입 파라미터를 Null이 될 수 없는 타입으로 제약하기만 하면 타입 인자로 Null이 될 수 있는 타입이 들어오는 일을 막을 수 있다.
    fun process(value: T) {
        value.hashCode()
    }
}

/**
 * 실행 시점의 제네릭: 타입 검사와 캐스트
 * 자바와 마찬가지로 코틀린 제네릭 타입 인자 정보는 런타임에 지워진다.
 * 이는 제네릭 클래스 인스턴스가 그 인스턴스를 생성할 때 쓰인 타입 인자에 대한 정보를 유지하지 않는다는 뜻이다.
 */
fun main72(args: Array<String>) {
    val list1: List<String> = listOf("a", "b")
    val list2: List<Int> = listOf(1, 2, 3)
}

/**
 * 컴파일러는 두 리스트를 서로 다른 타입으로 인식하지만 실행 시점에 그 둘은 완전히 같은 타입의 객체다.
 * 타입 파라미터가 2개 이상이라면 모든 타입 파라미터에 *를 포함시켜야 한다.
 */
fun printSum(c: Collection<*>) {
    val intList = c as? List<Int>
        ?: throw IllegalArgumentException("List is expected")
    println(intList.sum())
}

/**
 * 실행 시점에는 제네릭 타입의 타입 인자를 알 수 없으므로 캐스팅은 항상 성공한다.
 * 그런 타입 캐스팅을 사용하면 컴파일러가 "unchecked case(검사할 수 없는 캐스팅)"이라는 경고를 해준다.
 * 컴파일러가 캐스팅 관련 경고를 한다는 점을 제외하면 모든 코드가 문제없이 컴파일 된다.
 */
fun main73(args: Array<String>) {
    printSum(listOf(1, 2, 3))   //실행 결과 : 6

    printSum(setOf(1, 2, 3)) // IllegalArgumentException: List is expected

    // 잘못된 타입의 원소가 들어있는 리스트를 전달하면 실행 시점에 ClassCastException이 발생한다.
    printSum(listOf("a", "b", "c")) // ClassCastException: String cannot be cast to Number
}

/**
 * Kotlin 컴파일러는 컴파일 시점에 타입 정보가 주어진 경우에는 is 검사를 수행하게 허용할 수 있을 정도로 똑똑하다.
 */
fun printSum2(c: Collection<Int>) {
    if (c is List<Int>) {
        println(c.sum())
    }
}
/*
 * 실체화한 타입 파라미터의 제약
 * 다음과 같은 경우에 실체화한 타입 파라미터를 사용할 수 있다.
 * 타입 검사와 캐스팅(is, !is, as, as?)
 * 10장에서 설명할 코틀린 리플렉션 API(::class)
 * 코틀린 타입에 대응하는 java.lang.Class를 얻기(::class.java)
 * 다른 함수를 호출할 때 타입 인자로 사용
 * 하지만 다음과 같은 일은 할 수 없다.
 * 타입 파라미터 클래스의 인스턴스 생성하기
 * 타입 파라미터 클래스의 동반 객체 메소드 호출하기
 * 실체화한 타입 파라미터를 요구하는 함수를 호출하면서 실체화하지 않은 타입 파라미터로 받은 타입을 타입 인자로 넘기기
 * 클래스, 프로퍼티, 인라인 함수가 아닌 함수의 타입 파라미터를 refied로 지정하기
 */

/**
 * 변성: 제네릭과 하위 타입
 * 변성 개념은 List<String>와 List<Any>와 같이 기저 타입이 같고 타입 인자가 다른 여러 타입이 서로 어떤 관계가 있는지 설명하는 개념이다.
 * 변성을 잘 활용하면 사용에 불편하지 않으면서 타입 안전성을 보장하는 API를 만들 수 있다.
 *
 * 공변성: 하위 타입 관계를 유지
 * Producer<T>를 예로 공변성 클래스를 설명하자. A가 B의 하위 타입일 때 Producer<A>가 Producer<B>의 하위 타입이면 Peoducer는 공변적이다.
 * 이를 하위 타입 관계가 유지된다고 말한다. 예를 들어 Cat가 Animal의 하위 타입이기 때문에 Producer<Cat>은 Producer<Animal>의 하위 타입이다.
 * 코틀린에서 제네릭 클래스가 타입 파라미터에 대해 공변적임을 표시하려면 타입 파라미터 이름 앞에 out을 넣어야 한다.
 */
interface Producer<out T> {  // 클래스가 T에 대해 공변적이라고 선언한다.
    fun produce(): T
}
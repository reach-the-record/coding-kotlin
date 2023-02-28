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

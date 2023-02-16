/**
 * 컬렉션
 * Kotlin 컬렉션은 Java와 비슷하게 만들 수 있다.
 * 자체 컬렉션을 정의하지 않고 표준 자바 컬렉션을 활용하며, 자바 클래스를 확장해서 더 많은 기능 제공.
 *
 *
 */
class Collections {
}

fun main(args: Array<String>) {
    val set = hashSetOf(1, 2, 3, 4, 5)
    val list = arrayListOf(1, 2, 3, 4, 5)
    val map = hashMapOf(1 to "one", 2 to "two", 3 to "three", 4 to "four", 5 to "five")

    val strings = listOf("first", "second", "third", "fourth", "fifth")
    println(strings.last()) //가장 마지막 element 추출
    val numbers = setOf(1, 2, 3, 4, 5)
    println(numbers.max())  //max 추출

    //Kotlin 컬렉션에는 default로 toString 구현이 되어 있다.
    println(list)   // [1, 2, 3, 4, 5]

    /**
     * 코틀린이 지원하는 기능을 사용하지 않고 함수를 직접 구현하여 toString 커스터마이징.
     * 함수는 어떤 타입의 값을 원소로 하는 컬렉션이든 처리할 수 있다.
     * 제네릭 함수의 문법은 자바와 비슷하다.
     */
    fun <T> joinToString(
        collection: Collection<T>,
        separator: String = ":",
        prefix: String = "(",
        suffix: String = ")"
    ): String {
        val result = StringBuilder(prefix)

        for ((index, element) in collection.withIndex()) {
            if (index > 0) result.append(separator)
            result.append(element)
        }

        result.append(suffix)
        return result.toString()
    }

    println(joinToString(list, ":", "[", "]")) // [1:2:3:4:5]
    println(joinToString(collection = set, prefix = "<", suffix = ">")) // <1:2:3:4:5>
    println(joinToString(list)) //(1:2:3:4:5)

    /**
     * 확장 함수, 확장 프로퍼티
     * 확장 함수는 어떤 클래스의 멤버 메소드인 것처럼 호출할 수 있지만 그 클래스의 밖에 선언된 함수다.
     * 확장 함수를 만들려면 추가하려는 함수 이름 앞에 그 함수가 확장할 클래스의 이름을 덧붙이기만 하면 된다.
     * 클래스 이름을 수신 객체 타입(receiver type)이라 부르며, 확장 함수가 호출되는 대상이 되는 값(객체)을 수신 객체(receiver object)라고 부른다.
     * 클래스 안에서 정의한 메소드와 달리 확장 함수 안에서는 클래스 내부에서만 사용할 수 있는 비공개(private) 멤버나 보호된(protected) 멤버를 사용할 수 없다.
     */
    fun String.lastChar(): Char = this.get(this.length - 1)

    println("Kotlin".lastChar())
    //String 수신 객체 타입, "Kotlin" 수신 객체

    /**
     * 확장 함수를 사용하기 위해서는 그 함수를 다른 클래스나 함수와 마찬가지로 임포트해야 한다.
     * 확장 함수를 임포트 없이 사용한다면 동일한 이름의 확장 함수와 충돌할 수도 있기 때문에 임포트로 어떤 확장함수인지 명시해 주어야 한다.
     */
    /*
    import class명.lastChar // 명시적으로 사용
    import class명.* // * 사용 가능
    import class명.lastChar as last // as 키워드를 사용 가능
    */
    /**
     * 확장 함수를 사용한 joinToString 함수 정의
     */
    fun <T> Collection<T>.joinToString(
        separator: String = ", ",
        prefix: String = "",
        postfix: String = ""
    ): String {
        val result = StringBuilder(prefix)

        for ((index, element) in this.withIndex()) {
            if (index > 0) result.append(separator)
            result.append(element)
        }

        result.append(postfix)
        return result.toString()
    }

    /**
     * 확장 프로퍼티
     * 기존 클래스 객체에 대한 프로퍼티 형식의 구문으로 사용할 수 있는 API를 추가할 수 있다.
     * 프로퍼티라는 이름으로 불리기는 하지만 상태를 저장할 적절한 방법이 없기 때문에 실제로 확장 프로퍼티는 아무 상태도 가질 수 없다.
     */
    val sb = StringBuilder("Kotlin?")
    sb.lastChar = '!'
    println(sb)
}

var StringBuilder.lastChar: Char
    get() = get(length - 1)
    set(value: Char) {
        this.setCharAt(length - 1, value)
    }


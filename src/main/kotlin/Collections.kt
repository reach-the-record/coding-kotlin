import java.lang.IllegalArgumentException

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
     * 클래스를 확장한 함수와 그 클래스의 멤버 함수의 이름과 시그니처가 같다면 멤버 함수가 호출된다(멤버 함수의 우선순위가 더 높다).
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

    val varargsList = listOf("one", "two", "three")
    /**
     * 이미 배열에 들어있는 가변 길이 인자로 넘길 때
     * 자바에서는 배열을 넘기면 되지만 Kotlin에서는 배열을 명시적으로 풀어서 배열의 각 원소가 인자로 전달되도록 해야한다.
     * Spread 연산자로 처리
     */
    val argsList = listOf("args = ", *args)

    /**
     * to는 Kotlin키워드가 아니고, 중위 호출이라는 특별한 방식으로 to라는 일반 메소드를 호출한 것.
     * 중위 호출 시에는 수신 객체와 유일한 메소드 인자 사이에 메소드 이름을 넣는다.
     */
    val map = hashMapOf(1 to "one", 2 to "two", 3 to "three", 4 to "four", 5 to "five")
    1.to("one") //"to" 메소드를 일반적인 방식으로 호출함
    1 to "one"  //"to" 메소드를 중위 호출 방식으로 호출함

    /**
     * 함수(메소드) 중위 호출 사용을 허용하고 싶으면 infix 변경자를 함수(메소드) 선언 앞에 추가한다.
     * public infix fun <A, B> A.To(that: B): Pair<A, B> = Pair(this, that)
     * 이런 기능을 구조분해선언이라고 한다.
     * 구조 분해 선언은 Pair 인스턴스 외 다른 객체에도 적용 가능하다.
     * key, value 두 변수를 맵의 원소를 사용해 초기화 가능
     */

    /**
     * 문자열 나누기
     * split으로 여러 구분 문자열 지정 가능하다.
     * 정규식, 3중 따옴표로 묶은 문자열
     * 정규식이 강력하기는 하지만 나중에 알아보기 힘든 경우가 많음.
     * 정규식이 필요할 경우 코틀린 라이브러리를 사용하면 편하다.
     */

    //String 확장 함수 사용
    fun parsePath(path: String) {
        val directory = path.substringBeforeLast("/")
        val fullName = path.substringAfterLast("/")

        val fileName = fullName.substringBeforeLast(".")
        val extension = fullName.substringAfterLast(".")

        println("Dir: $directory, name: $fileName, ext: $extension")
    }

    //정규식 사용
    fun parsePathReg(path: String) {
        val regex = """(.+)/(.+)\\.(.+)""".toRegex()    //3중 따옴표 문자열에서는 '\'를 포함한 어떤 문자도 이스케이프 할 팔요 없다.
        val matchResult = regex.matchEntire(path)   //정규식 매치
        if (matchResult != null) {
            val (directory, filename, extension) = matchResult.destructured // 매치에 성공하면 그룹별로 분해한 매치 결과 (destructured) 프로퍼티를 각 변수에 대입한다.
            println("Dir: $directory, name: $filename, ext: $extension")
        }
    }

    /**
     * 로컬 함수, 확장으로 코드 다듬기
     * 같은 코드 반복을 피하기 위해 메소드 추출 리팩토링 -> 재사용은 가능하나, 클래스 안에 작은 메소드가 많아지고, 메소드 간 관계 파악이 힘들어 가독성 낮아질 수 있음.
     * 리팩토링하여 내부 클래스에 넣으면 코드를 깔끔하게 조직할 수는 있으나, 불필요한 준비 코드가 늘어남.
     *
     * Kotlin에서는 함수에서 추출한 함수를 원 함수 내부에 중첩시킬 수 있어서 깔끔하게 코드 조직이 가능하다.
     */
    fun savePersonProto(person: Person) {
        //필요한 검증을 그대로 나열, 여러 필드를 검증할 경우 하나씩 처리하는 코드가 중복.
        if (person.name.isEmpty()) {
            throw IllegalArgumentException(
                "Can't save person ${person.name}: empty Name"
            )
        }
        if (person.address.isEmpty()) {
            throw IllegalArgumentException(
                "Can't save person ${person.name}: empty Address"
            )
        }
    }

    /**
     * 중복 검증 로직은 제거했고 추가적으로 다른 필드 검증도 쉽게 추가 가능하게 변경.
     * person 객체를 validate 로컬 함수에 매번 전달하는 부분은 Kotlin에서 필요 없음.
     * Kotlin의 로컬함수는 자신이 속한 바깥 함수의 모든 파라미터와 변수를 사용할 수 있음.
     */
    fun savePersonFix(person: Person) {
        fun validate(person: Person,
                     value: String,
                     fieldName: String) {
            if (value.isEmpty()) {
                throw IllegalArgumentException(
                    "Can't save person ${person.name}: empty $fieldName"
                )
            }
        }

        validate(person, person.name, "Name")
        validate(person, person.address, "Address")
    }

    /**
     * 위 함수와 같은 내용으로 처리하나 바깥 함수의 변수와 파라미터를 모두 사용할 수 있기 때문에 validate 함수에서 person 생략
     */
    fun savePersonFixTwo(person: Person) {
        fun validate(value: String,
                     fieldName: String) {
            if (value.isEmpty()) {
                throw IllegalArgumentException(
                    "Can't save person ${person.name}: empty $fieldName"
                )
            }
        }

        validate(person.name, "Name")
        validate(person.address, "Address")
    }

    /**
     * 더 깔끔하게 개선하려면 Person클래스를 확장한 함수로 만들기도 가능
     */
    fun Person.validateBeforeSave() {
        fun validate(value: String,
                     fieldName: String) {
            if (value.isEmpty()) {
                throw IllegalArgumentException(
                    "Can't save person $name: empty $fieldName"
                )
            }
        }
        validate(name, "Name")
        validate(address, "Address")
    }

    //이런 식으로 로컬 함수를 써서 코드를 더 깔끔하게 유지하면서 중복을 제거할 수 있다.
    fun savePerson(person: Person) {
        person.validateBeforeSave()
    }

}

var StringBuilder.lastChar: Char
    get() = get(length - 1)
    set(value: Char) {
        this.setCharAt(length - 1, value)
    }

/**
 * 컬렉션 처리
 * 가변 길이 인자(varargs)는 메소드를 호출할 때 원하는 개수만큼 값을 인자로 넘기면 컴파일러가 배열에 값들을 넣어주는 기능이다.
 * Kotlin에서는 자바와 비슷하나 문법이 조금 다르다. 타입 뒤에 ... 대신 vararg 변경자를 붙인다.
 */

public fun <T> listOf(vararg elements: T): List<T> = if (elements.size > 0) elements.asList() else emptyList()

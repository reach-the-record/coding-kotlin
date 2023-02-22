/**
 * Kotlin 타입 시스템
 */
class KotlinType {
}

/**
 * Java - @Nullable, @NotNull annotation을 사용할 수 있으나, IDE 종속 기능이기 때문에 일관적인 NPE 해결을 보장할 수 없고
 * 오류 발생 위치를 정확하게 찾기 위해 적절한 위치에 annotation을 추가하는 것도 쉽지 않다. Optional타입 등 래퍼로 감싸는 방법도 단점 존재.
 *
 * Kotlin -  안전한 호출 연산자 (?.) > null 검사와 메소드 호출을 한 번의 연산으로 수행한다.
 * ex) s?.toUpperCase() ==> if(s ≠ null) s.toUpperCase() else null.
 *
 */

class Address1 (val streetAddress: String, val zipCode: Int,
    val city: String, val country: String)

class Company1 (val name: String, val address: Address1?)

class Person1 (val name: String, val company: Company1?)

fun Person1.countryName(): String {
    val country = this.company?.address?.country
    return if (country != null) country else "Unknown"
}

fun main20(args: Array<String>) {
    val person = Person1("Dmitry", null)
    println(person.countryName())
}

/**
 * 모든 정보가 제대로 있으면 주소를 출력한다.
 * 주소가 없으면 그냥 NullPointerException을 던지는 대신에 의미 있는 오류를 발생시킨다.
 */
fun printShippingLabel(person: Person1) {
    val address = person.company?.address
        ?: throw IllegalArgumentException("No address") // 엘비스 연산자(Elvis Operation)를 사용하여, Null이 아닐 경우 값을 반환, Null일 경우 throw Exception
    with (address) {
        println(streetAddress)
        println("$zipCode $city, $country")
    }
}

fun main21(args: Array<String>) {
    val address = Address1("Elsestr. 47", 80687, "Munich", "Germany")
    val jetbrains = Company1("JetBrains", address)
    val person = Person1("Dmitry", jetbrains)
    printShippingLabel(person)
    printShippingLabel(Person1("Alexey", null))
}

/**
 * 안전한 캐스트 (as?)
 * 'as?' - 값을 대상 타입으로 변환할 수 없으면 null을 반환한다.
 * 파라미터로 받은 값이 원하는 타입인지 쉽게 검사하고 캐스트할 수 있고,
 * 타입이 맞지 않으면 쉽게 false를 반환할 수 있다.
 */
class Person2(val firstName: String, val lastName: String) {
    override fun equals(o: Any?): Boolean {
        val otherPerson = o as? Person2 ?: return false // as? 로 타입 캐스트, 엘비스 연산자로 return 값 반환

        return otherPerson.firstName == firstName &&
                otherPerson.lastName == lastName
    }

    override fun hashCode(): Int =
        firstName.hashCode() * 37 + lastName.hashCode()
}

fun main22(args: Array<String>) {
    val p1 = Person2("Dmitry", "Jemerov")
    val p2 = Person2("Dmitry", "Jemerov")
    println(p1 == p2)
    println(p1.equals(42))
}

/**
 * 느낌표를 이중(!!)으로 사용하면 어떤 값이든 NotNull 타입으로 (강제로) 바꿀 수 있다.
 * 실제 Null에 대해 !!를 적용하면 NPE가 발생한다.
 *
 * 사용 시 유의할 점
 * !!를 넣에 대해 사용해서 발생하는 exception stack trace에는 어떤 파일의 몇 번째 줄인지에 대한 정보는 들어있지만
 * 어떤식에서 예외가 발생했는지에 대한 정보는 들어있지 않기 때문에 어떤 값이 널이었는지 확실히 하기 위해 여러 !! 단언문을 한 줄에 함께 쓰는 일을 피하는 것이 좋다.
 * person.company!!.address!!.country // 예시
 */
fun ignoreNulls(s: String?) {
    val sNotNull: String = s!!
    println(sNotNull.length)
}

/**
 * let 함수
 * let 함수를 사용하면 Null이 될 수 있는 식을 더 쉽게 다룰 수 있다.
 * let 함수를 안전한 호출 연산자와 함께 사용 -> 원하는 식을 평가해서 결과가 널인지 검사 -> 결과를 변수에 넣는 작업 처리.
 * let 함수는 자신의 수신 객체를 인자로 전달받은 람다에게 넘긴다.
 *
 * let 사용 규칙
 * 지정된 값이 null 이 아닌 경우에 코드를 실행해야 하는 경우.
 * Nullable 객체를 다른 Nullable 객체로 변환하는 경우.
 * 단일 지역 변수의 범위를 제한 하는 경우.
 *
 * let을 중첩시켜 처리하면 코드 복잡도가 증가하여 가독성이 떨어지므로 일반적인 if를 사용해 모든 값을 한꺼번에 검사하는 편이 낫다.
 */
fun sendEmailTo(email: String) {
    println("Sending email to $email")
}

fun main23(args: Array<String>) {
    var email: String? = "email@example.com"
    email?.let { sendEmailTo(it) }  //let으로 null이 아닐 경우 sendEmailTo 처리
    email = null
    email?.let { sendEmailTo(it) }  //let으로 null일 경우 skip
}

/**
 * Kotlin에서는 일반적으로 생성자에서 모든 프로퍼티를 초기화해야 한다.
 * 게다가 프로퍼티 타입이 Null이 될 수 없는 타입이라면 반드시 Null이 아닌 값으로 그 프로퍼티를 초기화해야 한다.
 * 초기화 값을 제공할 수 없으면 Null이 될 수 있는 타입을 사용할 수밖에 없다.
 * Null이 될 수 있는 타입을 사용하면 모든 프로퍼티 접근에 Null 검사를 넣거나 !! 연산자를 써야 한다.
 * 이럴 때 lateinit 변경자를 붙이면 프로퍼티를 나중에 초기화할 수 있다.
 */
class MyService {
    fun performAction(): String = "foo"
}

/**
 * Null이 될 수 없는 타입이라 해도 lateinit 변경자를 사용하여 생성자 안에서 초기화할 필요가 없다.
 */
class MyTest {
    private lateinit var myService: MyService // 초기화하지 않고 널이 될 수 없는 프로퍼티를 선언한다.

    fun setUp() {
        myService = MyService()
    }

    fun testAction() {
        myService.performAction() // 널 검사를 수행하지 않고 프로퍼티를 사용한다.
    }
}
/**
 * 산술 연산자 오버로딩
 * 관례를 사용하는 가장 단순한 예는 산술 연산자다.
 * Java에서는 원시 타입에 대해서만 산술 연산자를 사용할 수 있고, 추가로 String에 대해 + 연산자를 사용할 수 있다.
 */
class Overload {
}

/**
 * 이항 산술 연산 오버로딩
 * operator 변경자를 추가해 plus 함수를 선언하고 나면 + 기호로 두 Point 객체를 더할 수 있다.
 *
 * 오버로딩 가능한 이항 산술 연산자
 * a * b -> times
 * a / b -> div
 * a % b -> mod
 * a + b -> plus
 * a - b -> minus
 */
// case 1. 연산자를 자체 함수로 정의하기
data class Point1(val x: Int, val y: Int) {
    operator fun plus(other: Point1): Point1 {
        return Point1(x + other.x, y + other.y)
    }
}

fun main30(args: Array<String>) {
    val p1 = Point1(10, 20)
    val p2 = Point1(30, 40)
    println(p1 + p2)
}

// case 2. 연산자를 확장 함수로 정의하기
data class Point2(val x: Int, val y: Int)

operator fun Point2.plus(other: Point2): Point2 {
    return Point2(x + other.x, y + other.y)
}

/**
 * 연산자를 정의할 때 두 피연산자가(연산자 함수의 두 파라미터)가 같은 타입일 필요는 없다.
 * 연산자 함수의 반환 타입이 꼭 두 피연산자 중 하나와 일치해야만 하는 것도 아니다.
 */
data class Point3(val x: Int, val y: Int)

// case 1. 두 피연산자가 다른 연산자 정의
operator fun Point3.times(scale: Double): Point3 {
    return Point3((x * scale).toInt(), (y * scale).toInt())
}

// case 2. 반환 타입이 피연산자와 다른 연산자 정의
operator fun Char.times(count: Int): String {
    return toString().repeat(count)
}

/**
 * 복합 대입 연산자 오버로딩(compound assignment) : +=, -= 등 연산자
 * 코틀린 표준 라이브러리는 변경 가능한 컬렉션에 대해 plusAssign을 정의한다.
 * 이론적으로코드에 있는 +=를 plus와 plusAssign 양쪽으로 컴파일할 수 있다.
 * 어떤 클래스가 이 두 함수를 모두 정의하고 둘 다 +=에 사용 가능한 경우 컴파일러는 오류를 보고한다.
 */
operator fun <T> MutableCollection<T>.plusAssign(element: T) {
    this.add(element)
}

/**
 * 단항 산술 연산자
 * 이항 연산자 오버로딩하는 절차와 같음.
 *  +a -> unaryPlus
 *  -a -> unaryMinus
 *  !a -> not
 *  ++a, a++ -> inc
 *  --a, a-- -> dec
 */
data class Point4(val x: Int, val y: Int)

operator fun Point4.unaryMinus(): Point4 {
    return Point4(-x, -y)
}

fun main31(args: Array<String>) {
    val p = Point4(10, 20)
    println(-p)
}

/**
 * 비교연산자 오버로딩
 * ==, != -> 연산자를 사용하는 식도 equals 호출로 컴파일된다.
 */
class Point5(val x: Int, val y: Int) {
    override fun equals(obj: Any?): Boolean {
        if (obj === this) return true
        if (obj !is Point5) return false
        return obj.x == x && obj.y == y
    }
}

fun main32(args: Array<String>) {
    println(Point5(10, 20) == Point5(10, 20))
    println(Point5(10, 20) != Point5(5, 5))
    println(null == Point5(1, 2))
}

/**
 * 순서 연산자 : compareTo
 * Comparable에 들어있는 compareTo 메소드는 한 객체와 다른 객체의 크기를 비교해 정수로 나타내준다.
 * Java와 같은 Comparable 인터페이스를 지원한다.
 */
class Person4(
    val firstName: String, val lastName: String
) : Comparable<Person4> {

    override fun compareTo(other: Person4): Int {
        return compareValuesBy(this, other, Person4::lastName, Person4::firstName)  // Kotlin 표준 라이브러리의 compareValuesBy 함수를 사용해 compareTo를 쉽고 간결하게 정의할 수 있다.
    }
}
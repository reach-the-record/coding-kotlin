/**
 * Kotlin
 * Java와 같은 정적 타입 지정 언어.
 * 프로그램 구성 요소의 타입을 컴파일 시점에 알 수 있고, 프로그램 안에서 객체의 필드나 메소드를 사용할 때 마다 컴파일러가 타입을 검증해준다.
 * 코틀린은 기본 가시성이 public이기 때문에 변경자 생략 가능
 * 클래스 임포트와 함수 임포트에 차이가 없고 모든 선언을 import 키워드로 가져올 수 있음. 최상위 함수는 이름을 써서 임포트 가능.
 */
data class Person(
    val name: String,
    val age: Int? = null,    //null이 될 수 있는 타입 / parameter default value
    val address: String
)

/**
 * Java code
 */
/*
public class Person {
    private final String name;
    private final Integer age;
    public Person (String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }
    public Integer getAge() {
        return age;
}
*/
//Java의 프로퍼티 (getter, setter)는 코틀린에서 언어 기본 기능으로 제공한다.
fun main(args : Array<String>) {
    val persons = listOf(
        Person("Alice"),
        Person("Bob", age = 32) // 이름이 명시된 parameter
    )

    val oldest = persons.maxBy { it.age ?: 0 }  //lambda 식과 Elvis operation (?:로 표현하며, ?:의 왼쪽 객체가 non-null이면 그 객체의 값이 리턴되고, null이라면 ?:의 오른쪽 값을 리턴한다.)
    println("The oldest is: $oldest")   //문자열 출력
}

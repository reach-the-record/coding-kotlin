import kotlin.reflect.full.memberProperties

/**
 * 리플렉션 : 실행 시점에 코틀린 객체 내부 관찰
 *
 * 실행 시점에 동적으로 객체의 프로퍼티와 메소드에 접근할 수 있게 해주는 방법.
 * 일반적으로 객체의 메소드나 프로퍼티에 접근할 때는 프로그램 소스코드 안에 구체적인 선언이 있는 메소드나 프로퍼티 이름을 사용하며,
 * 컴파일러는 이름이 실제로 가리키는 선언을 컴파일 시점에 '정적으로' 찾아내서 해당하는 선언이 실제 존재함을 보장한다.
 * 하지만 타입과 관계없이 객체를 다뤄야 하거나 객체가 제공하는 메소드나 프로퍼티 이름을 오직 실행 시점에만 알 수 있는 경우가 있다. ex) JSON 직렬화 라이브러리
 * 직렬화 라이브러리는 어떤 객체든 JSON으로 변환할 수 있어야 하고, 실행 시점이 되기 전까지는 라이브러리가 직렬화할 프로퍼티나 클래스에 대한 정보를 알 수 없다.
 * 이런 경우 리플렉션이 필요하다. Kotlin에서 리플렉션을 사용하려면 두 가지 리플렉션 API를 다뤄야 한다.
 * 1) Java가 java.lang.reflect 패키지를 통해 제공하는 표준 리플렉션 API.
 * -  Kotlin 클래스는 일반 자바 바이트코드로 컴파일되므로 자바 리플렉션 API도 코틀린 클래스를 컴파일한 바이트코드를 완벽히 지원한다.
 * 2) Kotlin이 kotlin.reflect 패키지를 통해 제공하는 코틀린 리플렉션 API.
 * -  Java에는 없는 프로퍼티나 Null이 될 수 있는 타입과 같은 Kotlin 고유 개념에 대한 리플렉션 제공.
 *    현재 Kotlin 리플렉션 API는 Java 리플렉션 API를 완전히 대체할 수 있는 복잡한 기능을 제공하지는 않는다.
 */
class Reflections {
}

/**
 * Kotlin 리플렉션 API: KClass, KCallable, KFunction, KProperty

 * KClass
 * Class::class 또는 KClass라는 식을 쓰면 KClass의 인스턴스를 얻을 수 있다.
 * java.lang.Class에 해당하는 것으로, 클래스를 표현하는 역할을 한다.
 * 모든 선언 열거, 상위 클래스 얻기 등의 작업이 가능하다.
 * 클래스 이름과 그 클래스에 들어있는 프로퍼티 이름을 출력하고 memberProperties를 통해 클래스와 모든 조상 클래스 내부에 정의된 비확장 프로퍼티를 모두 가져온다.

 * KCallable
 * 함수, 프로퍼티의 공통 상위 인터페이스다.
 * call 인터페이스를 제공해 가변 인자와 가변 반환을 할 수 있다.

 * KFunction
 * 함수를 표현하는 역할을 한다.
 * invoke 함수를 제공해서 컴파일 타임에 인자 개수와 타입에 대한 체크를 할 수 있다.
 * KFunction1<Int, Unit>의 형식으로 반환 값 타입 정보를 넣어 활용이 가능하다.

 * KProperty
 * 프로퍼티를 표현한다. (단, 함수의 로컬 변수에는 접근할 수 없다)
 * get 함수를 제공해서 프로퍼티 값을 얻을 수 있다.
 */
class TempData(val name: String, val age: Int)

fun main80(args: Array<String>) {
    val temp = TempData("Alice", 29)
    val kClass = temp::class  //temp.javaClass.kotlin
    println(kClass.simpleName)  //TempData
    kClass.memberProperties.forEach{ println(it.name) } //age, name
}

/**
 * KClass 선언
 * interface KClass<T : Any> {
 *     val simpleName: String?
 *     val qualifiedName: String?
 *     val members: Collection<KCallable<*>>
 *     val constructors: Collection<KFunction<T>>
 *     val nestedClasses: Collection<KClass<*>>
 *     val objectInstance: T?
 *     actual fun isInstance(value: Any?): Boolean
 *     val typeParameters: List<KTypeParameter>
 *     val supertypes: List<KType>
 *     val sealedSubclasses: List<KClass<out T>>
 *     val visibility: KVisibility?
 *     val isFinal: Boolean
 *     val isOpen: Boolean
 *     val isAbstract: Boolean
 *     val isSealed: Boolean
 *     val isData: Boolean
 *     val isInner: Boolean
 *     val isCompanion: Boolean
 *     val isFun: Boolean
 *     val isValue: Boolean
 */

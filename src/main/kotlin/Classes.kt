import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.io.File
import java.io.Serializable
import java.lang.IllegalArgumentException

/**
 * Kotlin 클래스, 인터페이스
 * Java와는 클래스와 인터페이스가 약간 다르다.
 * - 인터페이스에 프로퍼티 선언 가능.
 * - 선언은 기본적으로 final, public
 * - 중첩 클래스는 기본적으로 내부 클래스가 아니다. (외부 클래스에 대한 참조가 없다.)
 * - 클래스를 data로 선언하면 컴파일러가 일부 표준 메소드를 생성해준다.
 * - delegation을 사용하면 위임을 처리하기 위한 준비 메소드를 직접 작성할 필요가 없다.
 *
 */
class Classes {
}

//인터페이스 선언
//인터페이스 내 메소드 디폴트 구현 처리 가능
interface Clickable {
    fun click()
    fun showOff() = println("Clickable!")   //인터페이스 메소드 디폴트 구현
}
/**
 * 인터페이스 구현 - 클래스 뒤 콜론(:) 후 인터페이스, 클래스를 적는 것으로 상속, 구현 처리
 * Java와 마찬가지로 인터페이스는 여러개 한번에 구현 가능하나, 클래스는 하나만 상속 가능
 */

class Button : Clickable {
    override fun click() = println("Clicked!")
}
/**
 * 다중 인터페이스 구현 관계에서 동일한 메소드가 구현되어 있을 경우
 * 중복된 상위 메소드는 하위 클래스에서 반드시 구현되어야 한다는 컴파일 오류가 발생한다.
 * 코틀린의 경우 Java6와 호환이 되도록 설계되었기 때문에 인터페이스 내 디폴트 메소드를 지원하지 않는다.
 * 디폴트 메소드가 있는 인터페이스를 일반 인터페이스와 디폴트 메소드 구현이 정적 메소드로 들어있는 클래스를 조합해 구현한다.
 * 인터페이스에는 메소드 선언만 들어가며, 인터페이스와 함께 생성되는 클래스에는 모든 디폴트 메소드 구현이 정적 메소드로 들어간다.
 */
interface Focusable {
    fun setFocus(b: Boolean) = println("I ${if (b) "got" else "lost"} focus.")

    fun showOff() = println("Focusable!")
}

/**
 * Java에서는 final로 명시적으로 상속을 금지하지 않는 모든 클래스를 다른 클래스가 상속할 수 있다.
 * Kotlin은 기본적으로 final, public이기 때문에 상속, 오버라이딩이 가능하게 하려면 앞에 open을 붙여야 한다.
 * 오버라이드를 허용하고 싶은 메소드나 프러퍼티의 앞에도 open 변경자를 붙여야 한다.
 */
open class RichButton : Clickable { //클래스가 열린 상태. 다른 클래스가 이 클래스를 상속할 수 있다.
    fun disable() {}    //이 함수는 final이다. 하위 클래스가 오버라이드할 수 없다.
    open fun animate() {}   //이 함수는 열려있어서 하위 클래스에서 오버라이드 가능하다.
    override fun click() {} //이 함수는 상위 클래스에서 선언된 메소드를 오버라이드 한다. 오버라이드 메소드는 기본적으로 열려있다.
}

/**
 * abstract로 선언한 추상 클래스는 인스턴스화 할 수 없기 때문에 추상 멤버 앞에 open 변경자를 명시할 필요가 없다.
 * 인터페이스의 경우 final, open, abstract를 사용하지 않는다.
 * 인터페이스는 항상 open이며 final로 변경할 수 없다.
 * 인터페이스 내에 본문이 없으면 자동으로 abstract가 된다.
 */
abstract class Animated {   //추상 클래스이기 때문에 이 클래스의 인스턴스를 만들 수 없다.
    abstract fun animate()  //이 함수는 추상 함수. 구현이 없기 때문에 하위 클래스에서 반드시 구현해야 한다.
    open fun stopAnimating(){}    //추상 클래스에 속해있더라도 비추상함수는 기본적으로 final이기 때문에 필요할 경우 open으로 오버라이드 가능
    fun animateTwice(){}    //추상 클래스에 속해있는 비추상함수, final이므로 하위 클래스에서 오버라이드 불가능.
}

/**
 * 가시성 변경자: 기본적으로 공개
 * Java의 기본 가시성 - 패키지 전용
 * Kotlin에는 기본 가시성이 없다. Kotlin에서는 패키지를 네임스페이스를 관리하는 용도로만 사용하기 때문에 패키지를 가시성 제어에 사용하지 않는다.
 * 패키지 전용 가시성이라는 대안으로 internal이라는 새로운 가시성 변경자 도입.
 * internal - 모듈 내부에서만 볼 수 있다는 뜻. (모듈 - 한 번에 한꺼번에 컴파일되는 코틀린 파일들)
 * 모듈 내부 가시성은 모듈의 구현에 대해 진정한 캡슐화를 제공한다.
 * Kotlin에서는 최상위 선언에 대해 private 가시성을 허용한다. (최상위 선언 - 클래스, 함수, 프로퍼티 등 포함)
 * 최상위 선언은 그 선언이 들어있는 파일 내부에서만 사용할 수 있다.
 * 이 또한 하위 시스템의 자세한 구현 사항을 외부에 감추고 싶을 때 유용한 방법이다.
 */
internal open class TalkativeButton : Focusable {
    private fun yell() = println("Hey!")
    protected fun whisper() = println("Let's talk!")
}
/**
 * 코틀린은 public 함수인 giveSpeech 안에서 그보다 가시성이 더 낮은(이 경우 internal) 타입인 TalkativeButton을 참조하지 못하게 한다.
 * 자바에서는 같은 패키지 안에서 protected 멤버에 접근할 수 있지만, 코틀린에서는 그렇지 않다는 점에서 자바와 코틀린의 protected가 다르다.
 */
/*
fun TalkativeButton.giveSpeech() {
    yell()
    whisper() // 오류: "whisper"에 접근할 수 없음: "whisper"는 "TalkativeButton"의 "protected" 멤버
}*/

/**
 * 자바처럼 코틀린에서도 클래스 안에 다른 클래스를 선언할 수 있다.
 * 자바와의 차이는 코틀린의 중첩 클래스는 명시적으로 요청하지 않는 한 바깥쪽 클래스 인스턴스에 대한 접근 권한이 없다는 점이다.
 */
interface State: Serializable

/**
 * // Java 구현
 * public class Button implements View {
 * 		@Override
 * 		public State getCurrentState() {
 * 				return new ButtonState();
 * 		}
 *
 * 		@Override
 * 		public void resoreState(State state) { /*...*/ }
 * 		public class ButtonState implements State { /*...*/ }
 * }
 */
interface View {
    fun getCurrentState(): State
    fun restoreState(state: State) { }

}

class Button2: View {
    override fun getCurrentState(): State = ButtonState();

    override fun restoreState(state: State) { /*...*/ }

    class ButtonState : State { /*...*/ }   //
}

/**
 * Sealed 클래스 : 클래스 계층 정의 시 계층 확장 제한
 */
interface Expr
class Num(val value: Int): Expr
class Sum(val left: Expr, val right: Expr): Expr

fun eval(e: Expr): Int =
    when (e) {
        is Num -> e.value   //숫자를 표현하는 Num 클래스
        is Sum -> eval(e.left) + eval(e.right) //덧셈 연산을 표현하는 Sum 클래스
        else -> //꼭 else 분기가 있어야 한다.
            throw IllegalArgumentException("Unknown expression")
    }

/**
 * 디폴트 값을 설정하면 새로운 하위 클래스를 추가하더라도 when이 모든 경우를 처리하는 지 제대로 검사할 수 없다.
 * 새로운 클래스 처리를 잊어버렸을 경우 디폴트 분기가 처리되기 때문에 심각한 버그가 발생할 수도 있다.
 * Kotlin에서는 sealed 클래스로 처리가 가능하다.
 * 상위 클래스에 sealed 변경자를 붙이면 상위 클래스에 속한 하위 클래스 정의를 제한할 수 있다.
 * sealed 클래스의 하위 클래스를 정의할 때는 반드시 상위 클래스에 중첩시켜야 한다.
 */
sealed class SealedExpr {
    class Num(val value: Int): SealedExpr()
    class Sum(val left: SealedExpr, val right: SealedExpr): SealedExpr()
}

fun eval2(e: SealedExpr): Int =
    when(e) {
        is SealedExpr.Num -> e.value
        is SealedExpr.Sum -> eval2(e.left) + eval2(e.right)
    }
/**
 * Kotlin은 주 생성자와 부 생성자를 구분하며, 초기화 블록을 통해 초기화 로직을 추가할 수 있다.
 */
class UserProto constructor(_nickname: String) { //파라미터가 하나만 있는 주 생성자
    /*
    초기화 블록은 주 생성자와 함께 사용된다.
    주 생성자는 제한적이기 때문에 별도의 코드를 포함할 수 없으므로 초기화 블록이 필요하다.
    필요하다면 클래스 안에 여러 초기화 블록을 선언할 수 있다.
     */
    val nickname: String

    init {  //초기화 블록
        nickname = _nickname
    }
}

interface User {
    val email: String
    val nickName: String
        get() = email.substringBefore('@') // 프로퍼티에 뒷받침하는 필드가 없다. 대신 매번 결과를 계산해 돌려준다.
}

/**
 * Getter와 Setter에서 필드 접근
 * 프로퍼티에 저장된 값의 변경 이력을 로그에 남기려는 경우를 생각해보자.
 * 그런 경우 변경 가능한 프로퍼티를 정의하되 세터에서 프로퍼티 값을 바꿀 때마다 약간의 코드를 추가로 실행해야 한다.
 */
class User2(val name: String) {
    var address: String = "unspecified"
        set(value: String) {
            println("""
                Address was changed for $name:
                "$field" -> "$value".""".trimIndent())
            field = value
        }
}

/**
 * 접근자의 가시성 변경
 * 접근자의 가시성은 기본적으로 프로퍼티의 가시성과 같다.
 * 필요에 따라 get이나 set 앞에 가시성 변경자를 추가해서 접근자의 가시성을 변경이 가능하다.
 */
class LengthCounter {
    var counter: Int = 0
        private set // 이 클래스 밖에서 이 프로퍼티의 값을 바꿀 수 없다.

    fun addWord(word: String) {
        counter += word.length
    }
}

/**
 * 컴파일러가 생성한 메소드: 데이터 클래스와 메소드 위임
 * Java에서는 클래스가 equals, hashCode, toString 등의 메소드를 구현해야 함.
 * Kotlin에서는 컴파일러가 기계적으로 생성하는 작업을 자동으로 한다.
 * 소스코드를 깔끔하게 유지할 수 있다.
 */

/**
 * 클래스가 데이터를 저장하는 역할만 수행할 경우 toString, equals, hashCode를 반드시 오버라이드 해야 한다.
 * IDE는 자동으로 메소드를 정의해주고 작성된 메소드의 정확성, 일관성을 검사해준다.
 * Kotlin은 data 변경자를 클래스 앞에 붙이면 필요한 메소드를 컴파일러가 알아서 만들어 준다.
 * --- 자동 생성 ---
 * 인스턴스 간 비교를 위한 euqals
 * HashMap과 같은 해시 기반 컨테이너에서 키로 사용할 수 있는 hashCode
 * 클래스의 각 필드를 각 순서대로 표시하는 문자열 표현을 만들어주는 toString
 */
data class Client(val name: String, val postalCode: Int)

/**
 * copy() 메소드
 * data class의 경우 불변 클래스로 만드는 것을 권장한다.
 * HashMap 등의 컨테이너에 데이터 클래스 객체를 담는 경우엔 불변성은 필수적이다.
 * copy() 메소드로 객체를 복사하면서 일부 프로퍼티를 바꿀 수 있다.
 * 복사본은 원본과 다른 생명주기를 가지며, 복사를하면서 일부 프로퍼티 값을 바꾸거나 복사본을 제거해도
 * 프로그램에서 원본을 참조하는 다른 부분에 전혀 영향을 끼치지 않는다.
 */

/**
 * 클래스 위임: by 키워드 사용
 * 데코레이터 패턴 : 상속을 허용하지 않는 클래스에 새로운 동작을 추가해야 할 때 사용하는 일반적인 방법.
 * 핵심은 상속을 허용하지 않는 클래스 대신 사용할 수 있는 새로운 클래스를 만들되 기존 클래스와 같은 인터페이스를
 * 데코레이터가 제공하게 만들고, 기존 클래스를 데코레이터 내부에 필드로 유지하는 것.
 * 이런 접근 방법의 단점은 준비 코드가 상당히 많이 필요하다는 점이다.
 * 코틀린에서는 인터페이스를 구현할 때 by 키워드를 통해 그 인터페이스에 대한 구현을 다른 객체에 위임 중이라는 사실을 명시할 수있다.
 */
class CountingSet<T>(
    val innerSet: MutableCollection<T> = HashSet<T>()
) : MutableCollection<T> by innerSet {

    var objectsAdded = 0

    //add와 addAll을 오버라이드해서 카운터를 증가시키고, MutableCollection 인터페이스의 나머지 메소드는 내부 컨테이너(innerSet)에게 위임한다.
    override fun add(element: T): Boolean {
        objectsAdded++
        return innerSet.add(element)
    }

    override fun addAll(c: Collection<T>): Boolean {
        objectsAdded += c.size
        return innerSet.addAll(c)
    }
}

/**
 * Java - 클래스 생성자를 private으로 제한하고 정적 필드에 객체를 저장하는 Singleton 방식으로 구현
 * Kotlin - 객체 선언 기능을 통해 Singleton을 언엇에서 기본 지원
 * 객체 선언 - 클래스 선언과 클래스에 속한 단일 인스턴스 선언을 합친 선언
 * 객체 선언도 클래스나 인터페이스를 상속할 수 있다.
 * 프레임워크를 사용하기 위해 특정 인터페이스를 구현해야 하는데,
 * 구현 내부에 다른 상태가 필요하지 않은 경우에 이런 기능이 유용하다.
 */
object CaseInsensitiveFileComparator : Comparator<File> {
    override fun compare(file1: File, file2: File): Int {
        return file1.path.compareTo(file2.path,
            ignoreCase = true)
    }
}

fun main12(args: Array<String>) {
    println(CaseInsensitiveFileComparator.compare(
        File("/User"), File("/user")))
    val files = listOf(File("/Z"), File("/a"))
    println(files.sortedWith(CaseInsensitiveFileComparator))
}

/**
 * Singleton 패턴과 DI(Dependency Injection)
 * Singleton 패턴과 마찬가지 이유로 대규모 소프트웨어 시스템에서는 객체 선언이 항상 적합하지는 않다.
 * 객체 생성을 제어할 방법이 없고 생성자 파라미터를 지정할 수 없어서다.
 * 이는 단위 테스트를 하거나 소프트웨어 시스템의 설정이 달라질 때 객체를 대체하거나 객체의 의존관계를 바꿀 수 없다.
 * 그런 기능이 필요하다면 Java와 마찬가지로 DI 프레임워크와 코틀린 클래스를 함께 사용해야 한다.
 */
data class Man2(val name: String) {
    object NameComparator : Comparator<Man2> {   //클래스 안에서 객체를 선언해도 인스턴스는 하나뿐이다.
        override fun compare(p1: Man2, p2: Man2): Int =
            p1.name.compareTo(p2.name)
    }
}

fun main13(args: Array<String>) {
    val men = listOf(Man2("Bob"), Man2("Alice"))
    println(men.sortedWith(Man2.NameComparator))
}

/**
 * 동반 객체: 팩토리 메소드와 정적 멤버가 들어갈 장소
 * Kotlin 클래스 내에는 정적인 멤버가 없음. - Kotlin은 Java static 키워드를 지원하지 않는다.
 * Kotlin에서는 패키지 수준의 최상위 함수와 객체 선언을 활용할 수 있다.
 * 클래스 안에 정의된 객체 중 하나에 companion을 붙이면 그 클래스의 동반 객체로 만들 수 있다.
 * 동반 객체의 프로퍼티나 메소드에 접근하려면 그 동반 객체가 정의된 클래스 이름을 사용한다.
 */
class A {
    companion object {
        fun bar() {
            println("Companion object called")
        }
    }
}

/**
 * 동반 객체 - private 생성자를 호출하기 좋은 위치.
 * 동반 객체는 자신을 둘러싼 클래스의 모든 private 멤버에 접근할 수 있다.
 * 동반 객체는 클래스 안에 정의된 일반 객체라서 동반 객체에 이름을 붙이거나,
 * 동반 객체가 인터페이스를 상속하거나, 동반 객체 안에 확장 함수와 프로퍼티를 정의할 수 있다.
 * 특별히 이름을 지정하지 않으면 동반 객체 이름은 자동으로 Companion이 된다.
 * 동반 객체도 다른 객체 선언과 마찬가지로 인터페이스 구현 가능.
 */
class User3 private constructor(val nickname: String) {
    companion object {
        fun newSubscribingUser(email: String) =
            User3(email.substringBefore('@'))
    }
}

fun main14(args: Array<String>) {
    val subscribingUser = User3.newSubscribingUser("bob@gmail.com")
    println(subscribingUser.nickname)
}

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
 * 
 */
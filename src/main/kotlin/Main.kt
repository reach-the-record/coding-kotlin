import java.lang.IllegalArgumentException

/**
 * 함수 선언 시 fun 키워드.
 * 파라미터 뒤에 : type
 * 클래스 안에 함수를 넣을 필요 없이 최상위 수준 정의 가능
 * 배열처리를 위한 문법이 따로 존재하지 않음
 * 표준 자바 라이브러리를 간결하게 쓸 수 있는 래퍼 제공
 */
fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    /**
     * 문자열 형식 지정 - 문자열 템플릿
     * 문자열 리터럴 필요한 곳에 $변수를 넣는다.
     * $문자를 문자열에 넣어야 할 경우 \로 이스케이프시켜야 한다. (\$)
     */
    val name = if (args.size > 0) args[0] else "Kotlin"
    println("Hello, $name")
}

/**
 * 블록이 본문인 함수
 */
fun max(a: Int, b: Int): Int {
    return if (a > b) a else b
}

/**
 * 식이 본문인 함수
 * 반환 타입 생략 가능
 * fun max2(a: Int, b: Int) = if (a > b) a else b
 * IntelliJ '식 본문으로 변환(Convert to expression body)'과 '블록 본문으로 변환(Convert to block body)'으로 타입 변환 가능
 */
fun max2(a: Int, b: Int): Int = if (a > b) a else b
//변수 타입 생략
val answer = 42
//변수 타입 지정. 초기화식을 사용하지 않고 변수를 선언하려면 타입 지정 필수
val answer2: Int = 42

/**
 * val, var
 * val - (value). java의 final과 같은 변경 불가능한 참조를 저장하는 변수
 * var - (variable). java의 일반 변수에 해당
 * 기본적으로 모든 변수를 val로 사용해 불변 변수로 선언하고, 꼭 필요한 경우만 var로 변경.
 * val 변수는 블록을 실행할 때 정확히 한 번만 초기화해야 하나, 조건에 따라 다른 여러값으로 초기화할 수도 있다.
 * val 참조 자체는 불변이지만, 참고가 가리키는 객체의 내부 값은 변경될 수 있다.
 * var 키워드는 변수의 값을 변경할 수 있지만 타입은 고정된다.
 */
/*
val message: String
if (canPerformOperation()) {
    message = "Success"
    //연산을 수행한다.
} else {
    message = "Failed"
}
*/

/**
 * Java의 instanceOf는 Kotlin에서 is로 대체된다.
 * Java에서는 instanceOf로 타입을 확인 후 변수 타입을 캐스팅 해야 하나, Kotlin에서는 컴파일러가 대신 캐스팅 해준다. (스마트 캐스트)
 */
/*
fun eval(e: Expr): Int {
    if (e is Num) {
        val n = e as Num
        return n.value
    }
    if (e is Sum) {
        return eval(e.right) + eval(e.left)
    }
    throw IllegalArgumentException("Unknown Expression")
}
*/
/**
 * Kotlin에서는 if가 값을 만들어내기 때문에 삼항연산자가 따로 없다.
 * if, else if, else로 변환
 */
/*
fun eval(e: Expr): Int {
    if (e is Num) {
        e.value
    } else if (e is Sum) {
        eval(e.right) + eval(e.left)
    } else {
        throw IllegalArgumentException("Unknown Expression")
    }
}*/

/**
 * if 를 when으로 변환
 */
/*
fun eval(e: Expr): Int {
    when (e) {
        is Num -> e.value
        is Sum -> eval(e.right) + eval(e.left)
        else -> throw IllegalArgumentException("Unknown Expression")
    }
}
*/
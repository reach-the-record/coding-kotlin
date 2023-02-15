/**
 * Iteration: while, for 루프
 * Java와 동일하나 for의 경우 for-each 루프 형태만 존재한다.
 */


fun main(args: Array<String>) {
    /**
     * 100부터 거꾸로 세되 짝수만 반복
     * 역방향 수열
     */
    for (i in 100 downTo 1 step 2) {
        print(i)
    }

    /**
     * 1부터 100까지 짝수만 반복
     * 정방향 수열
     */
    for (i in 1 until 100 step 2) {
        print(i)
    }

    /**
     * in 연산자로 어떤 값이 범위에 속하는지 검사할 수 있다. !in으로는 범위에 속하지 않는 값 검사
     */
    fun recognize(c: Char) = when(c) {
        in '0'..'9' -> "It's a digit!"
        in 'a'..'z' -> "It's a letter!"
        else -> "I don't know..."
    }

    /**
     * 예외 처리
     * 코틀린 예외 처리는 자바와 비슷하다. 다만 코틀린에서는 함수가 던질 수 있는 예외를 선언하지 않아도 된다.
     * Kotlin에서는 throws 절이 없다.
     * 예외가 발생한 경우 catch 블록 다음의 코드는 실행되지 않는다.
     * 코틀린의 try 키워드는 if나 when과 마찬가지로 식이다. 따라서 try의 값을 변수에 대입할 수 있다.
     */

}

class Iteration {
}




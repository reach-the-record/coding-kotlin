/**
 * enum 클래스 안에 메서드를 정의하는 경우에는 반드시 상수 목록과 메서드 정의 사이에 세미콜론을 넣어야 한다.
 */
enum class Color(
    val r: Int, val g: Int, val b: Int
) {
    RED(255, 0, 0),
    ORANGE(255, 165, 0),
    YELLOW(255, 255, 0),
    GREEN(0, 255, 0),
    BLUE(0, 0, 255),
    INDIGO(75, 0, 130),
    VIOLET(238, 130, 238);

    fun rgb() = (r * 256 + g) * 256 + b

    /**
     * Java의 switch와 다르게 when 절에서는 각 분기 끝에 break가 필요 없다.
     */
    fun getMnemonic(color: Color) =
        when (color) {
            Color.RED -> "Richard"
            Color.ORANGE -> "Of"
            Color.YELLOW -> "York"
            Color.GREEN -> "Gave"
            Color.BLUE -> "Battle"
            Color.INDIGO -> "In"
            Color.VIOLET -> "Vain"
        }

    /**
     * Kotlin의 when은 switch보다 훨씬 강력하다. 분기 조건에 상수만 사용할 수 있는 switch와 달리 분기조건에 임의 객체를 허용한다.
     */
    fun mix(c1: Color, c2: Color) =
        when (setOf(c1, c2)) {
            setOf(RED, YELLOW) -> ORANGE
            setOf(YELLOW, BLUE) -> GREEN
            setOf(BLUE, VIOLET) -> INDIGO
            else -> throw Exception("Dirty Color")
        }

    /**
     * 비교 대상을 Set 인스턴스로 생성하는 경우 보통은 큰 문제가 되지 않으나,
     * 자주 호출된다면 불필요한 가비지 객체가 늘어나는 것을 방지하기 위해 고쳐쓰는 것이 좋다.
     * 이 경우는 추가 객체를 만들지 않는다는 장점은 있지만 가독성이 떨어진다는 단점이 있다.
     */
    fun mixOptimized(c1: Color, c2: Color) =
        when {
            (c1 == RED && c2 == YELLOW) || (c2 == RED && c1 == YELLOW) -> ORANGE
            (c1 == YELLOW && c2 == BLUE) || (c2 == YELLOW && c1 == BLUE) -> GREEN
            (c1 == BLUE && c2 == VIOLET) || (c2 == BLUE && c1 == VIOLET) -> INDIGO

            else -> throw Exception("Dirty Color")
        }
}
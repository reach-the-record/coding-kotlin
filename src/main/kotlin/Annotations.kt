/**
 * 애너테이션
 * 애노테이션을 사용하면 라이브러리가 요구하는 의미를 클래스에게 부여할 수 있다.
 */
class Annotations {
}

/**
 * 애너테이션 선언
 * 인자: 원시 타입의 값, 문자열, enum, 클래스 참조, 다른 애노테이션 클래스, 그리고 지금까지 말한 요소들로 이뤄진 배열이 들어갈 수 있다.
 *
 * 인자 지정하는 문법: 자바와 약간 다르다.
 * 클래스를 애노테이션 인자로 지정할 때는 ::class를 클래스 이름 뒤에 넣어야 한다. ex) @MyAnnotation(MyClass::class)
 * 다른 애노테이션을 인자로 지정할 때는 인자로 들어가는 애노테이션의 이름 앞에 @를 넣지 않아야 한다.
 * 배열을 인자로 지정하려면 arrayOf 함수를 사용한다. ex) @RequestMapping(path = arrayOf("/foo", "/bar"))
 * 자바에서 선언한 애노테이션 클래스를 사용한다면 value라는 이름의 파라미터가 필요에 따라 자동으로 가변 길이 인자로 변환된다.
 * 그런 경우에는 @JavaAnnotationWithArrayValue("abc", "foo", "bar")처럼 arrayOf 함수를 쓰지 않아도 된다.
 * 애노테이션 인자를 컴파일 시점에 알 수 있어야 한다. 임의의 프로퍼티를 인자로 지정할 수는 없다. 프로퍼티를 애노테이션 인자로 사용하려면 그 앞에 const 변경자를 붙여야 한다.
 * ex)
 * const val TEST_TIMEOUT = 100L
 * @Test(timeout = TEST_TIMEOUT) fun testMethod() { ... }
 *
 * 메타 애너테이션
 * 애너테이션 클래스에 적용할 수 있는 애너테이션
 * ex)
 * @Target(AnnotaionTarget.PROPERTY)
 * annotation class JsonExclude
 *
 * @Target 메타애너테이션은 애너테이션을 적용할 수 있는 요소의 유형을 지정한다.
 * 애너테이션 클래스에 대해 구체적인 @Target을 지정하지 않으면 모든 선언에 적용할 수 있는 애너테이션이 된다.
 *
 * @Retention 애너테이션
 *
 * @Retention은 정의 중인 애너테이션 클래스를 소스 수준에서만 유지할지 .class 파일에 저장할지,
 * 실행 시점에 리플렉션을 사용해 접근할 수 있게 할지를 지정하는 메타애너테이션이다.
 */


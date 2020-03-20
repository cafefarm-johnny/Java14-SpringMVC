package com.example.demo.records;


/**
 * record는 데이터 클래스다. 코틀린의 데이터 클래스와 유사하다.
 * 특징)
 * - 생성자, getters, equals, hashCode, toString 을 자동으로 만들어준다. (필요한 경우 직접 정의할 수도 있다.)
 * - 각 멤버변수는 private final로 선언된다.
 * - setters 를 지원하지 않는다. (멤버변수가 상수다.)
 * - 블럭'{}' 내에서 필요한 메소드를 정의할 수 있다.
 *
 * 참고: (https://openjdk.java.net/jeps/359, https://www.vojtechruzicka.com/java-records)
 * */
public record DataRecord(String name, int age) {
    // 툭징) 빈 생성자의 경우 각 멤버변수들이 상수로 선언되기 때문에 에러가 발생한다.
    // public DataRecord() {}

    // 생성자를 직접 작성할 수도 있다.
    public DataRecord(String name, int age) {
        if (name.length() <= 2) {
            throw new IllegalArgumentException("name의 길이는 3자리 이상 입력해주세요.");
        } else if (age <= 0) {
            throw new IllegalArgumentException("age는 1보다 커야합니다.");
        }

        this.name = name;
        this.age = age;
    }

    // 필요에 따라 메소드를 정의할 수도 있다.
    public String toUpperName() {
        return this.name.toUpperCase();
    }
}











/**
 * 장점)
 * 1.
 * 보일러플레이트로부터 어느정도 해방된다.
 * 데이터 클래스를 생성 시 *필요한 요소들을 직접 작성하므로써 발생하는 '인간적인 실수들을(오타) 방지'하고, '핵심 기능들을 만드는 것에 집중'할 수 있다.
 *      - * constructor, equals, hashCode, toString, getters
 * 2.
 * 언어에서의 자체 지원이라는 점은 '디버깅의 효율성을 상승'시킬 것이다.
 * (컴파일 시점에 코드를 삽입하는 Lombok에 비해 에러 요소들 빠르게 발견이 가능)
 *
 *
 * 단점)
 * 1.
 * 코틀린의 데이터 클래스는 멤버변수에 var / val 키워드를 지원하여 getter, setter 사용 여부를 개발자에게 위임한다.
 *      - var / val ? 변경이 가능(mutable) : 변경이 불가능(immutable)
 * 그러나 record는 'setter 사용 여부를 개발자에게 권한을 주지 않으므로 좀 더 보수적'이라는 것을 인지해야 한다. (활용할 수 있는 범위가 제한적)
 * 2.
 * record의 특징들은 'Lombok을 통해 대체가능'한 수준이다.
 * 익숙해진 Lombok을 대체할 만큼의 필요성이 있는지 의문이다. (물론 첫 프리뷰 기능이라는 것을 간과해서는 안된다.)
 *
 * 참고: 코틀린 var / val 키워드 (https://hongku.tistory.com/342, http://wonwoo.ml/index.php/post/1495)
 *
 *
 * 확인해보지 못한 점)
 * 1.
 * Lombok 어노테이션과 조합하여 사용할 수 있는지 현재로는 확인 불가 (동일한 기능을 하는 라이브러리와 혼합 사용 시 어떠한 충돌을 일으킬 지도 궁금)
 *      - 사유: IntelliJ EAP 버전이 Lombok 플러그인을 지원하지 않음
 */

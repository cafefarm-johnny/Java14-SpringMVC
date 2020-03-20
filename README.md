# Java 14 Preview Featur R&D

해당 프로젝트는 Java 14의 preview feature를 R&D한 프로젝트입니다.

### 들어가기에 앞서

Java 14에 추가된 feature 중 기능에 관련된 항목만을 설명합니다. (`GC`와 관련된 내용이나 `제거된 패키지`들에 대한 내용은 다루지 않습니다.)

- records (Preview)

- Switch Expressions (Standard)

- Text Blocks (Second Preview)

- - -

## Step Up: Java 14 Setting

**OpenJDK 14 설정은 IntelliJ IDEA를 기준으로 설정합니다.**

* Java 14를 사용하기 위해서는 `IntelliJ의 얼리엑세스(Early Access Program)`버전이 필요합니다.

```
IntelliJ EAP 설치 (https://www.jetbrains.com/ko-kr/resources/eap/)
```

1. JDK는 OpenJDK 14버전을 필요로 합니다.

2. IntelliJ EAP 버전으로 프로젝트를 Open합니다.

3. `File → Project Structure → Project Settings → Project → Project SDK`항목의 버전을 `'OpenJDK 14'` 버전으로 설정합니다.

4. `File → Project Structure → Project Settings → Project → Project Language Level`을 
`'14 (Preview) - Records, patterns, text blocks'` 항목으로 선택합니다.

```
Java 14 Preview Feature & Spring MVC 참고 (https://youtu.be/mr-7kGy8Yao)
```

- - -

## R&D 중 발견된 이슈 리스트

- records (Preview)
        
    - 컨트롤러에서 응답 객체 타입으로 record 타입을 사용할 경우 Jackson2HttpMessageConverter가 ObjectWriter를 통해 `record 객체를 JSON 형태로 시리얼라이즈하는 과정에서 에러가 발생`합니다. 
    <img src="./img/InvalidDefinitionException.png">
    <br>
    **원인)** 
    <br>
    ObjectWriter는 기본 설정으로 `getters를 참조`하여 시리얼라이즈를 진행하는데, getters 정의를 record로 전가하는 경우 
    `메소드 네이밍 컨벤션에 get이라는 키워드가 누락되어 시리얼라이즈에 실패`합니다.
    <br>
    이 경우 @JsonAutoDetect / @JsonProperty 어노테이션을 통하여 `참조 대상을 필드의 멤버변수로 설정`하거나, 
    `메소드 네이밍에 get`이 들어가도록 getters를 직접 정의하여 문제를 우회할 수 있습니다.
        
    - ObjectMapper로 convertValue() 메소드를 통해 `Object to record 간 값 복사를 시도하는 경우 InvalidDefinitionException 에러가 발생`합니다.
    <img src="./img/(ObjectMapper)InvalidDefinitionException.png">
    <br>
    **원인)**
    <br>
    record는 멤버변수들을 생성할 때 `상수로 선언되기 때문에 인자 없는 생성자를 정의할 수 없습니다.`
    <br>
    반면 ObjectMapper의 convertValue() 메소드는 `인자 없는 생성자를 요구`하기 때문에 복사를 수행할 수가 없습니다.
        
    - `Spring Boot 2.3.0.M3`버전에 정의된 자식 의존성 중 `Spring Framework 5.2.4.RELEASE` 버전을 사용할 경우 
    `java.lang.UnsupportedOperationException` 예외가 발생합니다.
    <img src="./img/UnsupportedOperationException.png">
    <br.
    **원인)**
    <br>
    *ASM 라이브러리 버전 업데이트로 인한 호환성 이슈로, `Spring Framework`의 버전을 
    `5.2.3.RELEASE로 패치버전 다운그레이드`를 진행해야 records 기능을 사용할 수 있습니다. 
        
**ASM이란?**

ASM은 자바 바이트 코드를 분석하고 다루는 라이브러리이며, 스프링 프레임워크에 포함되어 있습니다.

```
ASM 참고 (https://www.roseindia.net/spring/spring3/spring-3-asm.shtml)
```

- Switch Expressions (Standard)

- Text Blocks (Second Preview)
# Java 14 Preview Feature & Spring MVC

해당 프로젝트는 Java 14의 preview feature (switch 표현식, record 등)를 사용해보고 Spring MVC로 사용해보는 학습 프로젝트입니다.

## Step Up: Java 14 Preview Feature

### 들어가기에 앞서

Java 14에 추가된 feature 중 *기능에 관련된 항목*만을 설명합니다. (`GC`와 관련된 내용이나 `제거된 패키지`들에 대한 내용은 다루지 않습니다.)

- records (Preview)

    - 불안정한 기능인 만큼 현재 `Reflection`과 연관된 기능들(RowMapper)에 이슈가 발생하고 있다고 합니다.
    
    - 그 외 records 기능을 사용했을 때 다음과 같은 이슈가 확인되었습니다.
        
        - `Spring Boot 2.3.0.M3`버전에서 정의된 의존성 중 `Spring Framework 5.2.4.RELEASE` 버전을 사용할 경우 `java.lang.UnsupportedOperationException` 예외가 발생합니다.
        
            - 이는 *ASM 라이브러리 버전 업데이트로 인한 호환성 이슈로, `Spring Framework`의 버전을 `5.2.3.RELEASE로 다운그레이드`를 진행해야 records 기능을 사용할 수 있습니다. 

- Switch Expressions (Standard)

- Text Blocks (Second Preview)

**ASM이란?**

ASM은 자바 바이트 코드를 분석하고 다루는 스프링 프레임워크에 포함되어 있는 라이브러리입니다.

자세한 내용은 다음 링크를 참고해주세요.

[ASM] (https://www.roseindia.net/spring/spring3/spring-3-asm.shtml, "Spring ASM link")
- - -

**해당 내용은 IntelliJ IDEA를 기준으로 설정합니다.**

* Java 14 preview feature를 사용하기 위해서는 `IntelliJ의 얼리엑세스(Early Access Program)`버전이 필요합니다.

[IntelliJ EAP] (https://www.jetbrains.com/ko-kr/resources/eap/, "IntelliJ EAP link")

1. JDK는 OpenJDK 14버전을 필요로 합니다.

2. IntelliJ EAP 버전으로 프로젝트를 Open합니다.

3. `File → Project Structure → Project Settings → Project → Project SDK`항목의 버전을 `'OpenJDK 14'` 버전으로 설정합니다.

4. `File → Project Structure → Project Settings → Project → Project Language Level`을 `'14 (Preview) - Records, patterns, text blocks'` 항목으로 선택합니다.

[Java 14 Preview Feature 참고] (https://openjdk.java.net/projects/jdk/14/, "Java 14 Preview Feature link")

[Spring MVC 참고] (https://youtu.be/mr-7kGy8Yao, "youtube link")

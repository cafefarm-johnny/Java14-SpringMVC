package com.example.demo.sw;

import java.util.Random;

/**
 * switch 문에서 값을 산출하고 싶은 경우 switch 표현식을 사용한다.
 */
public class SwitchExpressions {

    public void run() {
        var random = new Random().nextInt(6);

        // yield 구문은 변수에 할당하는 switch 표현식에 사용된다. (산출해야 할 데이터가 있는 switch 표현식으로 사용하는 경우)
        var result = switch (random) {
            case 1, 2, 3, 4, 5:
                yield random;
            default:
                yield "default";
        };

        System.out.println("yield 패턴 : " + result);

        // Arrow는 산출할 데이터가 없는 경우 사용된다.
        switch (random) {
            case 1, 2, 3, 4, 5 -> System.out.println("Arrow 패턴 : " + random);
            default -> System.out.println("Arrow 패턴 : default");
        }
    }
}

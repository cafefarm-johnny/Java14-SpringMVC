package com.example.demo.sw;

import java.util.Random;

/**
 * switch 표현식은 case 블록 내에서 값을 산출하는 작업을 좀 더 간결한 표현 문법으로 처리할 수 있게 해준다.
 */
public class SwitchExpressions {

    // 예제: 랜덤 난수를 만들고 만들어진 난수가 홀수/짝수인지 알아보기
    enum OddOrEven {
        ODD, EVEN;
    }

    public void run() {
        System.out.println("===== switch expressions =====");


        int random = new Random().nextInt(6); // 0 ~ 5 범위의 랜덤 난수 값 생성

        // 기존의 switch문 (switch statement)
        OddOrEven switchStatement;
        switch (random) {
            case 1:
            case 3:
            case 5: // fall-through
                switchStatement = OddOrEven.ODD;
                break;
            case 2:
            case 4:
                switchStatement = OddOrEven.EVEN;
                break;
            default:
                System.out.println("random: " + random);
                throw new RuntimeException("random 값이 예상 범위를 넘었습니다.");
        }
        System.out.println("switch statement: " + switchStatement);


        // switch 표현식 (switch expressions)
            // 산출할 값이 있는 경우 사용하는 yield 키워드 사용
        OddOrEven switchExpression = switch (random) {
            case 1, 3, 5:
                yield OddOrEven.ODD;
            case 2, 4:
                yield OddOrEven.EVEN;
            default:
                System.out.println("random: " + random);
                throw new RuntimeException("random 값이 예상 범위를 넘었습니다.");
        };
        System.out.println("switch expression: " + switchExpression);


            // 람다 표현식으로 표현을 조금 더 단축
        OddOrEven switchLambda = switch (random) {
            case 1, 3, 5 -> OddOrEven.ODD;
            case 2, 4 -> OddOrEven.EVEN;
            default -> {
                System.out.println("random: " + random);
                throw new RuntimeException("random 값이 예상 범위를 넘었습니다.");
            }
        };
        System.out.println("switch expression lambda: " + switchLambda);


        System.out.println("===== switch expressions =====\n");
    }


    /**
     * 장점)
     * - switch 문의 case, break 키워드의 불필요한 반복 작성을 줄여준다.
     * - 코드의 라인이 줄어들고, 가독성이 상승한다.
     * 단점)
     * - ...새로운 문법의 어색함?
     */
}

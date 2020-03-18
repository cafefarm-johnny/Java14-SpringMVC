package com.example.demo.pm;

import com.example.demo.pm.item.Glove;
import com.example.demo.pm.item.Shoes;

/**
 * 패턴 매칭은 인스턴스의 타입을 비교할 때 사용한다.
 * 따라서 instanceof 연산자에서 사용할 수 있다.
 */
public class PatternMatching {

    private int before(Object equipment) {
        if (equipment instanceof Shoes) {
            Shoes shoes = (Shoes) equipment; // 지역 변수를 만들고, 형변환하는 코드를 작성해야 한다.
            return shoes.size;
        } else if (equipment instanceof Glove) {
            Glove glove = (Glove) equipment;
            return glove.size;
        }

        throw new IllegalArgumentException("장비 타입을 알 수 없습니다.");
    }

    public int compute(Object equipment) {
        if (equipment instanceof Shoes shoes) { // 조건부에서 객체의 구성 요소를 뽑아낸다. (쉽고, 간결하고, 읽기 좋다.)
            return shoes.size;
        } else if (equipment instanceof Glove glove) {
            return glove.size;
        }

        throw new IllegalArgumentException("장비 타입을 알 수 없습니다.");
    }
}

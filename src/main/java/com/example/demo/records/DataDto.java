package com.example.demo.records;

import java.util.Objects;

public class DataDto {
    private final String name;
    private final int age;

    // 이것이 생성자이고
    public DataDto(String name, int age) {
        if (name.length() <= 2) {
            throw new IllegalArgumentException("name의 길이는 3자리 이상 입력해주세요.");
        } else if (age <= 0) {
            throw new IllegalArgumentException("age는 1보다 커야합니다.");
        }

        this.name = name;
        this.age = age;
    }

    // 이것이 getters 이며
    public String getName() { return name; }
    public int getAge() { return age; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataDto dto = (DataDto) o;
        return age == dto.age &&
                Objects.equals(name, dto.name);
    }

    @Override
    public int hashCode() { return Objects.hash(name, age); }

    // 이것들이 오버라이딩한 equals, hashCode, toString 이라는 것을 알기 위해 눈으로 보는 시간은 길어봐야 1 ~ 2분이다.
    @Override
    public String toString() {
        return "Dto{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
    // 1 ~ 2분으로 이해하기 위해 읽은 코드라인은 무려 '23'라인이다.'

    public String toUpperName() {
        return this.name.toUpperCase();
    }
}

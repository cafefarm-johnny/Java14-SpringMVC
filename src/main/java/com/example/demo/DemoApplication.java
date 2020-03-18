package com.example.demo;

import com.example.demo.pm.PatternMatching;
import com.example.demo.pm.item.Glove;
import com.example.demo.pm.item.HairBand;
import com.example.demo.pm.item.Shoes;
import com.example.demo.sw.SwitchExpressions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.List;

/**
 * JAVA 14 신규 구조 기능을 사용하려면 IDEA를 EAP(Early Access) 버전으로 설치해야 Java 14 Preview-Feature를 사용할 수 있다.
 */
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

        // switch
        var sw = new SwitchExpressions();
        sw.run();

        // pattern matching
        var pm = new PatternMatching();
        System.out.println("shoes size : " + pm.compute(new Shoes(265)));
        System.out.println("glove size : " + pm.compute(new Glove(10)));
        System.out.println("hair band size : " + pm.compute(new HairBand(55)));
    }

}

@Component
class Runner {

    private final PeopleService service;

    Runner(PeopleService service) {
        this.service = service;
    }

    @EventListener(ApplicationReadyEvent.class) // EventListener 등장 이전에는 ApplicationListner 인터페이스를 상속하여 onApplicationEvent 메소드를 오버라이딩하여 구현했다 (Spring 4.2 이전)
    public void onApplicationEventHandler() throws Exception {
        // Create
        var johnny = this.service.create("johnny", EmotionalState.NEUTRAL);
        System.out.println(johnny);
        System.out.println("johnny to Upper case : " + johnny.upperedName()); // additional methods

        // Read
        var findJohnny = this.service.findById(1);
        System.out.println("found by id : " + findJohnny);

        // throw IllegalArgumentException
//        var blank = this.service.create("j", EmotionalState.HAPPY); // compact constructor
//        System.out.println(blank);
    }
}

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//class Person {
//    private Integer id;
//    private String name;
//    int emotionalState;
//
//    public Person() {}
//    public Person(Integer id, String name, int emotionalState) {
//        if (name.length() <= 0) {
//            throw new IllegalArgumentException("이름을 작성하고 시도해주세요.");
//        }
//    }
//
//    public String upperedName() {
//        return this.name.toUpperCase();
//    }
//}

// @Data @AllArgsConstructor @NoArgsConstructor를 사용하여 DTO를 만드는 것 처럼 사용할 수 있다.
record Person(Integer id, String name, int emotionalState) {
    //  (compact constructor) 생성자를 간편하게 만들 수 있다.
    public Person {
        if (name.length() <= 2) {
            throw new IllegalArgumentException("이름을 작성하고 시도해주세요.");
        }
    }

    // (additional methods) 메소드를 추가하여 사용할 수 있다.
    public String upperedName() {
        return this.name.toUpperCase();
    }
}

enum EmotionalState {
    SAD, HAPPY, NEUTRAL
}

@Service
class PeopleService {
    private final JdbcTemplate template;

    private final String insertSql =
            """
                insert into PEOPLE (name, emotional_state) values (?, ?)
            """;

    private final String findByIdSql =
            """
                select * from PEOPLE 
                where ID = ?
            """;

    PeopleService(JdbcTemplate template) {
        this.template = template;
    }

    private final RowMapper <Person> personRowMapper = (rs, rowNum) -> new Person(rs.getInt("id"),
            rs.getString("name"),
            rs.getInt("emotional_state"));

    public Person create(String name, EmotionalState state) {

        // 과거 기존 switch 문을 이용한 enum 처리 방식
//        int enumIndex = 0;
//
//        switch (state) {
//            case SAD:
//                enumIndex = -1;
//                break;
//            case HAPPY:
//                enumIndex = 1;
//                break;
//            case NEUTRAL:
//                enumIndex = 0;
//        }

        // Java 10의 타입 추론 기능이 추가된 var 타입과 Java 14 버전에 추가된 switch 표현식의 콤비네이션
        var enumIndex = switch (state) {
            case SAD -> -1;
            case HAPPY -> 1;
            case NEUTRAL -> 0;
        };

        var declaredParameters = List.of(new SqlParameter(Types.VARCHAR, "name"),
                new SqlParameter(Types.INTEGER, "emotional_state"));
        var pscf = new PreparedStatementCreatorFactory(this.insertSql, declaredParameters) {
            {
                setReturnGeneratedKeys(true);
                setGeneratedKeysColumnNames("id");
            }
        };
        var psc = pscf.newPreparedStatementCreator(List.of(name, enumIndex));
        var kh = new GeneratedKeyHolder();
        this.template.update(psc, kh);

        // 과거 instanceof연산자로 비교 후 블록 내에서 캐스팅 처리를 진행하는 기존 방식
        // if (kh.getKey() instanceof Integer) {
        //     return findById((Integer) kh.getKey());
        // }

        // Java 14에 추가된 Pattern Matching 기능을 이용하여 조건문 내에서 인스턴스 비교 후 캐스팅을 하는 방식
        // 다음 링크에서 더욱 다양한 활용법을 볼 수 있습니다. (https://cr.openjdk.java.net/~briangoetz/amber/pattern-match.html)
        if (kh.getKey() instanceof Integer id) {
            return findById(id);
        }

        throw new IllegalArgumentException("사용자를 추가할 수 없습니다." + Person.class.getName());
    }

    public Person findById(Integer id) {
        return this.template.queryForObject(this.findByIdSql, new Object[] {id}, this.personRowMapper);
    }

}
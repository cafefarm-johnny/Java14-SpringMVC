package com.example.demo;

import com.example.demo.pm.PatternMatching;
import com.example.demo.pm.item.Glove;
import com.example.demo.pm.item.HairBand;
import com.example.demo.pm.item.Shoes;
import com.example.demo.sw.SwitchExpressions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
package com.crappyengineering.springboot.demo.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class HelloRepository {

    Map<String, Integer> nameOccurenceMap;

    public HelloRepository() {
        this.nameOccurenceMap = new HashMap<>();
    }

    public int getHelloCount(String name) {
        return nameOccurenceMap.getOrDefault(name, 0);
    }

    public int addHello(String name) {
        if(nameOccurenceMap.containsKey(name)) {
            int count = nameOccurenceMap.get(name);
            nameOccurenceMap.put(name, count++);
            return count;
        } else {
            nameOccurenceMap.put(name, 1);
            return 1;
        }
        //return nameOccurenceMap.computeIfPresent(name, (s, integer) -> integer++);
    }


}

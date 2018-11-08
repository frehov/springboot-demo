package com.crappyengineering.springboot.demo.service;

import com.crappyengineering.springboot.demo.repository.HelloRepository;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    private HelloRepository helloRepository;

    public HelloService(HelloRepository helloRepository) {
        this.helloRepository = helloRepository;
    }

    public int countTimesSaidHello(String name) {
        return helloRepository.addHello(name);
    }

}

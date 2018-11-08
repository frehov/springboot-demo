package com.crappyengineering.springboot.demo.controller;

import com.crappyengineering.springboot.demo.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    private HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping
    public String hello() {
        return "Hello Anon";
    }

    @GetMapping(path = "/{name}")
    public String helloWithName(@PathVariable String name) {
        return String.format("%d - Hello %s!", helloService.countTimesSaidHello(name), name);
    }

}

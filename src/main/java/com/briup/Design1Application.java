package com.briup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Design1Application {

    public static void main(String[] args) {
        SpringApplication.run(Design1Application.class, args);
    }

    @GetMapping("/hello")
    public Object hello() {
        return "hello world!";
    }
}

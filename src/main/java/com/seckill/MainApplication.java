package com.seckill;

import com.seckill.controller.DemoController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication

public class MainApplication {
    public static void main(String[] args) throws Exception{
        SpringApplication.run(MainApplication.class, args);
    }
}
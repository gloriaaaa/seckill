package com.seckill;

import com.seckill.controller.SampleController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
//public class MainApplication extends SpringBootServletInitializer {
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(MainApplication.class);
//
//    }
//    public static void main(String[] args) throws Exception{
//        SpringApplication.run(MainApplication.class, args);
//    }
//}

public class MainApplication {
    public static void main(String[] args) throws Exception{
        SpringApplication.run(MainApplication.class, args);
    }
}

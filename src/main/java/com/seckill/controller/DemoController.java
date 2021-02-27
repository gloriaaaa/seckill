package com.seckill.controller;

import com.seckill.domain.User;
import com.seckill.redis.RedisService;
import com.seckill.result.CodeMsg;
import com.seckill.result.Result;
import com.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/demo")
//controller方法分两类：1.rest api json输出 2.页面
public class DemoController {
    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/")
    @ResponseBody
    String home()
    {
        return "hello world!";
    }
    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello()
    {
        return Result.success("hello world");
        //return new Result(0,"success","hello world!");
    }
    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model)
    {
        model.addAttribute("name","gloria");
        return "hello";
    }
//    @RequestMapping("/helloError")
//    @ResponseBody
//    public Result<String> helloerror()
//    {
//        return Result.error(CodeMsg.SERVER_ERROR);//这里CodeMsg是一个对象可以使得封装code和message
//        //return new Result(0,"success","hello world!");
//    }
    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet()
    {
        User user = userService.getById(1);
        return Result.success(user);
        //return new Result(0,"success","hello world!");
    }
    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx()
    {
        userService.tx();
        return Result.success(true);
        //return new Result(0,"success","hello world!");
    }
    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<Boolean> redisGet()
    {
        redisService.get(String,Class<T> clazz);
        return Result.success(true);
        //return new Result(0,"success","hello world!");
    }




    //controller方法分两类：1.rest api json输出 2.页面
//    @RequestMapping("/demo/thymeleaf")
//    public String thymeleaf(Model model){
//        model.addAttribute("name","gloria");
//        return "hello";
//    }
//    @RequestMapping("/db/get")
//    @ResponseBody
//    public Result<String> dbGet()
//    {
//    }


}

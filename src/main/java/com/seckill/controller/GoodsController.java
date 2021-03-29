package com.seckill.controller;

import com.seckill.domain.MiaoshaUser;
import com.seckill.redis.RedisService;
import com.seckill.service.MiaoshaUserService;
import com.seckill.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/to_list")
//    public String list(HttpServletResponse response,Model model,
//                          @CookieValue(value=MiaoshaUserService.COOKI_NAME_TOKEN,required = false) String cookieToken,
//                          @RequestParam(value=MiaoshaUserService.COOKI_NAME_TOKEN,required = false) String paramToken){
//        if(StringUtils.isEmpty(cookieToken)&&StringUtils.isEmpty(paramToken))
//        {
//            return "login";
//        }
//        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
//        MiaoshaUser user = userService.getByToken(response,token);
//        model.addAttribute("user",user);
//        return "goods_list";
//    }
    public String list(Model model, MiaoshaUser user) {
        model.addAttribute("user", user);
        return "goods_list";
    }
}

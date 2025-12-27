package com.myproject.controller;

import com.myproject.pojo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @GetMapping("/list")
    public Result list() {
        //验证token(废案)(现在有拦截器了不需要这样一个个验证)
//        try {
////            System.out.println("HereHereHereHere HereHere");
//            Map<String, Object> claims = JwtUtil.parseToken(token);
//            return Result.success("所有的文章数据:");
//        } catch (Exception e) {
//            response.setStatus(401);
//            return Result.error("未登录");
//        }

            return Result.success("所有的文章数据:");

    }
}

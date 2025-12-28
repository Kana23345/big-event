package com.myproject.controller;

import com.myproject.pojo.Article;
import com.myproject.pojo.Result;
import com.myproject.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @PostMapping()
    public Result add(@RequestBody @Validated Article article){
        articleService.add(article);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody @Validated Article article){
        articleService.update(article);
        return Result.success();
    }

    @GetMapping("/detail")
    public Result<Article> detail(@RequestParam Integer id){
        Article article=articleService.detail(id);
        if(article==null){
            return Result.error("文章不存在");
        }
        return Result.success(article);
    }

    @DeleteMapping()
    public Result<Article> delete(@RequestParam Integer id){

        Article article=articleService.detail(id);
        if(article==null){
            return Result.error("文章不存在");
        }
        articleService.delete(id);
        return Result.success();
    }




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

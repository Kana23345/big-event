package com.myproject.controller;

import com.myproject.pojo.Category;
import com.myproject.pojo.Result;
import com.myproject.service.CategoryService;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public  Result<List<Category>> list(){

        List<Category> list= categoryService.list();
        return Result.success(list);

    }

    @PostMapping()
    public Result add(@RequestBody @Validated(Category.Add.class) Category category){

        categoryService.add(category);
        return Result.success();
    }

    @GetMapping("/detail")
    public Result<Category> detail(@RequestParam int id)
    {
       Category category=categoryService.detail(id);
        return  Result.success(category);
    }

    @PutMapping()
    public Result update(@RequestBody @Validated(Category.Update.class) Category category){
        if(categoryService.findById(category.getId())==null)
            return Result.error("分类不存在或id错误");
        categoryService.update(category);
        return Result.success();
    }


    @DeleteMapping()
    public Result<Object> delete(@RequestParam Integer id)
    {
        Category category=categoryService.findById(id);
        if(category==null)
            return Result.error("分类不存在或id错误");

        categoryService.delete(id);
        return Result.success(category);
    }

}

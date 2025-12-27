package com.myproject.service;

import com.myproject.pojo.Category;

import java.util.List;

public interface CategoryService {
    void add(Category category);

    List<Category> list();

    Category detail(int id);

    void update( Category category);

    void delete(Integer id);

    Category findById(int id);
}

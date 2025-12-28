package com.myproject.service;

import com.myproject.pojo.Article;

public interface ArticleService {
    void add(Article article);

    void update(Article article);

    Article detail(Integer id);

    void delete(Integer id);
}

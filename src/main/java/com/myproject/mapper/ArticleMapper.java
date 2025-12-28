package com.myproject.mapper;

import com.myproject.pojo.Article;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ArticleMapper {




//添加
@Insert("INSERT INTO article(title, content, cover_img, state, category_id, create_user, create_time, update_time) VALUES(#{title}, #{content}, #{coverImg}, #{state}, #{categoryId}, #{createUser}, NOW(), NOW())")
    void add(Article article);
//更新
@Update("UPDATE article SET title=#{title}, content=#{content}, cover_img=#{coverImg}, state=#{state}, category_id=#{categoryId}, update_time=NOW() WHERE id=#{id}")
    void update(Article article);

//查找
@Select("SELECT * FROM article WHERE id=#{id}")
    Article detail(Integer id);
//删除
@Delete("DELETE FROM article WHERE id=#{id}")
    void delete(Integer id);
}

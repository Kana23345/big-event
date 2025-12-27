package com.myproject.mapper;

import com.myproject.pojo.Category;
import com.myproject.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {


    //列出文章分类
    @Select("select * from category where create_user = #{id}")
    List<Category> list(Integer id);

    //添加文章分类
    @Insert("insert into category(category_name,category_alias,create_user,create_time,update_time) " +
            "values(#{categoryName},#{categoryAlias},#{createUserId},#{createTime},#{updateTime})")
    void add(Category category);

    //获取详情
    @Select("select * from category where id = #{id}")
    Category detail(int id);

    //更新
    @Update("update category set category_name=#{categoryName},category_alias=#{categoryAlias},update_time=#{updateTime} where id=#{id}")
    void update(Category category);

    //根据id删除
    @Delete("delete from category where id=#{id}")
    void deleteById(Integer id);


    //查询
    @Select("select * from category where id=#{id}")
    Category findById(int id);
}

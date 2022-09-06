package com.example.backend.Mapper;

import com.example.backend.entity.Project;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
//    是否存在
    @Select("select flag from file where name=#{name}")
    String nameForFlag(String name);
//    不存在插入
    @Insert("insert into file(name,flag) values(#{name},#{name})")
    Integer insertFile(String name);
//    删除
    @Delete("delete from file where name=#{name}")
    Integer removeFile(String name);
//   所有图片
    @Select("select name from file")
    List<String> searchAll();
}

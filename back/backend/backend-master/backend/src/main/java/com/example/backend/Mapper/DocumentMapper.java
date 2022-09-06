package com.example.backend.Mapper;

import com.example.backend.entity.Document;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DocumentMapper {
    @Select("select * from document where fileName=#{fileName} and pjctName=#{pjctName} and teamName=#{teamName}")
    Document isCovered(String fileName,String pjctName,String teamName);
//    插入数据
    @Insert("insert into document(fileName,curcontent,pjctName,teamName) values(#{fileName},#{curcontent},#{pjctName},#{teamName})")
    Integer insertDocument(String fileName, String curcontent,String pjctName,String teamName);
//    更新
    @Update("update document set curcontent=#{curcontent} where fileName=#{fileName} and pjctName=#{pjctName} and teamName=#{teamName}")
    Integer cover(String fileName, String curcontent,String pjctName,String teamName);
//    删除
    @Delete("delete from document where fileName=#{fileName} and pjctName=#{pjctName} and teamName=#{teamName}")
    Integer del(String fileName,String pjctName,String teamName);
    //    显示特定队伍项目所有文件
    @Select("select * from document where pjctName=#{pjctName} and teamName=#{teamName}")
    List<Document> search(String pjctName,String teamName);
    //    显示特定队伍所有文件
    @Select("select * from document where teamName=#{teamName}")
    List<Document> searchteamFiles(String teamName);
}

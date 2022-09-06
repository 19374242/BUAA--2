package com.example.backend.Mapper;

import com.example.backend.entity.Project;
import com.example.backend.entity.Team;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProjectMapper {
    //    插入项目表
    @Insert("insert into project(teamName,pjctName,delFlag) values(#{teamName},#{pjctName},0)")
    Integer createProject(String teamName, String pjctName);

    //    项目是否已存在
    @Select("select * from project where teamName=#{teamName} and pjctName=#{pjctName}")
    Project projectNameAndPjctNameNameForProject(String teamName, String pjctName);

    //    获取团队所有项目
    @Select("select * from project where teamName=#{teamName} and delFlag = 0")
    List<Project> getProjects(String teamName);

    //    删除项目
    @Update("update project set delFlag = 1 where teamName=#{teamName} and pjctName=#{pjctName}")
    Integer delPreoject(String teamName, String pjctName);

    //    获取已删除项目
    @Select("select * from project where teamName=#{teamName} and delFlag = 1")
    List<Project> getDelProjects(String teamName);

    //    恢复已删除项目
    @Update("update project set delFlag = 0 where teamName=#{teamName} and pjctName=#{pjctName}")
    Integer recoverProject(String teamName, String pjctName);

    //    彻底删除项目
    @Delete("delete from project where teamName=#{teamName} and pjctName=#{pjctName}")
    Integer removeProject(String teamName, String pjctName);

    //    重命名项目
    @Update("update project set pjctName = #{newName} where teamName=#{teamName} and pjctName=#{pjctName}")
    Integer renamePreoject(String teamName, String pjctName, String newName);
//    一个人所有项目
    @Select("select * from project,team where email=#{email} and delFlag = 0 and project.teamname=team.teamname")
    List<Project> oneForProjects(String email);
//    搜索个人项目
    @Select("select * from project,team where email=#{email} and delFlag = 0 and project.teamname=team.teamname and pjctName like #{word}")
    List<Project> searchOnesProjects(String email,String word);
//    搜索团队项目
    @Select("select * from project where teamName=#{teamName} and delFlag = 0 and pjctName like #{word}")
    List<Project> searchTeamProjects(String teamName,String word);
}


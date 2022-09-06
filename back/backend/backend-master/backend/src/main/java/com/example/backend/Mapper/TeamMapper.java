package com.example.backend.Mapper;

import com.example.backend.entity.Team;
import com.example.backend.entity.TeamName;
import com.example.backend.entity.TeamFile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeamMapper {
    //插入创建者信息
    @Insert("insert into team(teamName,email,rank) values(#{teamName},#{email},3)")
    Integer createTeam(String teamName, String email);

    //    是否存在队伍
    @Select("select * from team where teamName=#{teamName}")
    Team teamNameForTeam(String teamName);
    //找到等级
    @Select("select rank from team where teamName = #{teamName} and email = #{email}")
    Integer findRole(String teamName, String email);
    //    修改等级
    @Update("update team set rank=2 where teamName=#{teamName} and email=#{email}")
    Integer changeRole(String teamName, String email);
    //    移除成员
    @Update("delete from team where teamName=#{teamName} and email=#{email}")
    Integer detachMember(String teamName, String email);
    //    删除团队
    @Delete("DELETE from team WHERE team.teamname = #{teamName}")
    Integer deleteTeam(String teamName);
    //    插入邀请码
    @Insert("insert into invitecode(teamname,email,code) values(#{teamName},#{email},#{code})")
    Integer insertInviteCode(String teamName,String email,String code);
//    加入成员
    @Insert("insert into team(teamName,email,rank) values(#{teamName},#{email},1)")
    Integer insertTeam(String teamName, String email);
//    通过email找出所有成员信息
    @Select("select * from team where teamName=#{teamName}")
    List<Team> emailForALL(String teamName);
//    通过email找出所有团队名
    @Select("select teamName from team where email=#{email}")
    List<TeamName> emailForteamName(String email);
//取消管理员
    @Update("update team set rank=1 where teamName=#{teamName} and email=#{email}")
    Integer changeRoleCancelAdmin(String teamName, String email);
//创建团队文档
    @Insert("insert into teamfiles(teamname,filename,curcontent) values(#{teamName},#{fileName},#{curcontent})")
    Integer createTeamFile(String teamName,String fileName,String curcontent);
    //取出团队文档
    @Select("select * from teamfiles where teamName=#{teamName}")
    List<TeamFile> getTeamFile(String teamName);
    //编辑团队文档
    @Insert("update teamfiles set curcontent=#{curcontent} where teamName=#{teamName} and fileName=#{fileName}")
    Integer editTeamFile(String teamName,String fileName,String curcontent);
//    删除团队文档
    @Update("delete from teamfiles where teamName=#{teamName} and fileName=#{fileName}")
    Integer delTeamFile(String teamName, String fileName);
}

package com.example.backend.Service;

import com.example.backend.Mapper.DocumentMapper;
import com.example.backend.Mapper.TeamMapper;
import com.example.backend.Mapper.UserMapper;
import com.example.backend.entity.Document;
import com.example.backend.entity.GlobalVariable;
import com.example.backend.entity.TeamFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Service
public class TeamService {
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private DocumentMapper documentMapper;

    //    新建团队
    public Integer createTeam(String teamName, String email) {
        if (teamMapper.teamNameForTeam(teamName) != null) return -1;
        teamMapper.createTeam(teamName, email);
        return 0;
    }

    //    解散团队
    public Integer disbandTeam(String teamName, String email) {

        if (teamMapper.findRole(teamName, email) != 3) {
            return 1;
        } else {
            try {
                teamMapper.deleteTeam(teamName);
                return 0;
            } catch (Exception e) {
                System.out.println(e);
                return -1;
            }

        }
    }
//    授权管理员
    public Integer authorizeAdmin(String teamName,String fromEmail,String toEmail){
        if(teamMapper.findRole(teamName,fromEmail)!=3) return 1;
        if(teamMapper.findRole(teamName,toEmail)==2) return 2;
        teamMapper.changeRole(teamName,toEmail);
        return 0;
    }
    //    移除团队
    public Integer detachMember(String teamName, String fromEmail, String toEmail) {
        if(teamMapper.findRole(teamName,fromEmail)==1) return 1;
        if(teamMapper.findRole(teamName,toEmail)!=1) return 2;
        teamMapper.detachMember(teamName,toEmail);
        return 0;

    }
//    发送邀请
    public Integer sendInvitation(String teamName, String fromEmail, String toEmail) {
        GlobalVariable.inviteCode="http://47.109.43.251/invite/"+teamName;
        String s="欢迎加入"+teamName+"团队,您的验证码是"+GlobalVariable.inviteCode;
        teamMapper.insertInviteCode(teamName,toEmail,GlobalVariable.inviteCode);
        try {
            userService.sendEmail("super2021_41@163.com",toEmail,"加入我们",s);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
//      接受邀请
    public Integer acceptInvitation(String teamName, String email, Integer accept) {
        if(accept==1) return -1;
        teamMapper.insertTeam(teamName,email);
        return 0;
    }
//退出团队
    public Integer leaveTeam(String teamName, String email) {
        if(teamMapper.findRole(teamName,email)==3) return 1;
        teamMapper.detachMember(teamName,email);
        return 0;
    }
// 取消管理员
    public Integer cancelAdmin(String teamName, String fromEmail, String toEmail) {
        if(teamMapper.findRole(teamName,fromEmail)!=3) return 1;
        if(teamMapper.findRole(teamName,toEmail)==1) return 2;
        teamMapper.changeRoleCancelAdmin(teamName,toEmail);
        return 0;
    }
//返回团队文件
    public HashMap<String, Object> returnFile(String teamName) {
        HashMap<String,Object> hm=new HashMap<>();
        List<TeamFile> teamFiles=teamMapper.getTeamFile(teamName);
        hm.put("teamFiles",teamFiles);
        List<Document> documents=documentMapper.searchteamFiles(teamName);
        hm.put("pjctFiles",documents);
        hm.put("status",0);
        return hm;
    }
}

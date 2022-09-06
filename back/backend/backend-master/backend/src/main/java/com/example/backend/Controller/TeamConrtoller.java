package com.example.backend.Controller;

import com.example.backend.Mapper.TeamMapper;
import com.example.backend.Mapper.UserMapper;
import com.example.backend.Service.TeamService;
import com.example.backend.Service.UserService;
import com.example.backend.entity.ResForShowMember;
import com.example.backend.entity.Team;
import com.example.backend.entity.TeamName;
import com.example.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamConrtoller {
    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private TeamService teamService;
    @Autowired
    private UserMapper userMapper;

//    新建团队
    @GetMapping("/createTeam")
    public Integer createTeam(@RequestParam String teamName,
                              @RequestParam String email){
        return teamService.createTeam(teamName,email);
    }
//解散团队
    @GetMapping("/disbandTeam")
    public Integer disbandTeam(@RequestParam String teamName,
                              @RequestParam String email){
        return teamService.disbandTeam(teamName,email);
    }
//    授权管理员
    @GetMapping("/authorizeAdmin")
    public Integer authorizeAdmin(@RequestParam String teamName,
                                  @RequestParam String fromEmail,
                                  @RequestParam String toEmail){
        return  teamService.authorizeAdmin(teamName,fromEmail,toEmail);
    }
    //    取消管理员
    @GetMapping("/cancelAdmin")
    public Integer cancelAdmin(@RequestParam String teamName,
                                  @RequestParam String fromEmail,
                                  @RequestParam String toEmail){
        return  teamService.cancelAdmin(teamName,fromEmail,toEmail);
    }
    //    移除团队
    @GetMapping("/detachMember")
    public Integer detachMember(@RequestParam String teamName,
                              @RequestParam String fromEmail,
                              @RequestParam String toEmail){
        return  teamService.detachMember(teamName,fromEmail,toEmail);
    }
//    发送邀请
    @GetMapping("/sendInvitation")
    public Integer sendInvitation(@RequestParam String teamName,
                                @RequestParam String fromEmail,
                                @RequestParam String toEmail){
        return  teamService.sendInvitation(teamName,fromEmail,toEmail);
    }
//    接受邀请
    @GetMapping("/acceptInvitation")
    public Integer acceptInvitation(@RequestParam String teamName,
                                  @RequestParam String email,
                                  @RequestParam Integer accept){
        return  teamService.acceptInvitation(teamName,email,accept);
    }
//    退出团队
    @GetMapping("/leaveTeam")
    public Integer leaveTeam(@RequestParam String teamName,
                             @RequestParam String email){
        return  teamService.leaveTeam(teamName,email);
    }
//    展示所有团队成员信息
    @GetMapping("/showMember")
    public HashMap<String,Object> showMember(@RequestParam String teamName){
        HashMap<String,Object>hm=new HashMap<>();
        List<Team> teamMember= teamMapper.emailForALL(teamName);
        List<ResForShowMember> members=new ArrayList<>();
        for(int i=0;i<teamMember.size();i++){
            User user=userMapper.isEmailOccupy(teamMember.get(i).getEmail());
            ResForShowMember resForShowMember=new ResForShowMember();
            resForShowMember.setEmail(teamMember.get(i).getEmail());
            resForShowMember.setRole(teamMember.get(i).getRank());
            resForShowMember.setName(user.getName());
            resForShowMember.setNickname(user.getNickname());
            members.add(resForShowMember);
        }
        Integer status=0;
        hm.put("status",status);
        hm.put("members",members);
        hm.put("memNum",members.size());
        return hm;
    }
//    返回用户所有团队
    @GetMapping("/showTeam")
    public HashMap<String,Object> showTeam(@RequestParam String email){
        HashMap<String,Object>hm=new HashMap<>();
        List<TeamName> teams=teamMapper.emailForteamName(email);
        hm.put("Teams",teams);
        hm.put("len",teams.size());
        hm.put("status",0);
        return hm;
    }
    //    创建团队文档
    @GetMapping("/createTeamFile")
    public Integer createTeamFile(@RequestParam String teamName,
                                  @RequestParam String fileName){
        teamMapper.createTeamFile(teamName,fileName,"");
        return 0;
    }
    //    返回团队文件
    @GetMapping("/returnFile")
    public HashMap<String,Object> returnFile(@RequestParam String teamName){
        return teamService.returnFile(teamName);
    }

    //    编辑团队文档
    @GetMapping("/editTeamFile")
    public Integer editTeamFile(@RequestParam String teamName,
                                  @RequestParam String fileName,
                                @RequestParam String content){
        teamMapper.editTeamFile(teamName,fileName,content);
        return 0;
    }
    //    删除团队文档
    @GetMapping("/delTeamFile")
    public Integer delTeamFile(@RequestParam String teamName,
                                  @RequestParam String fileName){
        teamMapper.delTeamFile(teamName,fileName);
        return 0;
    }
}

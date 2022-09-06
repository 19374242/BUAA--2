package com.example.backend.Controller;

import com.example.backend.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    //    新建项目
    @GetMapping("/createProject")
    public Integer createProject(@RequestParam String teamName, @RequestParam String pjctName) {
        return projectService.createTeam(teamName, pjctName);
    }

    //    团队项目列表
    @GetMapping("/getProjects")
    public HashMap<String, Object> getProjects(@RequestParam String teamName) {
        return projectService.getProjects(teamName);
    }

    //    删除项目
    @GetMapping("/delProject")
    public Integer delProject(@RequestParam String teamName, @RequestParam String pjctName) {
        return projectService.delProject(teamName, pjctName);
    }

    //    查看删除项目
    @GetMapping("/getDelProjects")
    public HashMap<String, Object> getDelProjects(@RequestParam String teamName) {
        return projectService.getDelProjects(teamName);
    }

    //    恢复已删除项目
    @GetMapping("recoverProject")
    public Integer recoverProject(@RequestParam String teamName, @RequestParam String pjctName) {
        return projectService.recoverProject(teamName, pjctName);
    }

    //    彻底删除项目
    @GetMapping("/removeProject")
    public Integer removeProject(@RequestParam String teamName, @RequestParam String pjctName) {
        return projectService.removeProject(teamName, pjctName);
    }

    //    重命名项目
    @GetMapping("/renameProject")
    public Integer renameProject(@RequestParam String teamName, @RequestParam String pjctName,
                                 @RequestParam String newName) {
        return projectService.renameProject(teamName, pjctName, newName);
    }
    //    个人项目列表
    @GetMapping("/oneForProjects")
    public HashMap<String, Object> oneForProjects(@RequestParam String email) {
        return projectService.oneForProjects(email);
    }
//    个人项目排序
    @GetMapping("/allProjectSort")
    public HashMap<String, Object> allProjectSort(@RequestParam Integer method,
                                                  @RequestParam String email) {
        return projectService.allProjectSort(method,email);
    }
    //    队伍项目排序
    @GetMapping("/teamProjectSort")
    public HashMap<String, Object> teamProjectSort(@RequestParam Integer method,
                                                  @RequestParam String teamName) {
        return projectService.teamProjectSort(method,teamName);
    }
//    项目副本
    @GetMapping("/projectCopy")
    public Integer projectCopy(@RequestParam String teamName,
                                               @RequestParam String pjctName,
                                               @RequestParam String newName) {
        return projectService.projectCopy(teamName,pjctName,newName);
    }

    //    搜索个人项目列表
    @GetMapping("/searchOnesProjects")
    public HashMap<String, Object> searchOnesProjects(@RequestParam String word, @RequestParam String email) {
        return projectService.searchOnesProjects(word, email);
    }

    //    搜索团队项目列表
    @GetMapping("/searchTeamProjects")
    public HashMap<String, Object> searchTeamProjects(@RequestParam String word, @RequestParam String teamName) {
        return projectService.searchTeamProjects(word, teamName);
    }


}
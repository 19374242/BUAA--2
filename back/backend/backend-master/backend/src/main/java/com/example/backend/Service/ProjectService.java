package com.example.backend.Service;

import com.example.backend.Mapper.DocumentMapper;
import com.example.backend.Mapper.ProjectMapper;
import com.example.backend.Mapper.TeamMapper;
import com.example.backend.entity.Document;
import com.example.backend.entity.Project;
import com.example.backend.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.lang.model.type.ErrorType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class ProjectService {
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private DocumentMapper documentMapper;

    //    新建项目
    public Integer createTeam(String teamName, String pjctName) {
        if (projectMapper.projectNameAndPjctNameNameForProject(teamName, pjctName) != null) return 1;
        projectMapper.createProject(teamName, pjctName);
        return 0;
    }

    //    团队项目列表
    public HashMap<String, Object> getProjects(String teamName) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            map.put("projects", projectMapper.getProjects(teamName));
            map.put("status", 0);
        } catch (Exception e) {
            System.out.println(e);
            map.put("status", -1);
        }
        return map;
    }

    //    删除项目
    public Integer delProject(String teamName, String pjctName) {
        if (projectMapper.projectNameAndPjctNameNameForProject(teamName, pjctName) == null) return -1;
        try {
            projectMapper.delPreoject(teamName, pjctName);
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    //    查看删除项目
    public HashMap<String, Object> getDelProjects(String teamName) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            map.put("projects", projectMapper.getDelProjects(teamName));
            map.put("status", 0);
        } catch (Exception e) {
            System.out.println(e);
            map.put("status", -1);
        }
        return map;
    }

    //    恢复已删除项目
    public Integer recoverProject(String teamName, String pjctName) {
        if (projectMapper.projectNameAndPjctNameNameForProject(teamName, pjctName) == null) return -1;
        try {
            projectMapper.recoverProject(teamName, pjctName);
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    //    彻底删除项目
    public Integer removeProject(String teamName, String pjctName) {
        if (projectMapper.projectNameAndPjctNameNameForProject(teamName, pjctName) == null) return -1;
        try {
            projectMapper.removeProject(teamName, pjctName);
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    //    重命名项目
    public Integer renameProject(String teamName, String pjctName, String newName) {
        if (projectMapper.projectNameAndPjctNameNameForProject(teamName, pjctName) == null) return -1;
        if (projectMapper.projectNameAndPjctNameNameForProject(teamName, newName) != null) return 1;
        try {
            projectMapper.renamePreoject(teamName, pjctName, newName);
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    public HashMap<String, Object> oneForProjects(String email) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("project",projectMapper.oneForProjects(email));
        map.put("status",0);
        return map;
    }
//个人项目排序
    public HashMap<String, Object> allProjectSort(Integer method,String email) {
        HashMap<String,Object> hm=new HashMap<>();
        List<Project> res=projectMapper.oneForProjects(email);
        Integer status=0;
        hm.put("status",status);
        if(method==0){
            hm.put("projects",res);
        }
        else if(method==1){
            Collections.reverse(res);
            hm.put("projects",res);
        }
        else if(method==2){
            Collections.sort(res);
            hm.put("projects",res);
        }
        else if(method==3){
            Collections.sort(res);
            Collections.reverse(res);
            hm.put("projects",res);
        }
        return hm;
    }
//队伍项目排序
    public HashMap<String, Object> teamProjectSort(Integer method, String teamName) {
        HashMap<String,Object> hm=new HashMap<>();
        List<Project> res=projectMapper.getProjects(teamName);
        Integer status=0;
        hm.put("status",status);
        if(method==0){
            hm.put("projects",res);
        }
        else if(method==1){
            Collections.reverse(res);
            hm.put("projects",res);
        }
        else if(method==2){
            Collections.sort(res);
            hm.put("projects",res);
        }
        else if(method==3){
            Collections.sort(res);
            Collections.reverse(res);
            hm.put("projects",res);
        }
        return hm;
    }
//创建副本
    public Integer projectCopy(String teamName, String pjctName, String newName) {
        Integer status=0;
        projectMapper.createProject(teamName,newName);
        List<Document> documents=documentMapper.search(pjctName,teamName);
        for(int i=0;i<documents.size();i++){
            documentMapper.insertDocument(documents.get(i).getFileName(),
                                        documents.get(i).getCurcontent(),
                                        newName,
                                        teamName);
        }
        return status;

    }

    //搜索个人项目
    public HashMap<String, Object> searchOnesProjects(String word, String email) {
        HashMap<String,Object> map=new HashMap<>();
        StringBuilder pattern = new StringBuilder("%");
        for (char c : word.toCharArray()) {
            pattern.append(c).append("%");
        }
        String newWord=String.valueOf(pattern);
        List<Project> result=projectMapper.searchOnesProjects(email,newWord);
        map.put("status", 0);
        map.put("project", result);
        return map;
    }

    //搜索个人项目
    public HashMap<String, Object> searchTeamProjects(String word, String teamName) {
        HashMap<String,Object> map=new HashMap<>();
        StringBuilder pattern = new StringBuilder("%");
        for (char c : word.toCharArray()) {
            pattern.append(c).append("%");
        }
        String newWord=String.valueOf(pattern);
        List<Project> result=projectMapper.searchTeamProjects(teamName,newWord);
        map.put("status", 0);
        map.put("project", result);
        return map;
    }

}



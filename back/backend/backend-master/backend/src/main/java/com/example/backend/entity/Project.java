package com.example.backend.entity;

import lombok.Data;

@Data
public class Project implements Comparable<Project>{
    private String teamName;
    private String pjctName;
    private Integer delFlag;

    @Override
    public int compareTo(Project o) {
        return this.pjctName.compareTo(o.pjctName);
    }
}

package com.example.backend.Controller;

import com.example.backend.Mapper.DocumentMapper;
import com.example.backend.entity.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/document")
public class DocumentController {
    @Autowired
    private DocumentMapper documentMapper;

    //    传入
    @GetMapping("/upload")
    public Integer upload(@RequestParam String fileName, @RequestParam String curcontent,
                          @RequestParam String pjctName,@RequestParam String teamName) {
        if(documentMapper.isCovered(fileName,pjctName,teamName)==null){
            documentMapper.insertDocument(fileName,curcontent,pjctName,teamName);
        }
        else{
            documentMapper.cover(fileName,curcontent,pjctName,teamName);
        }
        return 0;
    }
//    传出
    @GetMapping("/download")
    public Document download(@RequestParam String fileName,@RequestParam String pjctName,@RequestParam String teamName) {
        return documentMapper.isCovered(fileName,pjctName,teamName);
    }
//    删除
    @GetMapping("/del")
    public Integer del(@RequestParam String fileName,@RequestParam String pjctName,@RequestParam String teamName) {
        documentMapper.del(fileName,pjctName,teamName);
        return 0;
    }
    //    显示所有
    @GetMapping("/showAll")
    public List<Document> showAll(@RequestParam String pjctName,@RequestParam String teamName) {
        List<Document> documents=documentMapper.search(pjctName,teamName);
        return documents;
    }
}

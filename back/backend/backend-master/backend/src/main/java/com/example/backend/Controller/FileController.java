package com.example.backend.Controller;

import com.example.backend.Mapper.FileMapper;
import com.example.backend.entity.MyFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/file")  //给接口加前缀，要在/file处访问才行
public class FileController {
    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Autowired
    private FileMapper fileMapper;

    //上传图片
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) {
//        获取名字不是getName啊啊啊啊
        String fileName = file.getOriginalFilename();
        File uploadFile = new File(fileUploadPath + '/' + fileName);
        if (fileMapper.nameForFlag(fileName) == null) {
            fileMapper.insertFile(fileName);
        }
//        将文件转存到相应位置
        try {
            file.transferTo(uploadFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileName;
    }

    //    删除
    @GetMapping("/del")
    public Integer del(@RequestParam String fileName) {
        fileMapper.removeFile(fileName);
//        删除
        FileSystemUtils.deleteRecursively(new File(fileUploadPath + "/" + fileName));
        return 1;
    }

    //    预览某一图片,注意传参数用的是get
    @GetMapping("/preview")
    public HashMap<String, Object> preview(@RequestParam String name) {
        String url = fileUploadPath + "/" + name;
        HashMap<String, Object> res = new HashMap<>();
        res.put("url", url);
        //        值得注意的是，这里不能直接返回String,因为前端必须要是JSON类型，即“键”：“值”，因此，用哈希表刚好符合这种输出格式,前端用res.url即可获得数据
        return res;
    }

    @GetMapping("/showFiles")
    public  List<MyFiles> showFiles() {
        List<String> files=fileMapper.searchAll();
        List<MyFiles> res = new ArrayList<>();
        for(int i=0;i<files.size();i++){
            MyFiles myFiles=new MyFiles();
            myFiles.setUrl(fileUploadPath + "/" + files.get(i));
            myFiles.setName(files.get(i));
            res.add(myFiles);
        }
        return res;


    }
}
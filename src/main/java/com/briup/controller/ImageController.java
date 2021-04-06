package com.briup.controller;

import com.briup.common.respose.SimpleRespose;
import com.briup.common.utils.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
@Controller
@RequestMapping("image/")
public class ImageController {


    @PostMapping("upload")
    @ResponseBody
    public Object imageUpload(@RequestBody MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String fileName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
        fileName = fileName.concat(String.valueOf(System.currentTimeMillis())).concat(fileType);
        FileUtils.readAndWrite(file.getInputStream(), new FileOutputStream(new File(FileUtils.getFilePath().concat("\\" + fileName))));
        return new SimpleRespose(fileName, "上传成功", "0");
    }

    @GetMapping("get")
    @ResponseBody
    public Object getImage(@RequestParam String file, HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        File file1 = new File(FileUtils.getFilePath().concat("\\" + file));
        if (file1.exists()) {
            FileUtils.readAndWrite(new FileInputStream(file1), outputStream);
            return null;
        }
        return new SimpleRespose(null, "文件不存在", "1");
    }

    @GetMapping("delete")
    @ResponseBody
    public Object delete(@RequestParam String file){
        File file1 = new File(FileUtils.getFilePath().concat("\\" + file));
        if (FileUtils.deleteFile(file1)){
            return new SimpleRespose("","删除成功","0");
        }
        return null;
    }
}

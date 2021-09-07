package com.briup.controller;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.briup.common.respose.SimpleRespose;
import com.briup.common.utils.AWSS3Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("image/")
public class ImageController {
    private final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Resource
    public AWSS3Util awss3Util;


    @PostMapping("upload")
    @ResponseBody
    public Object imageUpload(@RequestBody MultipartFile file) throws IOException {
        logger.info("image/upload test -");
        String originalFilename = file.getOriginalFilename();
        String fileName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
        fileName = fileName.concat(String.valueOf(System.currentTimeMillis())).concat(fileType);
        final File excelFile = File.createTempFile(fileName, fileType);
        logger.info("image/upload test --");
        // MultipartFile to File
        file.transferTo(excelFile);

        awss3Util.put(fileName, excelFile);

        //程序结束时，删除临时文件
        deleteFile(excelFile);
//        FileUtils.readAndWrite(file.getInputStream(), new FileOutputStream(new File(FileUtils.getFilePath().concat("\\" + fileName))));


        return new SimpleRespose(fileName, "上传成功", "0");
    }

    /**
     * 删除
     *
     * @param files
     */
    private void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @GetMapping("get")
    @ResponseBody
    public SimpleRespose getImage(@RequestParam String fileName, HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        S3ObjectInputStream inputStream = awss3Util.get(fileName);
        if (inputStream != null) {
            awss3Util.readAndWrite(inputStream, outputStream);
            return null;
        }
        return new SimpleRespose(null, "文件不存在", "1");
    }

    @GetMapping("delete")
    @ResponseBody
    public Object delete(@RequestParam String fileName){
        String result = awss3Util.delete(fileName);
        if ("删除成功".equals(result)){
            return new SimpleRespose("","删除成功","0");
        }
        return new SimpleRespose(result,"删除失败","0");
    }
}

package com.briup.controller;


import com.briup.common.respose.SimpleRespose;
import com.briup.common.utils.TencentCosUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
@RequestMapping("image/")
public class ImageController {
    private final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Resource
    public TencentCosUtil tencentCosUtil;


    @PostMapping("upload")
    @ResponseBody
    public Object imageUpload(@RequestBody MultipartFile file) throws IOException {
        logger.info("image/upload start -");
        String originalFilename = file.getOriginalFilename();
        String fileName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
        fileName = fileName.concat(String.valueOf(System.currentTimeMillis())).concat(fileType);
        final File excelFile = File.createTempFile(fileName, fileType);
        // MultipartFile to File
        file.transferTo(excelFile);

        tencentCosUtil.put(fileName, excelFile);
        logger.info("image/upload end --");

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
        FileInputStream inputStream = tencentCosUtil.get(fileName);
        if (inputStream != null) {
            tencentCosUtil.readAndWrite(inputStream, outputStream);
            return null;
        }
        return new SimpleRespose(null, "文件不存在", "1");
    }

    @GetMapping("getPath")
    @ResponseBody
    public SimpleRespose getImagePath(@RequestParam String fileName) throws IOException {
        return null;
    }

    @GetMapping("delete")
    @ResponseBody
    public Object delete(@RequestParam String fileName){
        String result = tencentCosUtil.delete(fileName);
        if ("删除成功".equals(result)){
            return new SimpleRespose("","删除成功","0");
        }
        return new SimpleRespose(result,"删除失败","1");
    }
}

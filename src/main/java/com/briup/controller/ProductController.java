package com.briup.controller;

import com.briup.bean.product.Product;
import com.briup.common.respose.SimpleRespose;
import com.briup.common.utils.FileUtils;
import com.briup.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("product/")
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @PostMapping("create")
    @ResponseBody
    public Object create(@RequestBody Product product){
        Optional<Product> one = productDao.findOne(Example.of(product));
        if (one.isPresent()){
            return new SimpleRespose(null,"创建失败，该产品已存在","1");
        }
        return new SimpleRespose(productDao.save(product),"创建成功","0");
    }


    @GetMapping("search")
    @ResponseBody
    public Object search(@RequestParam(name = "keywords" ,required = false) String keywords){
        if ("null".equals(keywords)){
            return  new SimpleRespose(productDao.findAll(),null,"0");
        }
        return  new SimpleRespose(productDao.searchProduct(keywords),null,"0");
    }

    @RequestMapping("getProductById")
    @ResponseBody
    public Object getProductById(@RequestParam(name = "id") String id){
        return new SimpleRespose(productDao.findById(id).isPresent() ? productDao.findById(id).get() : null,null, "0");
    }

    @PostMapping("imageUpload")
    @ResponseBody
    public Object imageUpload(@RequestBody MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String fileName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
        fileName = fileName.concat(String.valueOf(System.currentTimeMillis())).concat(fileType);
        FileUtils.readAndWrite(file.getInputStream(),new FileOutputStream(new File(FileUtils.getFilePath().concat("\\"+fileName))));
        return  new SimpleRespose(fileName,"上传成功","0");
    }

    @GetMapping("getImage")
    @ResponseBody
    public Object getImage(@RequestParam String file, HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        File file1 = new File(FileUtils.getFilePath().concat("\\"+file));
        if (file1.exists()){
            FileUtils.readAndWrite(new FileInputStream(file1),outputStream);
            return null;
        }
        return new SimpleRespose(null,"文件不存在","1");
    }
}

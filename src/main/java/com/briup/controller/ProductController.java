package com.briup.controller;

import com.briup.bean.product.Product;
import com.briup.common.respose.SimpleRespose;
import com.briup.dao.ProductDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

}

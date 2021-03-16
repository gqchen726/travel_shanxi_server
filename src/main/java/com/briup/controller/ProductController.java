package com.briup.controller;

import com.briup.bean.product.Product;
import com.briup.common.respose.SimpleRespose;
import com.briup.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequestMapping("product/")
public class ProductContorller {

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
}

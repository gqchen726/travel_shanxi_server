package com.briup.controller;

import com.briup.bean.collection.CollectionDetail;
import com.briup.bean.collection.ex.CollectionRequest;
import com.briup.bean.product.Product;
import com.briup.bean.user.User;
import com.briup.common.respose.SimpleRespose;
import com.briup.dao.CollectionDetailDao;
import com.briup.dao.ProductDao;
import com.briup.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("collection/")
@Controller
public class CollectionController {

    @Autowired
    private CollectionDetailDao collectionDetailDao;
    @PostMapping("create")
    @ResponseBody
    public Object create(@RequestBody CollectionRequest collectionRequest){
        Optional<CollectionDetail> one = collectionDetailDao.findOne(Example.of(new CollectionDetail(null, collectionRequest.getProductCode(), collectionRequest.getMobileNumber())));
        if (one.isPresent()){
            return new SimpleRespose(null,"该产品已经收藏","1");
        }
        CollectionDetail save = collectionDetailDao.save(new CollectionDetail(String.valueOf(System.currentTimeMillis()), collectionRequest.getProductCode(), collectionRequest.getMobileNumber()));
        return new SimpleRespose(null,"收藏成功","0");
    }

    @GetMapping("list")
    @ResponseBody
    public Object list(@RequestParam String mobileNumber){
        CollectionDetail collectionDetail = new CollectionDetail();
        collectionDetail.setMobileNumber(mobileNumber);
        List<CollectionDetail> all = collectionDetailDao.findAll(Example.of(collectionDetail));
        return new SimpleRespose(all,"","0");
    }
}


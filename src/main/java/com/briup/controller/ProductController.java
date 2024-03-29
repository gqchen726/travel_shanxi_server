package com.briup.controller;

import com.briup.bean.product.Product;
import com.briup.common.respose.SimpleRespose;
import com.briup.common.utils.TencentCosUtil;
import com.briup.dao.ProductDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("product/")
public class ProductController {

    @Autowired
    private ProductDao productDao;
    @Resource
    public TencentCosUtil tencentCosUtil;

    @PostMapping("create")
    @ResponseBody
    public Object create(@RequestBody Product product) {
        Optional<Product> one = productDao.findById(product.getProductCode());
        if (one.isPresent()) {
            return new SimpleRespose(null, "创建失败，该产品已存在", "1");
        }
        return new SimpleRespose(productDao.save(product), "创建成功", "0");
    }


    @GetMapping("search")
    @ResponseBody
    public Object search(@RequestParam(name = "keywords", required = false) String keywords) {
        if ("null".equals(keywords) || "ALL".equals(keywords)) {
            return new SimpleRespose(productDao.findAll(), null, "0");
        }
        return new SimpleRespose(productDao.searchProduct(keywords), null, "0");
    }

    @RequestMapping("getProductById")
    @ResponseBody
    public Object getProductById(@RequestParam(name = "id") String id) {
        return new SimpleRespose(productDao.findById(id).isPresent() ? productDao.findById(id).get() : null, null, "0");
    }
    @GetMapping("searchSimpleProduct")
    @ResponseBody
    public Object searchSimpleProduct(@RequestParam(name = "keywords", required = false) String keywords) {
        if ("null".equals(keywords)) {
            return new SimpleRespose(null, null, "0");
        }
        List<Product> products = productDao.searchProduct(keywords);
        if (products.size()>  0){
            List<Map> collect = products.stream().map(Product::getSimpleProduct).collect(Collectors.toList());
            return new SimpleRespose(collect,null,"0");
        }
        return new SimpleRespose(null, null, "0");
    }

    @GetMapping("getAllCategory")
    @ResponseBody
    public Object getAllCategory(){
        List<String> allCategory = productDao.getAllCategory();
        HashSet<Object> categorys = new HashSet<>();
        for(String category: allCategory) {
            String[] split = category.split("#");
            for (String s : split) {
                if (StringUtils.isNoneEmpty(s)) {
                    categorys.add(s);
                }
            }
        }
        return new SimpleRespose(categorys,null,"0");
    }

    @GetMapping("delete")
    @ResponseBody
    public Object delete(@RequestParam String productCode){
        Optional<Product> productDaoById = productDao.findById(productCode);
        if (productDaoById.isPresent()){
            String resources = productDaoById.get().getResources();
            if (resources != null) {
                String[] split = resources.split(";");
                for ( String s : split){
                    tencentCosUtil.delete(s);
                }
            }
            productDao.deleteById(productCode);

            return new SimpleRespose(null,"删除成功","0");
        }
        return new SimpleRespose(null,"产品补存在","1");
    }

    @PostMapping("update")
    @ResponseBody
    public Object update(@RequestBody Product product){
        Optional<Product> productDaoById = productDao.findById(product.getProductCode());
        if (productDaoById.isPresent()){
            Product save = productDao.save(product);
            return new SimpleRespose(save,"success","0");
        }
        return new SimpleRespose(null,"更新失败，产品不存在","1");
    }

    @GetMapping("checkProduct")
    @ResponseBody
    public  Object checkProduct(@RequestParam String productCode){
        Optional<Product> productDaoById = productDao.findById(productCode);
        return new SimpleRespose(productDaoById.isPresent() ,"","0");
    }

}

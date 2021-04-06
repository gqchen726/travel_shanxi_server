package com.briup.bean.product;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "t_product")
public class Product {

    @Column(name = "productName", unique = true, nullable = false)
    private String productName;
    @Id
    @Column(name = "product_code")
    private String productCode;
    @Column(name = "description")
    private String description;
    @Column(name = "price", nullable = false)
    private String price;
    @Column(name = "ex")
    private String ex;
    @Column(name = "category")
    private String category;
    @Column(name = "resources")
    private String resources;

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", productCode='" + productCode + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", ex='" + ex + '\'' +
                '}';
    }
    @JsonIgnore
    public Map getSimpleProduct(){
        HashMap<String, String> map = new HashMap<>();
        map.put("value",this.getProductName());
        return map;
    }
}

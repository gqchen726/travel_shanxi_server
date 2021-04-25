package com.briup.bean.order.ex;

import com.briup.bean.order.Order;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class  OrderCreate {

    private Integer productNum;

    private String mobileNumber;

    private String productCode;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date date;

    public Date getDate() {
        return date;
    }
    @JsonFormat(pattern = "yyyy/MM/dd")
    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }


    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public OrderCreate() {
    }
}

package com.briup.bean.order.ex;

import com.briup.bean.order.Order;

public class OrderCreate {

    private Order order;

    private String mobileNumber;

    private String productCode;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

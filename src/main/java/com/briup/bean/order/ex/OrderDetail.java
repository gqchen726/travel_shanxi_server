package com.briup.bean.order.ex;

import com.briup.bean.order.Order;

import java.io.Serializable;
import java.util.Date;

public class OrderDetail {

    private String OrderId;

    private String status;

    private String totalPrice;

    private Date generationDate;

    private Integer productNum;

    private Date startDate;

    private Date enddate;

    public OrderDetail(Order order) {
        OrderId = order.getOrderId();
        this.status = order.getStatus();
        this.totalPrice = order.getTotalPrice();
        this.generationDate = order.getGenerationDate();
        this.productNum = order.getProductNum();
        this.startDate = order.getStartDate();
        this.enddate = order.getEnddate();
    }

    public OrderDetail() {
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getGenerationDate() {
        return generationDate;
    }

    public void setGenerationDate(Date generationDate) {
        this.generationDate = generationDate;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }
}

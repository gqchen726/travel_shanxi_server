package com.briup.bean.order;

import com.briup.bean.product.Product;
import com.briup.bean.user.User;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "t_order")
public class Order {
    @Id
    @Column(name="order_id")
    private String OrderId;
    @Column(name = "status")
    private String status;
    @OneToOne(targetEntity = Product.class)
    @JoinColumn(name = "product_code" ,referencedColumnName = "product_code")
    private Product productCode;
    @Column(name = "totalPrice")
    private String totalPrice;
    @Column(name = "generationDate")
    private Date generationDate;
    @Column(name = "product_Num")
    private Integer productNum;
    //
    @Column(name ="startDate")
    private Date startDate;
    @Column(name = "enddate")
    private Date enddate;

    @ManyToOne(cascade = CascadeType.ALL,targetEntity = User.class)
    private User user;

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

    public Product getProductCode() {
        return productCode;
    }

    public void setProductCode(Product productCode) {
        this.productCode = productCode;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    @Override
    public String toString() {
        return "Order{" +
                "OrderId='" + OrderId + '\'' +
                ", status='" + status + '\'' +
                ", productCode=" + productCode +
                ", totalPrice='" + totalPrice + '\'' +
                ", generationDate=" + generationDate +
                ", productNum=" + productNum +
                ", startDate=" + startDate +
                ", enddate=" + enddate +
                ", user=" + user +
                '}';
    }
}

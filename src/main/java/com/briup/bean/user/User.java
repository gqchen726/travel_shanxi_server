package com.briup.bean.user;

import com.briup.bean.collection.CollectionDetail;
import com.briup.bean.order.Order;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_users")
public class User {

    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Id//主键
    @Column(name = "mobileNumber")
    private String mobileNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "address")
    private String address;
    @Column(name = "sex")
    private String sex;
    @Column(name = "age")
    private String age;
    @Column(name = "birth")
    private Date birth;
    @Column(name = "registerCode")
    private String registerCode;
    @Column(name = "approval")
    private String approval;
    @Column(name = "admin")
    private String admin;
    @OneToMany(targetEntity = Order.class)
    private List<Order> orders = new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

}

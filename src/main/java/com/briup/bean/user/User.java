package com.briup.bean.user;

import com.briup.bean.order.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @OneToMany(targetEntity = Order.class, cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    /*    @OneToMany(mappedBy = "t_users",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
        private List<Collection> collectionList;*/
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
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

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", birth=" + birth +
                ", registerCode='" + registerCode + '\'' +
                ", approval='" + approval + '\'' +
                ", admin='" + admin + '\'' +
                ", orders=" + orders +
                '}';
    }
}

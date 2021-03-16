package com.briup.controller;

import com.briup.bean.order.Order;
import com.briup.bean.order.ex.OrderCreate;
import com.briup.bean.product.Product;
import com.briup.bean.user.User;
import com.briup.common.respose.SimpleRespose;
import com.briup.dao.OrderDao;
import com.briup.dao.ProductDao;
import com.briup.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("order/")
public class OrderController {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ProductDao productDao;

    /**
     * 创建订单
     * 1，根据登陆信息去取用户信息
     * 2。根据前端传递的productCode取获取产品
     * @param orderCreate
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public Object create(@RequestBody OrderCreate orderCreate){
        Order order = generatedOrder(orderCreate);
        Order save = orderDao.save(order);
        return new SimpleRespose(save,"下单成功","0");
    }

    /**
     *
     * @param orderCreate
     * @return
     */
    private Order generatedOrder(OrderCreate orderCreate){
        Optional<User> userById = userDao.findById(orderCreate.getMobileNumber());
        Optional<Product> productDaoById = productDao.findById(orderCreate.getProductCode());
        User user = userById.get();
        Product product = productDaoById.get();
        Order order = orderCreate.getOrder();
        order.setUser(user);
        order.setProductCode(product);
        order.setOrderId(String.valueOf(System.currentTimeMillis()));
        return order;
    }
}

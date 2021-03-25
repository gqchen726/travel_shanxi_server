package com.briup.controller;

import com.briup.bean.order.Order;
import com.briup.bean.order.ex.OrderCreate;
import com.briup.bean.order.ex.OrderDetail;
import com.briup.bean.order.ex.OrderRespose;
import com.briup.bean.order.ex.OrderStatus;
import com.briup.bean.product.Product;
import com.briup.bean.user.User;
import com.briup.common.respose.SimpleRespose;
import com.briup.dao.OrderDao;
import com.briup.dao.ProductDao;
import com.briup.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    @PostMapping("create")
    @ResponseBody
    public Object create(@RequestBody OrderCreate orderCreate){
        Order order = generatedOrder(orderCreate);
        Order save = orderDao.save(order);
        return new SimpleRespose(new OrderRespose(order),"下单成功","0");
    }

    @GetMapping("getOrderById")
    @ResponseBody 
    public Object getOrderById(@RequestParam("oderId") String orderId ){
        Optional<Order> byId = orderDao.findById(orderId);
        return new SimpleRespose(byId.isPresent()? new OrderRespose(byId.get()):null,null,"0");
    }
    @GetMapping("listAllOrder")
    @ResponseBody
    public Object listAll(@RequestParam(name = "mobileNumber") String mobileNumber){
        List<Order> orders = orderDao.listAllOrder(mobileNumber);
        List<OrderRespose> orderDetails = new ArrayList<>();
        for (Order order : orders){
            OrderRespose orderDetail = new OrderRespose(order);
            orderDetails.add(orderDetail);
        }
        return new SimpleRespose(orderDetails,null,"0");
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
        Order order = new Order();
        order.setProduct(product);
        order.setUser(user);
        //设置订单状态
        order.setStatus(OrderStatus.GENERATED.getName());
        //生成订单号
        order.setOrderId(String.valueOf(System.currentTimeMillis()));
        //计算订单价钱
        Integer productNum = orderCreate.getProductNum();
        order.setTotalPrice(String.valueOf(productNum*(Long.parseLong(product.getPrice()))));
        //订单生成时间
        order.setGenerationDate(new Date());
        return order;
    }
}

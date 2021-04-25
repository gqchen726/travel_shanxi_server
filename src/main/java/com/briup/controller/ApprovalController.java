package com.briup.controller;

import com.briup.bean.order.Order;
import com.briup.bean.order.ex.OrderRespose;
import com.briup.bean.order.ex.OrderStatus;
import com.briup.bean.user.User;
import com.briup.common.respose.SimpleRespose;
import com.briup.common.utils.EmailUtils;
import com.briup.common.utils.RedisUtil;
import com.briup.dao.OrderDao;
import com.briup.dao.ProductDao;
import com.briup.dao.StockDao;
import com.briup.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("approval")
public class ApprovalController {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private StockDao stockDao;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private EmailUtils emailUtils;


    @GetMapping("listAll")
    @ResponseBody
    public Object listAll(){
        List<Order> orders = orderDao.listAllApprovalOrder(OrderStatus.SUBMIT.getName());
        List<OrderRespose> orderDetails = new ArrayList<>();
        for (Order order : orders) {
            OrderRespose orderDetail = new OrderRespose(order);
            orderDetails.add(orderDetail);
        }
        return new SimpleRespose(orderDetails, null, "0");
    }
    @GetMapping("update")
    @ResponseBody
    public Object update(@RequestParam String orderId, @RequestParam String status , @RequestParam String mobileNumber) throws Exception {
        Optional<Order> byId = orderDao.findById(orderId);
        Order order = null;
        if (byId.isPresent()){
             order = byId.get();
        }else {
            return new SimpleRespose(null,null,"1");
        }
        if (status.equals(OrderStatus.REJECT.getName())){
            order.setStatus(OrderStatus.REJECT.getName());
        }
        if (status.equals(OrderStatus.APPROVAL.getName())){
            order.setStatus(OrderStatus.APPROVAL.getName());
        }
        //邮件通知
        sendResult(order,mobileNumber);
        orderDao.save(order);
        return new SimpleRespose(new OrderRespose(order), null, "0");
    }

    private void sendResult(Order order ,String mobileNumber) throws Exception {

        String contect = "您的报价单{orderId} ,已经被{approval} 做了审核, 审核结果为 {result}";
        Optional<User> byId = userDao.findById(mobileNumber);

        String replace = contect.replace("{orderId}", order.getOrderId()).replace("{approval}", byId.get().getName()).replace("{result}", order.getStatus());

        String email = order.getUser().getEmail();

        String subject = "保单审核结果";

        emailUtils.sendemail(email,subject,replace);

    }
}

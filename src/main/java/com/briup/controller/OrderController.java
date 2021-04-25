package com.briup.controller;

import com.briup.bean.Stock;
import com.briup.bean.order.Order;
import com.briup.bean.order.ex.OrderCreate;
import com.briup.bean.order.ex.OrderDetail;
import com.briup.bean.order.ex.OrderRespose;
import com.briup.bean.order.ex.OrderStatus;
import com.briup.bean.product.Product;
import com.briup.bean.user.User;
import com.briup.common.respose.SimpleRespose;
import com.briup.common.utils.RedisUtil;
import com.briup.dao.OrderDao;
import com.briup.dao.ProductDao;
import com.briup.dao.StockDao;
import com.briup.dao.UserDao;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

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
    @Autowired
    private StockDao stockDao;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 创建订单
     * 1，根据登陆信息去取用户信息
     * 2。根据前端传递的productCode取获取产品
     *
     * @param orderCreate
     * @return
     */
    @PostMapping("create")
    @ResponseBody
    public Object create(@RequestBody OrderCreate orderCreate) {
        Order order = generatedOrder(orderCreate);
        if(order == null) {
            return  new SimpleRespose(null,"下单失败","1");
        }
        orderDao.save(order);
        return new SimpleRespose(new OrderRespose(order), "下单成功", "0");
    }

    @GetMapping("changeStatus")
    @ResponseBody
    public Object update(@RequestParam String orderId,@RequestParam String status ,@RequestParam String mobileNumber){
        Optional<User> user = userDao.findById(mobileNumber);
        String admin = user.get().getAdmin();
        boolean isAdmin = false;
        if (null != admin && !StringUtils.isEmpty(admin) ){
            isAdmin = true;
        }
        if (status.equals(OrderStatus.REJECT.getName())){
        //TODO
         }
    if (status.equals(OrderStatus.APPROVAL.getName())){
        //TODO
    }
        Optional<Order> byId = orderDao.findById(orderId);
        if (byId.isPresent()){
            byId.get().setStatus(status);
        }else {
             return  new SimpleRespose(null,"更新失败","1");
        }
        Order save = orderDao.save(byId.get());
       return new SimpleRespose(new OrderRespose(save), null, "0");
    }



    @GetMapping("getOrderById")
    @ResponseBody
    public Object getOrderById(@RequestParam("orderId") String orderId) {
        Optional<Order> byId = orderDao.findById(orderId);
        return new SimpleRespose(byId.isPresent() ? new OrderRespose(byId.get()) : null, null, "0");
    }

    @GetMapping("listAllOrder")
    @ResponseBody
    public Object listAll(@RequestParam(name = "mobileNumber") String mobileNumber) {
        List<Order> orders = orderDao.listAllOrder(mobileNumber);
        List<OrderRespose> orderDetails = new ArrayList<>();
        for (Order order : orders) {
            if (!order.getStatus().equals(OrderStatus.DELETE.getName())) {
                OrderRespose orderDetail = new OrderRespose(order);
                orderDetails.add(orderDetail);
            }
        }
        return new SimpleRespose(orderDetails, null, "0");
    }

    /**
     * @param orderCreate
     * @return
     */
    private Order generatedOrder(OrderCreate orderCreate) {
        //1.检查时间
        if (!new Date().before(orderCreate.getDate())){
            return null;
        }
        //2.检查缓存中有没有
        boolean exit = checkStock(orderCreate.getProductCode(), orderCreate.getDate());
        if (!exit){
            boolean count = checkCount(orderCreate.getProductCode(), orderCreate.getDate());
            //有  而且够
            if (count){
                //2.1生成订单
                return getOrder(orderCreate);
            }else {
                //有 但是不够
                return null;
            }
        }else {
            //没有
            stockDao.save(new Stock(orderCreate.getProductCode(),orderCreate.getDate()));
            return getOrder(orderCreate);
        }
         

    }

    private boolean checkStock(String productCode,Date date){
        Jedis connect = redisUtil.getConnect();
        String s = connect.get(productCode.concat(date.toString()));
        return  s == null ;
    }
    private boolean checkCount(String productCode,Date date){
        Jedis connect = redisUtil.getConnect();
        String s = connect.get(productCode.concat(date.toString()));
        return  Long.parseLong(s) < 5000 ;
    }
    private void addCount(String key,Integer productNumber){
        Jedis connect = redisUtil.getConnect();
        String s = connect.get(key);
        long l =0L;
        if (s != null){
            l = Long.parseLong(s);
        }
        l = l + productNumber;
        connect.set(key, new Long(l).toString());
        connect.expire(key,3600);
    }

    private Order getOrder(OrderCreate orderCreate){

        Optional<User> userById = userDao.findById(orderCreate.getMobileNumber());
        Optional<Product> productDaoById = productDao.findById(orderCreate.getProductCode());
        User user = userById.get();
        Product product = productDaoById.get();
        Order order = new Order();
        order.setProduct(product);
        order.setUser(user);
        order.setStartDate(orderCreate.getDate());
        //设置订单状态
        order.setStatus(OrderStatus.GENERATED.getName());
        //生成订单号
        order.setOrderId(String.valueOf(System.currentTimeMillis()));
        //计算订单价钱
        Integer productNum = orderCreate.getProductNum();
        order.setTotalPrice(String.valueOf(productNum * (Long.parseLong(product.getPrice()))));
        //订单生成时间
        order.setGenerationDate(new Date());
        order.setProductNum(orderCreate.getProductNum());
        //2.2增加缓存
        addCount(orderCreate.getProductCode().concat(orderCreate.getDate().toString()),orderCreate.getProductNum());
        return  order;
    }

    @GetMapping("delete")
    @ResponseBody
    public Object delete(@RequestParam("orderId") String orderId) {
        if (orderDao.findById(orderId).isPresent()) {
            orderDao.deleteById(orderId);
            return new SimpleRespose(null, null, "0");
        } else {
            return new SimpleRespose(null, "删除失败", "1");
        }
    }
}

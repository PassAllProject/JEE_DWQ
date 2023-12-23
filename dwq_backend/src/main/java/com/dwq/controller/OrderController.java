package com.dwq.controller;
import com.dwq.entity.dto.Order;
import com.dwq.service.impl.OrderService;
import io.lettuce.core.dynamic.annotation.Param;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
@RestController
@RequestMapping("/api/auth/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public int addOrder(@RequestBody Order order) {
        orderService.addOrder(order);
        for(Order order1:orderService.getAllOrders()){

            if(order1.getTotalPrice().compareTo(order.getTotalPrice())==0&&order.getUserId().equals(order1.getUserId())){
                return order1.getOrderId();
            }
        }
        return 0;
    }

    @DeleteMapping("/{id}")
    public int deleteOrder(@PathVariable int id) {
        return orderService.deleteOrder(id);
    }

    @PutMapping("/{id}")
    public int updateOrder(@RequestBody Order order,@PathVariable int id) {
        order.setOrderId(id);
        return orderService.updateOrder(order);
    }

    @GetMapping
    public List<Order> getAllOrders(Integer orderId)
    {
        if(orderId==null)
        return orderService.getAllOrders();
        else
        return orderService.getOrderById(orderId);
    }

}

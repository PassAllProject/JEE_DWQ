package com.dwq.controller;

import com.dwq.entity.dto.OrderDetail;
import com.dwq.service.impl.OrderDetailService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/orderDetails")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @PostMapping
    public void addOrderDetail(@RequestBody OrderDetail orderDetail) {
        orderDetailService.addOrderDetail(orderDetail);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderDetail(@PathVariable("id") Integer detailId) {
        orderDetailService.deleteOrderDetail(detailId);
    }

    @PutMapping("/{id}")
    public void updateOrderDetail(@RequestBody OrderDetail orderDetail,@PathVariable("id") Integer detailId) {
        orderDetail.setDetailId(detailId);
        orderDetailService.updateOrderDetail(orderDetail);
    }
    @GetMapping
    public List<OrderDetail> getOrderDetail(Integer detailId) {
        if(detailId!=null)
           return orderDetailService.getOrderDetail(detailId);
        else
          return orderDetailService.getAllOrderDetails();
    }
}

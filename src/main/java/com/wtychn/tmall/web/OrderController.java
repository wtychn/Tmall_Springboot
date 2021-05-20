package com.wtychn.tmall.web;

import com.wtychn.tmall.pojo.Order;
import com.wtychn.tmall.service.OrderItemService;
import com.wtychn.tmall.service.OrderService;
import com.wtychn.tmall.util.Page4Navigator;
import com.wtychn.tmall.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController
@Api(value = "订单管理")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;

    @GetMapping("/orders")
    @ApiOperation(value = "订单分页查询")
    public Page4Navigator<Order> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start < 0 ? 0 : start;
        Page4Navigator<Order> page = orderService.list(start, size, 5);
        orderItemService.fill(page.getContent());
        orderService.removeOrderFromOrderItem(page.getContent());
        return page;
    }

    @PutMapping("deliveryOrder/{oid}")
    @ApiOperation(value = "订单发货")
    public Object deliveryOrder(@PathVariable int oid) throws IOException {
        Order o = orderService.get(oid);
        o.setDeliveryDate(new Date());
        o.setStatus(OrderService.waitConfirm);
        orderService.update(o);
        return Result.success();
    }
}

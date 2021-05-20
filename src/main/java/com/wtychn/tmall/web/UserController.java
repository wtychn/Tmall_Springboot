package com.wtychn.tmall.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wtychn.tmall.pojo.User;
import com.wtychn.tmall.service.UserService;
import com.wtychn.tmall.util.Page4Navigator;

@RestController
@Api(value = "用户管理")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/users")
    @ApiOperation(value = "用户分页查询")
    public Page4Navigator<User> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start < 0 ? 0 : start;
        return userService.list(start, size, 5);
    }

}

package com.wtychn.tmall.web;

import com.wtychn.tmall.pojo.Property;
import com.wtychn.tmall.service.PropertyService;
import com.wtychn.tmall.util.Page4Navigator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(value = "属性管理")
public class PropertyController {
    @Autowired
    PropertyService propertyService;

    @GetMapping("/categories/{cid}/properties")
    @ApiOperation(value = "分页查询")
    public Page4Navigator<Property> list(@PathVariable("cid") int cid, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start < 0 ? 0 : start;
        return propertyService.list(cid, start, size, 5);
    }

    @GetMapping("/properties/{id}")
    @ApiOperation(value = "按id查询")
    public Property get(@PathVariable("id") int id) throws Exception {
        return propertyService.get(id);
    }

    @PostMapping("/properties")
    @ApiOperation(value = "增加数据")
    public Object add(@RequestBody Property bean) throws Exception {
        propertyService.add(bean);
        return bean;
    }

    @DeleteMapping("/properties/{id}")
    @ApiOperation(value = "删除数据")
    public String delete(@PathVariable("id") int id, HttpServletRequest request) throws Exception {
        propertyService.delete(id);
        return null;
    }

    @PutMapping("/properties")
    @ApiOperation(value = "更新数据")
    public Object update(@RequestBody Property bean) throws Exception {
        propertyService.update(bean);
        return bean;
    }

}

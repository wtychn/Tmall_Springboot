package com.wtychn.tmall.web;

import com.wtychn.tmall.pojo.Product;
import com.wtychn.tmall.service.CategoryService;
import com.wtychn.tmall.service.ProductImageService;
import com.wtychn.tmall.service.ProductService;
import com.wtychn.tmall.util.Page4Navigator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@Api(value = "产品管理")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductImageService productImageService;

    @GetMapping("/categories/{cid}/products")
    @ApiOperation(value = "分页查询")
    public Page4Navigator<Product> list(@PathVariable("cid") int cid, @RequestParam(value = "start", defaultValue = "0") int start,@RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start<0?0:start;

        Page4Navigator<Product> page = productService.list(cid, start, size, 5);

        productImageService.setFirstProductImages(page.getContent());
        return page;
    }

    @GetMapping("/products/{id}")
    @ApiOperation(value = "按id查询")
    public Product get(@PathVariable("id") int id) throws Exception {
        return productService.get(id);
    }

    @PostMapping("/products")
    @ApiOperation(value = "增加数据")
    public Object add(@RequestBody Product bean) throws Exception {
        bean.setCreateDate(new Date());
        productService.add(bean);
        return bean;
    }

    @DeleteMapping("/products/{id}")
    @ApiOperation(value = "删除数据")
    public String delete(@PathVariable("id") int id, HttpServletRequest request)  throws Exception {
        productService.delete(id);
        return null;
    }

    @PutMapping("/products")
    @ApiOperation(value = "更新数据")
    public Object update(@RequestBody Product bean) throws Exception {
        productService.update(bean);
        return bean;
    }
}

package com.wtychn.tmall.web;

import com.wtychn.tmall.pojo.Category;
import com.wtychn.tmall.service.CategoryService;
import com.wtychn.tmall.util.ImageUtil;
import com.wtychn.tmall.util.Page4Navigator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
 
@RestController
@Api(value = "Category管理")
public class CategoryController {
	@Autowired
	CategoryService categoryService;

	@GetMapping("/categories")
	@ApiOperation(value = "分页查询")
	public Page4Navigator<Category> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
		start = start < 0 ? 0 : start;
		return categoryService.list(start, size, 5);
	}

	@PostMapping("/categories")
	@ApiOperation(value = "添加数据")
	public Object add(Category bean, MultipartFile image, HttpServletRequest request) throws Exception {
		categoryService.add(bean);
		saveOrUpdateImageFile(bean, image, request);
		return bean;
	}
	public void saveOrUpdateImageFile(Category bean, MultipartFile image, HttpServletRequest request)
			throws IOException {
		File imageFolder= new File(request.getServletContext().getRealPath("img/category"));
		File file = new File(imageFolder,bean.getId()+".jpg");
		if(!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		image.transferTo(file);
		BufferedImage img = ImageUtil.change2jpg(file);
		ImageIO.write(img, "jpg", file);
	}

	@DeleteMapping("/categories/{id}")
	@ApiOperation(value = "删除数据")
	public String delete(@PathVariable("id") int id, HttpServletRequest request)  throws Exception {
		categoryService.delete(id);
		File  imageFolder= new File(request.getServletContext().getRealPath("img/category"));
		File file = new File(imageFolder,id+".jpg");
		file.delete();
		return null;
	}

	@GetMapping("/categories/{id}")
	@ApiOperation(value = "按id查询")
	public Category get(@PathVariable("id") int id) throws Exception {
		return categoryService.get(id);
	}

	@PutMapping("/categories/{id}")
	@ApiOperation(value = "更新数据")
	public Object update(Category bean, MultipartFile image,HttpServletRequest request) throws Exception {
		String name = request.getParameter("name");
		bean.setName(name);
		categoryService.update(bean);

		if(image!=null) {
			saveOrUpdateImageFile(bean, image, request);
		}
		return bean;
	}

}


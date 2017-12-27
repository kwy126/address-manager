package com.circle.controller;

import com.circle.service.IProductService;
import com.circle.utils.json.JsonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping(value = "product")
public class FileUploadController  extends AbstractController{
    @Autowired
    private IProductService productService = null;

    @ResponseBody
    @RequestMapping(value = "findProductList")
    public JsonReturn findProductList(String search, int pageNumber, int pageSize, HttpServletRequest request) {
        return productService.findProductList(search, pageNumber,pageSize,acctName(request));
    }

    @ResponseBody
    @RequestMapping(value = "findProductPage")
    public JsonReturn findProductPage(@RequestParam String search, @RequestParam int page, HttpServletRequest request) {
        return productService.findProductPage(search, page, acctName(request));
    }

    @ResponseBody
    @RequestMapping(value = "findAllProduct")
    public JsonReturn findProductCount(String search, int pageNumber, int pageSize,HttpServletRequest request) {
        return productService.findProductList(search,pageNumber,pageSize, acctName(request));
    }


    @RequestMapping(value = "addProduct")
    @ResponseBody
    public JsonReturn addAccount(@RequestParam String name, @RequestParam String number, @RequestParam String price, @RequestParam String description,HttpServletRequest request) {
        return productService.addProduct(name, number, price,description, acctName(request));
    }

    @RequestMapping(value = "findProductById")
    @ResponseBody
    public JsonReturn findProductById(@RequestParam int id){
        return productService.findProductById(id);
    }

    @RequestMapping(value = "updateProductById")
    @ResponseBody
    public JsonReturn updateProductById(@RequestParam String name, @RequestParam String number, @RequestParam String price,@RequestParam String description,@RequestParam String id, HttpServletRequest request){
        return productService.updateProductById(name,number,price,description,id,acctName(request));
    }

    @ResponseBody
    @RequestMapping(value = "delProductById")
    public JsonReturn delProductById(@RequestParam String  id) {
        return productService.delProductById(Integer.valueOf(id));

    }

    @ResponseBody
    @RequestMapping(value="upload")
    public JsonReturn upload(@RequestParam("file") MultipartFile file, @RequestParam String id,HttpServletResponse response, HttpServletRequest request){
        //1. 首先判断文件后缀
        String originalFilename = file.getOriginalFilename();
        String prefix = null;
        int prefixIndex = originalFilename.lastIndexOf(".");
        if (prefixIndex != -1) {
            prefix = originalFilename.substring(prefixIndex + 1);
            prefix = prefix.toLowerCase();

        }
        String imageRoot = request.getSession().getServletContext().getRealPath("/")+"image/";
        String renameImage = UUID.randomUUID().toString();
        // 可以使用日期作为文件夹的名字
        Date nowDate = new Date();
        String folderName = new SimpleDateFormat("yyyyMMdd").format(nowDate);
        File folderFile = new File(imageRoot + "/" + folderName);
        // 如果不存在这个目录则进行创建。
        // 为了保证高并发时不会重复创建目录，要进行线程锁定
        // 使用悲观锁就行了
        if (!folderFile.exists()) {
            synchronized (FileUploadController.class) {
                while (!folderFile.exists()) {
                    folderFile.mkdirs();
                }
            }
        }


        // 以下就是这个即将创建的文件的完整路径了
        String relativePath = folderName + "/" + renameImage + "." + prefix;
        String fullImagePath = imageRoot + "/" + relativePath;

        // 4、====
        try {
            file.transferTo(new File(fullImagePath));
        } catch (IllegalStateException e) {
            return JsonReturn.buildFailure(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        productService.updateProductURLById(relativePath,Integer.valueOf(id),acctName(request));
        return JsonReturn.buildSuccess("文件上传成功！其路径是：" + relativePath);
    }
}

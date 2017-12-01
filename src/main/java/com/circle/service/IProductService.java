package com.circle.service;

import com.circle.util.json.JsonReturn;
import com.circle.vo.ProductModel;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by keweiyang on 2017/6/4.
 */
public interface IProductService extends IBaseService<ProductModel> {

    JsonReturn addProduct(String name, String number, String price, String description, String s);

    JsonReturn findProductList(String search, int page, int pageSize, String s);

    JsonReturn findProductPage(String search, int page, String s);

    JsonReturn findAllProduct(String search, String s);

    JsonReturn findProductById(int id);

    JsonReturn updateProductById(String name, String number, String price, String description, String id, String s);

    JsonReturn updateProductURLById(String url, int id, String s);

    JsonReturn delProductById(Integer id);

}

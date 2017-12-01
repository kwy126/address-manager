package com.circle.service;

import com.circle.constant.PageConstant;
import com.circle.dao.ProductDAO;
import com.circle.util.date.DateTimeUtil;
import com.circle.util.json.JsonReturn;
import com.circle.util.pageutil.PageUtils;
import com.circle.vo.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProductServiceImpl extends BaseService<ProductModel> implements IProductService{

    @Autowired
    private ProductDAO dao;

    public JsonReturn addProduct(String name, String number, String price,String description ,String s) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(number) || StringUtils.isEmpty(price)|| StringUtils.isEmpty(description)) {
            return JsonReturn.buildFailure("添加失败！");
        }

        ProductModel  model = new ProductModel();
        model.setName(name);
        model.setNumber(Integer.valueOf(number));
        model.setDescription(description);
        model.setPrice(Double.valueOf(price));
        model.setCreateTime(DateTimeUtil.getCurrentTime());
        model.setCreator(s);
        model.setTimestamp(new Timestamp(DateTimeUtil.getCurrentTime().getTime()));
        dao.create(model);
        return JsonReturn.buildSuccess(model.getId());
    }

    public JsonReturn findProductList(String search, int page,int pageSize, String s) {
        Map<String,Object> result = new HashMap<String,Object>();
        int count = dao.findProductPage(search);
        List<ProductModel> productModelList = dao.findProductList(search, (page - 1) * pageSize, pageSize);
        if (CollectionUtils.isEmpty(productModelList)) {
            return JsonReturn.buildFailure("未获取相关数据！");
        }

        result.put("total",count);
        result.put("rows",productModelList);
        return JsonReturn.buildSuccess(result);
    }

    public JsonReturn findProductPage(String search, int page, String s) {
        int count = dao.findProductPage(search);
        return JsonReturn.buildSuccess(PageUtils.calculatePage(page,count,PageConstant.DEFAULT_LINE));

    }

    public JsonReturn findAllProduct(String search, String s) {
        List<ProductModel> productModelList  = dao.findAllProduct(search);
        if (CollectionUtils.isEmpty(productModelList)) {
            return JsonReturn.buildFailure("未获取相关数据！");
        }
        Map<String,Object> result = new HashMap<String,Object>();
        int count = dao.findProductPage("");
        result.put("total",count);
        result.put("rows",productModelList);
        return JsonReturn.buildSuccess(result);
    }

    public JsonReturn findProductById(int id) {
      ProductModel productModel=  dao.findProductById(id);
      return JsonReturn.buildSuccess(productModel);
    }

    public JsonReturn updateProductById(String name, String number, String price, String description, String id, String s) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(number) || StringUtils.isEmpty(price)) {
            return JsonReturn.buildFailure("修改失败！");
        }
        ProductModel  model = new ProductModel();
        model.setName(name);
        model.setNumber(Integer.valueOf(number));
        model.setPrice(Double.valueOf(price));
        model.setDescription(description);
        model.setUpdateTime(DateTimeUtil.getCurrentTime());
        model.setUpdator(s);
        model.setTimestamp(new Timestamp(DateTimeUtil.getCurrentTime().getTime()));
        dao.update(model);
        return JsonReturn.buildSuccess("修改成功！");
    }

    public JsonReturn updateProductURLById(String url, int id, String s) {
        if (StringUtils.isEmpty(url) || StringUtils.isEmpty(id) || StringUtils.isEmpty(s)) {
            return JsonReturn.buildFailure("修改失败！");
        }

        dao.updateProductURLById(url,id);
        return JsonReturn.buildSuccess("修改成功！");
    }

    public JsonReturn delProductById(Integer integer) {
        if (integer == null) {
            return JsonReturn.buildFailure("操作失败！");
        }
        dao.delete(integer);
        return JsonReturn.buildSuccess("商品删除成功！");
    }
}

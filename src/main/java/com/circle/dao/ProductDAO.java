package com.circle.dao;

import com.circle.vo.ProductModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDAO extends BaseDAO<ProductModel> {

    List<ProductModel> findProductList(@Param("search") String search, int i, int defaultLine);

    List<ProductModel> findAllProduct(String search);

    int findProductPage(@Param("search") String search);

    ProductModel findProductById(int id);

    void updateProductURLById(String url, int id);

}

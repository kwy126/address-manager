package com.circle.dao;


import com.circle.vo.FunctionCodeModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FunctionCodeDAO extends BaseDAO<FunctionCodeModel> {

    FunctionCodeModel getFunctionByName(@Param("name") String name, @Param("level") String level, @Param("description") String description);

}

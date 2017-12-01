package com.circle.service;


import com.circle.vo.FunctionCodeModel;


/**
 * Created by keweiyang on 2017/6/4.
 */
public interface IFunctionCodeService extends IBaseService<FunctionCodeModel> {

    String getFuncationByNmae(String name);



}

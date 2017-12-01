package com.circle.service;


import com.circle.vo.FunctionCodeModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FunctionCodeServiceImpl extends BaseService<FunctionCodeModel> implements IFunctionCodeService {

    public String getFuncationByNmae(String name) {
        return null;
    }
}

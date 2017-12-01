package com.circle.intercept;

import com.circle.util.LogUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

/**
 * Created by keweiyang on 2017/6/5.
 */
@Component(value = "myMethodIntercept")
public class MyMethodIntercept extends LogUtil implements MethodInterceptor {

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object result = methodInvocation.proceed();

        try {
            //1. 获取当前方法所在的类及方法名
            String methodName = new StringBuffer().append(methodInvocation.getThis().toString()).append("|").append(methodInvocation.getMethod()).toString();
            info("methodName={}", methodName);

            //2. 记录参数列表
            Object[] params = methodInvocation.getArguments();
            for(int i=0;i<params.length;i++) {
                if (params[i] == null) {
                    continue;
                }
                info("\targ[{}] -> {}", i, params[i].toString());
            }
            //3. 记录当前函数的返回值
            info("\treturn : {} - {}", methodName,result);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }
}

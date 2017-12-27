package com.circle.intercept;

import com.circle.utils.SessionKey;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by keweiyang on 2017/6/7.
 */
public class AdminIntercept extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 请求中包含/oauth/不做拦截
        if (request.getRequestURI().contains("/0/")) {
            return true;
        }
        if (request.getRequestURI().contains("/module/")) {
            return true;
        }
        if (request.getRequestURI().contains("/toLogin/")) {
            return true;
        }
        // 判断用户是否已登录
        Object object = request.getSession().getAttribute(SessionKey.LOGIN_USER_INFO);
      /*  if (object == null) {
            //System.out.printf(JsonHelper.object2Str(JsonReturn.buildFailure(LoginState.UNLOGIN)).toString());
            System.out.println(JsonReturn.buildFailure(LoginState.UNLOGIN));
            response.getOutputStream().print(JsonHelper.object2Str(JsonReturn.buildFailure(LoginState.UNLOGIN)).toString());
            return true;
        }*/

        return true;
    }
}

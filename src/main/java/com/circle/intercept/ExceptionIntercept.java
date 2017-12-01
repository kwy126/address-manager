package com.circle.intercept;

import com.circle.util.json.JsonReturn;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * Created by keweiyang on 2017/6/8.
 */
@ControllerAdvice
public class ExceptionIntercept implements Serializable {

    @ResponseBody
    @ResponseStatus(value= HttpStatus.OK)
    @ExceptionHandler({Exception.class})//监听所有Exception
    public JsonReturn exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ex.printStackTrace();
        return JsonReturn.buildFailure("服务器错误！");
    }
}

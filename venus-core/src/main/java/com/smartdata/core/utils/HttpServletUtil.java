package com.smartdata.core.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 获取HttpServlet子对象
 * @author khlu
 * @date 2018/10/15
 */
public class HttpServletUtil {

    private static ServletRequestAttributes requestAttributes;

    /**
     * 获取HttpServletRequest对象
     */
    public static HttpServletRequest getRequest(){
        requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest();
    }

    /**
     * 获取HttpServletResponse对象
     */
    public static HttpServletResponse getResponse(){
        requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getResponse();
    }
}

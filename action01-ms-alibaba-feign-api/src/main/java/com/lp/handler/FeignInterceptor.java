package com.lp.handler;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class FeignInterceptor implements RequestInterceptor {
    public void apply(RequestTemplate requestTemplate) {
        //requestTemplate.header("token", UUID.randomUUID().toString());
        try {
            //使用 RequestContextHolder 工具获取 request 相关变量
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {

                //获取request对象
                HttpServletRequest request = requestAttributes.getRequest();
                Enumeration<String> headerNames = request.getHeaderNames();
                if (headerNames != null) {
                    while (headerNames.hasMoreElements()) {
                        //头文件的key
                        String name = headerNames.nextElement();
                        //头文件的value
                        String values = request.getHeader(name);
                        //将令牌数据添加到头文件中
                        requestTemplate.header(name, values);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
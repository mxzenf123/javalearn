package org.yangxin.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

public class Demo1 {

    private static final Logger log = LoggerFactory.getLogger(Demo1.class);

    public static void main(String[] args) throws Exception {
        AnnotationConfigServletWebServerApplicationContext context =
                new AnnotationConfigServletWebServerApplicationContext(Config.class);

        RequestMappingHandlerMapping requestMappingHandlerMapping = context.getBean(RequestMappingHandlerMapping.class);

        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        System.out.println(handlerMethods);

        MockHttpServletRequest request = new MockHttpServletRequest("POST","/say");
        request.setParameter("name", "杨大哥");
        request.addHeader("token", "测试获取token");
        MockHttpServletResponse response = new MockHttpServletResponse();

        HandlerExecutionChain handler = requestMappingHandlerMapping.getHandler(request);

        MyRequestMappingHandlerAdapter requestMappingHandlerAdapter =
                context.getBean(MyRequestMappingHandlerAdapter.class);

        requestMappingHandlerAdapter.invokeHandlerMethod(request, response, (HandlerMethod)handler.getHandler());
    }
}

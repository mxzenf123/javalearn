package org.yangxin.mvc;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
@EnableConfigurationProperties({WebMvcProperties.class, ServerProperties.class})
public class Config {

    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory(ServerProperties serverProperties){
        return new TomcatServletWebServerFactory(serverProperties.getPort());
    }

    @Bean
    public DispatcherServlet dispatcherServlet(){
        return new DispatcherServlet();
    }

    @Bean
    public DispatcherServletRegistrationBean dispatcherServletRegistrationBean(DispatcherServlet dispatcherServlet, WebMvcProperties webMvcProperties){
        DispatcherServletRegistrationBean dispatcherServletRegistrationBean = new DispatcherServletRegistrationBean(dispatcherServlet, "/");
        dispatcherServletRegistrationBean.setLoadOnStartup(webMvcProperties.getServlet().getLoadOnStartup());
        return dispatcherServletRegistrationBean;
    }

    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping(){
        return new RequestMappingHandlerMapping();
    }

    @Bean
    public MyRequestMappingHandlerAdapter requestMappingHandlerAdapter(){
        List<HandlerMethodArgumentResolver> handlerMethodArgumentResolvers = new ArrayList<>();
        TokenArgumentResolver tokenArgumentResolver = new TokenArgumentResolver();
        handlerMethodArgumentResolvers.add(tokenArgumentResolver);
        MyRequestMappingHandlerAdapter requestMappingHandlerAdapter = new MyRequestMappingHandlerAdapter();
        requestMappingHandlerAdapter.setCustomArgumentResolvers(handlerMethodArgumentResolvers);
        return requestMappingHandlerAdapter;
    }
}

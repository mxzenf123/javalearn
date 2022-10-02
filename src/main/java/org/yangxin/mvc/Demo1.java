package org.yangxin.mvc;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebApplicationContext;

public class Demo1 {

    public static void main(String[] args) {
        AnnotationConfigServletWebApplicationContext context =
                new AnnotationConfigServletWebApplicationContext(Config.class);
    }
}

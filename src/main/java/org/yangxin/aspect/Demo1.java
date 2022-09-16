package org.yangxin.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.aspectj.annotation.BeanFactoryAspectInstanceFactory;
import org.springframework.aop.aspectj.annotation.MetadataAwareAspectInstanceFactory;
import org.springframework.aop.aspectj.annotation.ReflectiveAspectJAdvisorFactory;
import org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.List;

public class Demo1 implements IDemo1 {

    public void say(String word){
        System.out.println(word);
    }

    @Test
    public void test_advisor() throws Exception{

//        Method method = MyAspect.class.getDeclaredMethod("logFile", ProceedingJoinPoint.class);
//        Around annotation = AnnotationUtils.getAnnotation(method, Around.class);
//        System.out.println(annotation);
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("demo1",Demo1.class);
        context.registerBean("myAspect", MyAspect.class);

        ReflectiveAspectJAdvisorFactory reflectiveAspectJAdvisorFactory = new ReflectiveAspectJAdvisorFactory(context.getDefaultListableBeanFactory());
        MetadataAwareAspectInstanceFactory factory =
                new BeanFactoryAspectInstanceFactory(context.getDefaultListableBeanFactory(), "myAspect");
        List<Advisor> advisors = reflectiveAspectJAdvisorFactory.getAdvisors(factory);
        advisors.stream().forEach(advisor -> {
            System.out.println(advisor);
        });
    }

    @Test
    public void test_annotationAspectBeanPostProcessor(){
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("demo1",Demo1.class);
        context.registerBean(MyAspect.class);
        context.registerBean(AnnotationAwareAspectJAutoProxyCreator.class);
//        context.registerBean(AspectJAwareAdvisorAutoProxyCreator.class);

        context.refresh();

        IDemo1 demo1 = (IDemo1)context.getBean("demo1");
        demo1.say("杨大哥");
        System.out.println(demo1.getClass());

        context.close();
    }
}

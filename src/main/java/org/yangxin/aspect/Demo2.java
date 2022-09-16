package org.yangxin.aspect;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Demo2 implements IDemo1 {

    public void say(String words){
        System.out.println(words);
    }

    public static void main(String[] args) throws Exception {
        Demo2 demo2 = new Demo2();
        Method method = Demo2.class.getDeclaredMethod("say", String.class);
        Object[] _args = new Object[]{"杨大哥好"};
        List<Advisor> advisors = new ArrayList<>();
        ProxyFactory proxyFactory = new ProxyFactory();
        advisors.add(new DefaultPointcutAdvisor(new Advice1()));
        advisors.add(new DefaultPointcutAdvisor(new Advice2()));
        proxyFactory.addAdvisors(advisors);
        proxyFactory.setTarget(demo2);
        Object proxy = proxyFactory.getProxy(Demo2.class.getClassLoader());
        ((IDemo1)proxy).say("杨大哥好");
//        ReflectiveMethodInvocation invocation =
//                new ReflectiveMethodInvocation(demo2, demo2, method, _args, demo2.getClass(),advices);
    }
}


class Advice1 implements MethodInterceptor{
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("advice1前置通知");
        Object retVal = invocation.proceed();
        System.out.println("advice1后置通知");
        return retVal;
    }
}

class Advice2 implements MethodInterceptor{
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("advice2前置通知");
        Object retVal = invocation.proceed();
        System.out.println("advice2后置通知");
        return retVal;
    }
}
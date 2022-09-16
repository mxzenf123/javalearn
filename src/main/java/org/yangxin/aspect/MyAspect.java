package org.yangxin.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class MyAspect {

//    @Before(value = "execution(* org.yangxin..*(..))")
    @Around(value = "execution(* org.yangxin..*(..))")
    public void logFile(ProceedingJoinPoint proceedingJoinPoint){

        System.out.println("开始logFile调用方法");
        try {
            proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
            System.out.println("logFile调用结束");
        } catch (Throwable throwable) {
            System.out.println("调用异常");
//            throwable.printStackTrace();
        }
    }

//    @Around(value = "execution(* org.yangxin..*(..))")
//    public void recordFile(ProceedingJoinPoint proceedingJoinPoint){
//
//        System.out.println("开始recordFile调用方法");
//        try {
//            proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
//            System.out.println("recordFile调用结束");
//        } catch (Throwable throwable) {
//            System.out.println("调用异常");
////            throwable.printStackTrace();
//        }
//    }

//    public void recordLog(){}
}

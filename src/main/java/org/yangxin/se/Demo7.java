package org.yangxin.se;

import org.junit.jupiter.api.Test;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.concurrent.CompletableFuture;

/**
 * CompletableFuture
 */
public class Demo7 {

    public static void main(String[] args) {
        Object lock = new Object();
        synchronized (lock) {
            try {
                Thread.sleep(10000 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public synchronized void test_03() throws Exception{
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() ->{

            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("【"+Thread.currentThread().getName()+"】执行结束");
            return "运行完毕";
        });

        stringCompletableFuture.complete("不等了直接完成");

        System.out.println(stringCompletableFuture.get());
    }

    @Test
    public void test_01() throws Exception{
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程【" + Thread.currentThread().getName() + "】执行完毕");
        });

        Thread.sleep(100);
        Void aVoid = voidCompletableFuture.get();
        System.out.println("是否执行完毕："+voidCompletableFuture.isDone());
    }

    @Test
    public void test_02() throws Exception{
        CompletableFuture<Void> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
//            System.out.println("supplyAsync执行的线程：" + Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (1==1) {
                throw new UnsupportedOperationException("异常");
            }
            return "执行完毕";
        }).thenAccept(s->{
            System.out.println("消费结果：" + s);
        }).exceptionally(e->{
            System.out.println("执行异常：" + e);
            return  null;
        });
//                .whenCompleteAsync((v,e)->{
//            System.out.println("收到异常"+e);
////            System.out.println("whenComplete执行的线程：" + Thread.currentThread().getName());
//            System.out.println("complete完成");
//        })
//                .exceptionally(e->{
//            return "异常"+e.toString();
//        })
                ;
        System.out.println("返回值："+stringCompletableFuture.get());
    }
}

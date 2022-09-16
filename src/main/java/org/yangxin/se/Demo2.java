package org.yangxin.se;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程状态
 */
public class Demo2 {

    private final static Object lock = new Object();

    private final static ReentrantLock rLock = new ReentrantLock();

    public static void main(String[] args) throws Exception {
//        test_wait_notify_thread_status();
        test_reentrant_lock();
    }

    private static void getLockThread(Runnable runnable){
        try {
            System.out.println(Thread.currentThread().getName()+"等待锁");
            rLock.lockInterruptibly();
            System.out.println(Thread.currentThread().getName()+"获取了锁");
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName()+"等待锁被打断");
            return;
        }
        try {
            runnable.run();
        } /*catch (InterruptedException e) {

            System.out.println(Thread.currentThread().getName()+"等待锁被打断");
//            return;
        }*/ finally {
            if (rLock.isLocked()) {
                rLock.unlock();
                System.out.println(Thread.currentThread().getName()+"释放了锁");
            }

        }
    }

//    @Test
    public static void test_reentrant_lock() throws Exception{
        Thread thread1 = new Thread(() ->{
            getLockThread(()->{

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        },"thread1");

        Thread thread2 = new Thread(() ->{
            getLockThread(()->{

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        },"thread2");

//        getLockThread(()->{
//
//        });
//        Thread.sleep(10000);
        thread1.start();
        Thread.sleep(10);
        thread2.start();
        thread2.interrupt();
    }

    public static void test_wait_notify_thread_status() throws Exception{
        new Thread(()->{
            synchronized (lock) {
                try {
                    lock.wait();
                    System.out.println(Thread.currentThread().getName()+"执行完毕");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"thread1").start();

        new Thread(()->{
            synchronized (lock) {
                try {
                    lock.wait();
                    System.out.println(Thread.currentThread().getName()+"执行完毕");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"thread2").start();

        Thread.sleep(2000);

        synchronized (lock) {
            lock.notifyAll();
        }

        System.out.println(Thread.currentThread().getName()+"执行完毕");
    }
}



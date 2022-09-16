package org.yangxin.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池使用
 */
public class Demo1 {

    @Test
    public void test01(){
        ExecutorService executorService = Executors.newFixedThreadPool(1);
    }
}

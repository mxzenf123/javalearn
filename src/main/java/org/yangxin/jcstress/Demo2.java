package org.yangxin.jcstress;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.II_Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@JCStressTest
@State
@Outcome(id = "100000, 100000", expect = Expect.ACCEPTABLE_INTERESTING, desc = "预想值")
public class Demo2 {

    private AtomicInteger i = new AtomicInteger(0);
    static List<Integer> is;
    static int size = 100000;
    static {
        is = new ArrayList<>();
        for (int j = 0; j < size; j++) {
            is.add(1);
        }

    }
    @Actor
    public void actor1(II_Result r){
        int i = 0;
        for (int j = 0; j < size; j++) {
            i += is.get(j);
        }
        r.r1 = i;
    }

    @Actor
    public void actor2(II_Result r){


        final Thread curThread = Thread.currentThread();
        is.parallelStream().forEach(j->{
//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            Thread jThread = Thread.currentThread();
            if (curThread.getName().equals(jThread.getName())) {
                i.addAndGet(j);
            }

        });

        r.r2 = i.get();
    }

    public static void main(String[] args) {
        String aaa121 = "LA1001GetFile";
        System.out.println(aaa121.endsWith("GetFile"));
    }
}

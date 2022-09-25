package org.yangxin.jcstress;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@JCStressTest
@State
@Outcome(id = "10", expect = Expect.ACCEPTABLE_INTERESTING, desc = "预想值")
public class Demo4 {
    private AtomicInteger i = new AtomicInteger(0);
    private List<Integer> ls = new ArrayList(){
        {
            add(1);
            add(1);
            add(1);
            add(1);
            add(1);
            add(1);
            add(1);
            add(1);
            add(1);
            add(1);
        }
    };

    @Actor
    public void action(I_Result r){
        ls.parallelStream().forEach(j->{
            i.addAndGet(j);
        });
        r.r1 = i.get();
    }
}

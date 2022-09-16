package org.yangxin.jcstress;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.II_Result;
import org.openjdk.jcstress.infra.results.I_Result;

@JCStressTest
@State
@Outcome(id = "2", expect = Expect.ACCEPTABLE_INTERESTING, desc = "预想值")
@Outcome(id = "1", expect = Expect.FORBIDDEN, desc = "预想值")
@Outcome(id = "0", expect = Expect.FORBIDDEN, desc = "不应该出现")
public class Demo3 {

    private int a = 0;
    private int b = 0;
    private int x = 0;
    private int y = 0;

    @Actor
    public void actor1(){
        a = 1;
        x = b;
    }

    @Actor
    public void actor2(){
        b = 1;
        y = a;
    }

    @Arbiter
    public void actor3(I_Result r){
        r.r1 = x + y;
    }
}

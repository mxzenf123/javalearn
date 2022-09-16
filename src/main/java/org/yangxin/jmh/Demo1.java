package org.yangxin.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.File;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@State(Scope.Thread)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
public class Demo1 {

    @Benchmark
    public void stringAdd(){
        String s = "";
        for (int i = 0; i < 1000; i++) {
            s += i;
        }
    }

    @Benchmark
    public void stringBuilderAppend(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append(i);
        }

    }

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder().include(Demo1.class.getSimpleName())
                .result("result.json")
                .resultFormat(ResultFormatType.JSON)
                .build();
        Collection<RunResult> results = new Runner(opt).run();

    }
}

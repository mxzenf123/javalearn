package org.yangxin.se;

import java.util.concurrent.*;

/**
 * forkjoin示例
 */
public class  Demo6 extends RecursiveTask<Integer> {

    private Integer start;
    private Integer end;
    private static final int THRESHOLD = 2;

    public Demo6(Integer start, Integer end){
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        if ((end - start + 1) <= THRESHOLD) {
            System.out.println("正在fork的线程：" + Thread.currentThread().getName());
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            int _end = (end + start)/2;

            Demo6 _demo61 = new Demo6(start, _end);
            Demo6 _demo62 = new Demo6(_end+1, end);

            _demo61.fork();
            _demo62.fork();

            return _demo61.join()+_demo62.join();
        }
        return sum;
    }

    public static void main(String[] args) throws Exception {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        Demo6 demo6 = new Demo6(1,100000);
        Future<Integer> submit = forkJoinPool.submit(demo6);
        Integer join = submit.get();
        System.out.println(join);
    }
}

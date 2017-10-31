package assignment4;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

  private static final int CORE_POOL_SIZE = 10;
  private static final int MAX_POOL_SIZE = 10;

  public static void main(final String[] args) throws InterruptedException, ExecutionException {
    final PriorityBlockingQueue<Runnable> jobs = new PriorityBlockingQueue<>();

    final ThreadPoolExecutor threadPool =
        new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, 1, TimeUnit.SECONDS, jobs);

    final PriorityCallable<Integer> partialSumTask1 = new PriorityCallable<Integer>() {

      @Override
      public Integer call() {
        Integer partialSum = 0;
        final int low = 0;
        final int hi = 100;
        for (int i = low; i <= hi; i++) {
          partialSum += i;
        }
        return partialSum;
      }

      @Override
      public int getPriority() {
        return 0;
      }
    };

    final PriorityCallable<Integer> partialSumTask2 = new PriorityCallable<Integer>() {

      @Override
      public Integer call() {
        Integer partialSum = 0;
        final int low = 101;
        final int hi = 200;
        for (int i = low; i <= hi; i++) {
          partialSum += i;
        }
        return partialSum;
      }

      @Override
      public int getPriority() {
        return 1;
      }
    };

    final Future<Integer> halfSumFuture1 = threadPool.submit(partialSumTask1);
    final Future<Integer> halfSumFuture2 = threadPool.submit(partialSumTask2);

    final Integer totalSum = halfSumFuture1.get() + halfSumFuture2.get();

    System.out.println(totalSum);

    threadPool.shutdown();
  }

}

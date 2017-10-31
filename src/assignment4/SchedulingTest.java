package assignment4;

import java.util.concurrent.Future;
import assignment4.pooling.PriorityCallable;
import assignment4.pooling.PriorityThreadPoolExecutor;

public class SchedulingTest {

  public static void main(final String[] args) {
    // Get PriorityThreadPoolExecutor with only 1 thread for the sake of testing scheduling.
    final PriorityThreadPoolExecutor threadPool = ThreadPool.getPriorityThreadPoolExecutor(1);

    final PriorityCallable<Integer> lowerPriorityTask = new PriorityCallable<Integer>() {

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
        return 1;
      }
    };

    final PriorityCallable<Integer> higherPriorityTask = new PriorityCallable<Integer>() {

      @Override
      public Integer call() {
        Integer partialSum = 0;
        final int low = 1;
        final int hi = 9000000;
        for (int j = low; j <= hi; j++) {
          partialSum += (int) Math.log10(partialSum);
        }
        return partialSum;
      }

      @Override
      public int getPriority() {
        return 0;
      }
    };


    // Two lowerPriorityTask (f1 and f2) are submitted before a higherPriorityTask (f3).
    // Since we only have 1 thread available, the first submitted task (f1) will be run immediately,
    // and the other two will be executed based on their priority. Even though f2 is submitted
    // before f3, f3 has a higher priority and will be run before f2. Therefore, the output in
    // console should be:
    // f1 done.
    // f3 done.
    // f2 done.
    final Future<Integer> f1 = threadPool.submit(lowerPriorityTask);
    final Future<Integer> f2 = threadPool.submit(lowerPriorityTask);
    final Future<Integer> f3 = threadPool.submit(higherPriorityTask);

    boolean f1Done = false;
    boolean f2Done = false;
    boolean f3Done = false;

    while (!f1Done || !f2Done || !f3Done) {
      if (f1.isDone() && !f1Done) {
        f1Done = true;
        System.out.println("f1 done.");
      }
      if (f2.isDone() && !f2Done) {
        f2Done = true;
        System.out.println("f2 done.");
      }
      if (f3.isDone() && !f3Done) {
        f3Done = true;
        System.out.println("f3 done.");
      }
    }

    threadPool.shutdown();

  }

}

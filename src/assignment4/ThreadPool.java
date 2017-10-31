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
    final PriorityCallable<Integer> fibonancciGen1 = new PriorityCallable<Integer>() {
    	int num = 20;
    	int sum = 0 ;
        @Override
        public Integer call() {
        	for (int i = 1; i <= num; i++){  
         		   sum+= fibonacci(i);
         	   		}
        	return sum;
        }
        public long fibonacci(int n) {
            if (n <= 1) return n;
            else return fibonacci(n-1) + fibonacci(n-2);
        }

        @Override
        public int getPriority() {
          return 2;
        }
      };
      final PriorityCallable<Integer> fibonancciGen2 = new PriorityCallable<Integer>() {
    	  int num = 40;
      	int sum = 0 ;
          @Override
          public Integer call() {
          	for (int i = 1; i <= num; i++){  
           		   sum+= fibonacci(i);
           	   		}
          	return sum;
          }
          public long fibonacci(int n) {
              if (n <= 1) return n;
              else return fibonacci(n-1) + fibonacci(n-2);
          }

          @Override
          public int getPriority() {
            return 0;
          }
        };  
        final PriorityCallable<Integer> bulbleSort1 = new PriorityCallable<Integer>() {
      	  int num = 20000;
        	int sum = 0 ;
        	int numArray[] = new int[num];
            @Override
            public Integer call() {
            	for (int i = 1; i < num; i++){  
             		   numArray[i]=(int)(Math.random()*100);
             	   		}
            	bubbleSort(numArray);
            	return 1;
            }
            public void bubbleSort(int[] arr) {  
                int n = arr.length;  
                int temp = 0;  
                 for(int i=0; i < n; i++){  
                         for(int j=1; j < (n-i); j++){  
                                  if(arr[j-1] > arr[j]){  
                                         //swap elements  
                                         temp = arr[j-1];  
                                         arr[j-1] = arr[j];  
                                         arr[j] = temp;  
                                 }  
                                  
                         }  
                 }
            }  

            @Override
            public int getPriority() {
              return 3;
            }
          };  
        final Future<Integer> halfSumFuture1 = threadPool.submit(partialSumTask1);
        final Future<Integer> halfSumFuture2 = threadPool.submit(partialSumTask2);
        final Future<Integer> fibGen1 = threadPool.submit(fibonancciGen1);
        final Future<Integer> fibGen2 = threadPool.submit(fibonancciGen2);
        final Future<Integer> bubSort1 = threadPool.submit(bulbleSort1);
        final Future<Integer> bubSort2 = threadPool.submit(bulbleSort1);
        final Integer totalSum = halfSumFuture1.get() + halfSumFuture2.get();
        System.out.println("Total Array Sum: "+totalSum);
        System.out.println("BubbleSort2: "+bubSort2.get());
        final Integer totalFibSum = fibGen1.get() + fibGen2.get();
        System.out.println("Total Fibonancci Sum: "+totalFibSum);
        System.out.println("BubbleSort1: "+bubSort1.get());
      
        threadPool.shutdown();
  }

}

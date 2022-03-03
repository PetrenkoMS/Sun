package main.java;

import java.util.concurrent.*;

import static java.lang.Thread.currentThread;

public class Pool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//            Callable<String> task = () -> currentThread().getName(); //Одна команда, поэтому не нужны {};
//            ExecutorService service = Executors.newFixedThreadPool(3);
//            for (int i = 0; i < 5; i++) {
//                Future result = service.submit(task); //Результат task (Имя потока);
//                System.out.println(result.get());
//            }
//            service.shutdown();
        //      ------
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2,
//                0L, TimeUnit.SECONDS, new SynchronousQueue());
//        Callable<String> task = () -> Thread.currentThread().getName();
//        threadPoolExecutor.setRejectedExecutionHandler((runnable, executor) -> System.out.println("Ops"));
//        for (int i = 0; i < 5; i++) {
//            threadPoolExecutor.submit(task);
//        }
//        threadPoolExecutor.shutdown();
        //------
        Object lock = new Object();
        //ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService = Executors.newWorkStealingPool();
        Callable<String> task = () -> {
            System.out.println(Thread.currentThread().getName());
            lock.wait(2000);
            System.out.println("Finished");
            return "result";
        };
        for (int i = 0; i < 5; i++) {
            executorService.submit(task);
        }
        executorService.shutdown();
    }
}

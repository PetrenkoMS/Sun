package main.java;

import java.util.concurrent.*;

import static java.lang.Thread.currentThread;

public class Pool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//            Callable<String> task = () -> currentThread().getName(); //Одна команда, поэтому не нужны {};
//            //Callable аналогичен Runnable, но со встроенным исключением(try/catch). Также возвращает значение => Future
//            ExecutorService service = Executors.newFixedThreadPool(3);
//            for (int i = 0; i < 5; i++) {
//                Future result = service.submit(task); //Результат task (Имя потока); Future - результат действия, который будет выполнен в будущем
//                System.out.println(result.get());
//            }
//            service.shutdown();
// --------------
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2,
//                0L, TimeUnit.SECONDS, new SynchronousQueue()); //SynchronousQueue - очередь в 1 элемент
//        Callable<String> task = () -> Thread.currentThread().getName();
//        threadPoolExecutor.setRejectedExecutionHandler((runnable, executor) -> System.out.println("Ops")); //Устанавливает новый обработчик для неисполнимых задач.
//        for (int i = 0; i < 5; i++) {
//            threadPoolExecutor.submit(task);
//        }
//        threadPoolExecutor.shutdown();
//  --------------
        Object lock = new Object();
        //ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService = Executors.newWorkStealingPool(); //Простаивающие потоки начинают забирать задачи из других потоков или другие задачи
        Callable<String> task = () -> {
            System.out.println(Thread.currentThread().getName());
            lock.wait(2000);
            System.out.println("Finished");
            return "result";
        };
        for (int i = 0; i < 5; i++) {
            executorService.submit(task);
        } //Почему не выводит?
        executorService.shutdown();
    }
}

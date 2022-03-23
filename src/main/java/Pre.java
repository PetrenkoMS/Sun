package main.java;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
//Создание потока наследника и создание потока через implements
//Создание потоков через executor
//Callable
//CyclicBarrier
//вывод с определенным промежутком - ScheduledExecutorService

public class Pre {
    //Этот вариант хуже второго
//    public static void main(String[] args) {
//        System.out.println(Thread.currentThread().getName()); //Поток, в котором он (тут main)
//        MyThread th1 = new MyThread();
//        MyThread th2 = new MyThread();
//        th1.start();
//        th2.start();
//
//    }
//}
//class MyThread extends Thread {  //поток наследник
//    public void run() {
//        System.out.println("Hello from " + this.getName());
//    }
//--------------------------------------------------------------------------
//    public static void main(String[] args) {
//        for (int i = 0; i < 100; i++) {
//            Thread th = new Thread(new MyRunnable());
//            th.start();
//        }
//
//    }
//
//}
//
//class MyRunnable implements Runnable { //реализует интерфейс метода, не наследует напрямую его
//    @Override
//    public void run() {
//        System.out.println("Hello from " + Thread.currentThread().getName());
//    }
//    ----------------
//    public static void main(String[] args) {
//        //ExecutorService exec = Executors.newSingleThreadExecutor(); //1 поток
//        ExecutorService exec = Executors.newFixedThreadPool(15); //несколько потоков
//        for (int i = 0; i <100; i++) {
//            exec.submit(()-> System.out.println("Hello  " + Thread.currentThread().getName()));
//        }
//        exec.shutdown();
//    }
//    --------------
//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        ExecutorService exec = Executors.newFixedThreadPool(15);
//        Callable<String> str = () -> {return "Hello " + Thread.currentThread().getName();}; //как и Runnable, только со встроенным exception и выводит результат
//        List<Future<String>> futures = new ArrayList<>(); //список фьючерсов (используется в Callable, куда добавлять будем
//        for (int i = 0; i < 100; i++) {
//            futures.add(exec.submit(str));
//        }
//        for (Future<String> f: futures)  {
//            System.out.println(f.get());
//        }
//    exec.shutdown();
//    }
//---------------------------------
    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService s_exec = Executors.newScheduledThreadPool(1); //может планировать, выполнять асинхронно -- через какое-то время
        Runnable r = () -> System.out.println("Hello");
        s_exec.schedule(r, 5, TimeUnit.SECONDS); //каждые 5 секунд запускать на выполнение поток r
        s_exec.scheduleWithFixedDelay(r, 5, 1,TimeUnit.SECONDS); //повторяет через секунду
        Thread.sleep(8000);
        s_exec.shutdown();

        ExecutorService exec = Executors.newFixedThreadPool(100);
        Runnable allSet = () -> System.out.println("Все здесь");
        CyclicBarrier bar = new CyclicBarrier(100, allSet); //выведет когда будет 100 потоков (Больше, чем есть потоков - нельзя, иначе зависнет
        Runnable r2 = () -> {
            System.out.println("Thread " + Thread.currentThread());
            try {
                bar.await(); //Уменьшает счетчик циклического барьера. Как будет 0 - закончится, и будет BrokenBarrier
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        };
        for (int i = 0; i < 100; i++) {
            exec.submit(r);
            exec.submit(r2); //В выводе: поток, приоритет (0-10), группа(main)
        }
        exec.shutdown();
    }
}




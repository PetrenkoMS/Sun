//package main.java;



import java.util.concurrent.locks.ReentrantLock;

public class HelloWorldApp {
    public static void main(String[] args) {
        //long i=0;
//        while (true){
//            i+=1;
//        }
        //System.out.println("Hello, " + args[0]);
        //Moon i=new Moon();
        //i.fff('1');
        Thread currentThread = Thread.currentThread();  //Текущий поток
        ThreadGroup threadGroup = currentThread.getThreadGroup();
        System.out.println("Thread: " + currentThread.getName()); //Имя текущего потока
        System.out.println("Thread Group: " + threadGroup.getName()); //Группы
        System.out.println("Parent Group: " + threadGroup.getParent().getName()); //Родителя


        ReentrantLock lock = new ReentrantLock();  //Только 1 поток может использовать блокировку
        int count = 0;


        lock.lock(); //Захват ресурса для данного потока
        try {
            count++;
        } finally { //Выполняется в любом случае
            lock.unlock(); //Разблокировка ресурса
        }
        System.out.println(count);



        Thread th = Thread.currentThread();
        th.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {  //Перехват возникшей ошибки
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("Возникла ошибка: " + e.getMessage());
            }
        });


        try {
            System.out.println(2 / 0);
        }
        catch(ArithmeticException e){  //Арифметическая ошибка
            e.printStackTrace();
            System.out.println(e.getMessage()); //выведет ошибку и ее характер, но продолжет работу, обойдя это исключение
        }
    }


}

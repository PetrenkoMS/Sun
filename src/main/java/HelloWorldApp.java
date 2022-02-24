package main.java;

import java.util.concurrent.locks.ReentrantLock;

class HelloWorldApp {
    public static void main(String[] args) {
        //long i=0;
//        while (true){
//            i+=1;
//        }
        //System.out.println("Hello, " + args[0]);
        //Moon i=new Moon();
        //i.fff('1');
        Thread currentThread = Thread.currentThread();
        ThreadGroup threadGroup = currentThread.getThreadGroup();
        System.out.println("Thread: " + currentThread.getName());
        System.out.println("Thread Group: " + threadGroup.getName());
        System.out.println("Parent Group: " + threadGroup.getParent().getName());


        ReentrantLock lock = new ReentrantLock();
        int count = 0;


        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
        System.out.println(count);



//        Thread th = Thread.currentThread();
//        th.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread t, Throwable e) {
//                System.out.println("Возникла ошибка: " + e.getMessage());
//            }
//        });


//        try {
//            System.out.println(2 / 0);
//        }
//        catch(ArithmeticException e){
//            e.printStackTrace();
//            System.out.println(e.getMessage());
//        }
    }


}

package main.java;

import java.sql.SQLOutput;

public class ThreadLocal {
    public static void main(String[] args) {
        java.lang.ThreadLocal threadLocal = new java.lang.ThreadLocal();
        InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();
        Thread thread1 = new Thread(()-> {
            threadLocal.set("Thread 1");
            inheritableThreadLocal.set("I work!");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String value = (String) threadLocal.get();
            System.out.println(value);
            //threadLocal.remove();
            //value = (String) threadLocal.get();
            //System.out.println(value);
            System.out.println(inheritableThreadLocal.get());

            Thread childThread1 = new Thread(()->{
                System.out.println("Child");
                System.out.println(threadLocal.get());
                System.out.println(inheritableThreadLocal.get());
            });
            childThread1.start();

        });

//        Thread thread2 = new Thread(()-> {
//            threadLocal.set("Thread 2");
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            String value = (String) threadLocal.get();
//            System.out.println(value);
//        });
    thread1.start();
    //thread2.start();
    }
}

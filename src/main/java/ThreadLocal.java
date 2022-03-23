package main.java;

import java.sql.SQLOutput;

public class ThreadLocal {
    public static void main(String[] args) {
        java.lang.ThreadLocal threadLocal = new java.lang.ThreadLocal();
        InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>(); //дочерний потока получает начальные значения для всех наследуемых локальных переменных потока родителя
        Thread thread1 = new Thread(()-> {
            threadLocal.set("Thread 1");  //поток thread1 (текущий)
            inheritableThreadLocal.set("I work!"); //задает значение для inheritableThreadLocal
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String value = (String) threadLocal.get(); //value получает значение threadLocal
            System.out.println(value);
//            threadLocal.remove();  //удаляет текущее значение
//            value = (String) threadLocal.get();
//            System.out.println(value);
            System.out.println(inheritableThreadLocal.get());

            Thread childThread1 = new Thread(()->{  //наследник потока thread1
                System.out.println("Child");
                System.out.println(threadLocal.get()); //уже не видит threadLocal
                System.out.println(inheritableThreadLocal.get()); //Видит
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
//        thread2.start();
    }
}

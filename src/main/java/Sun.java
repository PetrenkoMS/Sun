//  ---------2 части-------------
//Создается поток, наследуемый Thread. Функция факториала.
//Создаем поток через Runnable task = () -> { ... };
//Запуск потока и метод run.
//Synchronized(некоторый объект) -- захват монитора текущим потоком, связанным с данным объектом. Т.о. общего ресурса для нескольких объектов.


//Создаются два потока, один с помощью Runnable, второй - поток наследник Thread-а.
//Выводятся значение из блока synchronize, затем результат run для потока MyThread, пока поток, созданный с помощью Runnable выполняет sleap (1 минуту)
//Во второй части -- два потока захватывают монитор

import java.util.concurrent.Semaphore;
public class Sun {
    public static class MyThread extends Thread {  //Создаем поток MyThread, наследуя класс Thread (поток-наследник)
        @Override  //Что-то переписываем в Thread для MyThread
        public void run() {  //Создаем метод run -- сработает при запуске потока
            Integer c = 2076180480;
            Integer d = fact(c);
            System.out.println(d);
        }
    }


    public static Integer fact(Integer a) {  //Это не факториал, это просто сумма до определенного числа
        Integer f = 1;
        for (int i = 1; i <= a; i++) {
            f = f + 1;
        }
        return f;
    }
//
//    public static void main(String[] args) {
//        Runnable task = () -> {    //Создается поток, через лямбда функцию
//            try {
//                int secToWait = 1000 * 60;  //во втором варианте красивее записано
//                Thread.currentThread().sleep(secToWait);  //Текущий поток засыпает на t мс
//                System.out.println("Waked up");
//            } catch (InterruptedException e) {  //При выведении ошибки InterruptedException -- прерывании потока. (Для исключения ошибок).
//                e.printStackTrace();
//            }
//        }; //Лямбда функция закрывается c помощью ;
//        Thread thread = new Thread(task);  //Создается объект thread класса Thread, который будет делать то, что написано в Runnable
//        thread.start(); //Именно он создает поток
////        Thread thread = new MyThread();
//        Thread thread1 = new MyThread();  //Создается объект thread1 класса MyThread
////        thread.start();
//        thread1.start();
//        Integer f1 = 1;
//        Integer f2 = 1;
//        Integer a = 4;
//        for (int i = 1; i <= a; i++) {
//            f1 = f1 + 1;
//        }
//        //System.out.println(f1);
//        for (int i = 1; i <= a; i++) {
//            f2 = f2 + 1;
//        }
//        //System.out.println(f2);
//        Object object = new Object();    //объект класса object
//        synchronized (object) {   //Текущий поток пытается захватить монитор, связанный с объектом object.
//            System.out.println("Hello World");
//        } //Если надо остановить поток, то не stop(), а interrupt()
//
//    }
//}

// -----------------
//Два потока захватывают монитор. Вначале сработал main, потом другой.

    public static void main(String[] args)  throws InterruptedException {
        Object lock = new Object(); //Создается объект lock

        Runnable task = () -> {
            synchronized (lock) {  //Захват потоком объект lock (но он захвачен пока main, значит будет ждать освобождения)
                System.out.println("thread");
            }
        };

        Thread th1 = new Thread(task);
        th1.start();
        synchronized (lock) {  //текущий поток (main) захватил монитор с объектом lock
            for (int i = 0; i < 8; i++) {
                Thread.currentThread().sleep(1000);
                System.out.print("  " + i); //Раз в секунду выводит число i
            }
            System.out.println(" ...");
        }
    }
}

package main.java;

//Wait(), notify(), notifyAll()


public class Signaling {
    public static void main(String[] args) throws InterruptedException {
        Object object = new Object();
        int a = 0;
        Runnable task1 = () -> {
            synchronized(object) {
                int a1 = 1;
                System.out.println("Старт");
                try {
                    System.out.println("Ждет");
                    object.wait();  //Ждет, пока не будет notify
                    System.out.println("1) Сделает после notify-я");
                    a1= a1 - 1;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(a1);
            }
            System.out.println("2) Сделает после notify-я");
            System.out.println("Финиш");

        };

        Runnable task2 = () -> {
            synchronized(object) {
                System.out.println("Сейчас проснется");
                //object.notify(); //Ждет завершения потока th1 и пробуждает от wait-а
                object.notifyAll(); //Если больше одного wait-а
                System.out.println("Работает дальше");
            }
        };


        Runnable task3 = () -> {
            synchronized(object) {
                System.out.println("2 поток собирается ждать");
                try {
                    object.wait();
                    System.out.println("Проснулся 2-ой поток");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread th1 = new Thread(task1);
        th1.start();
        th1.sleep(5000);
        Thread th3 = new Thread(task3);
        th3.start();
        th1.sleep(6000);
        Thread th2 = new Thread(task2);
        th2.start();


    }
}

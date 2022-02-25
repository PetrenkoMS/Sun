package main.java;

import java.util.concurrent.Semaphore;

public class Semaphores {
//    public static void main(String[] args) throws InterruptedException {
//        Semaphore semaphore = new Semaphore(0);
//        Runnable task = () -> {
//            try {
//                semaphore.acquire();
//                System.out.println("Finished");
//                semaphore.release();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        };
//        new Thread(task).start();
//        Thread.sleep(5000);
//        semaphore.release(1);
//    }
//    ------

    static class Philosopher extends Thread {

        private Semaphore sem;

        // поел ли философ
        private boolean full = false;

        private String name;

        Philosopher(Semaphore sem, String name) {
            this.sem=sem;
            this.name=name;
        }

        public void run()
        {
            try
            {
                // если философ еще не ел
                if (!full) {
                    //Запрашиваем у семафора разрешение на выполнение
                    sem.acquire();
                    System.out.println (name + " садится за стол");

                    // философ ест
                    sleep(300);
                    full = true;

                    System.out.println (name + " поел! Он выходит из-за стола");
                    sem.release();

                    // философ ушел, освободив место другим
                    sleep(300);
                }
            }
            catch(InterruptedException e) {
                System.out.println ("Что-то пошло не так!");
            }
        }
    }

    public static void main(String[] args) {

        Semaphore sem = new Semaphore(2);
        new Philosopher(sem,"Сократ").start();
        new Philosopher(sem,"Платон").start();
        new Philosopher(sem,"Аристотель").start();
        new Philosopher(sem,"Фалес").start();
        new Philosopher(sem,"Пифагор").start();
    }

}

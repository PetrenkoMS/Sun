package main.java;

import java.util.concurrent.Semaphore;

public class Semaphores {
//    public static void main(String[] args) throws InterruptedException {
//        Semaphore semaphore = new Semaphore(0); //Создается семафор с начальным и максимальном значении счетчика = 0, т.е. максимум потоков к ресурсу
//        Runnable task = () -> {
//            try {
//                semaphore.acquire(); //Метод, для получения разрешения у семафора. В (int permits) можно указать кол-ва потоков.
//                System.out.println("Finished");
//                semaphore.release(); //Освобождение от разрешения
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

        private Semaphore sem; //Только в этом классе переменная

        // поел ли философ
        private boolean full = false;

        private String name;

        Philosopher(Semaphore sem, String name) {
            this.sem=sem;   //this - текущий экземпляр этого класса
            this.name=name;
        }

        public Semaphore getSem() {
            return sem;
        }

        public void setSem(Semaphore sem) {
            this.sem = sem;
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

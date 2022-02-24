import java.util.concurrent.Semaphore;
public class Sun {
//    public static class MyThread extends Thread {
//        @Override
//        public void run() {
//            Integer c=2076180480;
//            Integer d=fact(c);
//            System.out.println(d);
//        }
//    }



//    public static Integer fact(Integer a) {
//        Integer f=1;
//        for (int i=1; i<=a; i++){
//            f=f+1;
//        }
//        return f;
//    }
//
//    public static void main(String []args){
//        Runnable task = () -> {
//            try {
//                int secToWait = 1000 * 60;
//                Thread.currentThread().sleep(secToWait);
//                System.out.println("Waked up");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        };
//        Thread thread = new Thread(task);
//        thread.start();
////        Thread thread = new MyThread();
////        Thread thread1 = new MyThread();
////        thread.start();
////        thread1.start();
//        Integer f1=1;
//        Integer f2=1;
//        Integer a=4;
//        for (int i=1; i<=a; i++){
//            f1=f1+1;
//        }
//        System.out.println(f1);
//        for (int i=1; i<=a; i++){
//            f2=f2+1;
//        }
//        System.out.println(f2);
//        Object object = new Object();
//        synchronized(object) {
//            System.out.println("Hello World");
//        }
//    }

    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();

        Runnable task = () -> {
            synchronized (lock) {
                System.out.println("thread");
            }
        };

        Thread th1 = new Thread(task);
        th1.start();
        synchronized (lock) {
            for (int i = 0; i < 8; i++) {
                Thread.currentThread().sleep(1000);
                System.out.print("  " + i);
            }
            System.out.println(" ...");
        }
    }
}

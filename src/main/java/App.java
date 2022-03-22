package main.java;

public class App{
//    public static int value = 0;
//
//    public static void main(String[] args) {
//        Runnable task = () -> {
//            for (int i = 0; i < 10000; i++) {
//                int oldValue = value;
//                int newValue = ++value;
//                if (oldValue + 1 != newValue) {
//                    throw new IllegalStateException(oldValue + " + 1 = " + newValue);
//                }
//            }
//        };
//        new Thread(task).start();
//        new Thread(task).start();
//        new Thread(task).start();
//    }

    public static volatile boolean flag = false;  //Делаем что хотим?

    public static void main(String[] args) throws InterruptedException {
        Runnable whileFlagFalse = () -> {
            while(!flag) {
            }
            System.out.println("Flag is now TRUE");
        };

        new Thread(whileFlagFalse).start();
        Thread.sleep(1000);
        flag = true;
    }
}

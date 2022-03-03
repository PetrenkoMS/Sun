package main.java;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Fork_Join {
    //Факториал
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        CountFactTask task = new CountFactTask(15);
        System.out.println("Result : " + pool.invoke(task));
    }
}

class CountFactTask extends RecursiveTask<Long> {

    private  static  final long serialVersionUID = 1L;
    private long num1;
    private long num2;
    public CountFactTask(long num1, long num2) {
        super();
        this.num1 = num1;
        this.num2 = num2;
    }
    public CountFactTask(long number) {
        this(1,number);
    }

    @Override
    protected Long compute() { //простая ли задача, или надо разбивать?
       if(num1 - num2 <=5) {
           return multiply(num2);
       }
       else {
           long mid = num1 + (num2-num1)/2;
           CountFactTask task1 = new CountFactTask(num1, mid);
           CountFactTask task2 = new CountFactTask(mid+1,num2);
           task1.fork(); //откладывает для рассчетов позже
           long c = task2.compute(); //fork -> compute -> join --- иначе нельзя! зависнет
           return c*task1.join();

       }
    }

    private long multiply(long num) {
        if (num == num1) {
            return num1;
        }
        return num * multiply(num-1);
    }


}

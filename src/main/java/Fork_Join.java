package main.java;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

//Факториал
//ForkJoin разбивает сложную задачу на мелкие, и потоки их выполняют.
//Затем объединяют результат с помощью invoke
//Fork запускает в потоке асинхронную работу
//Join ждет и объединяет
//Потом нужен invoke

public class Fork_Join {

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(); //Создаем пул потоков ForkJoin-а
        CountFactTask task = new CountFactTask(15);  //Потоки для задачи, описан ниже
        System.out.println("Result : " + pool.invoke(task));
    }
}

class CountFactTask extends RecursiveTask<Long> { //наследник класса RecursiveTask.

    private long num1; //Приватные - только в этом классе. Protect - для этого класса и наследников.
    private long num2;
    public CountFactTask(long num1, long num2) { //2 переменные входящие
        //super(); //для того, чтобы можно было обращаться к num1 и num2? Нужен ли?
        this.num1 = num1;  //num1 в этом потоке
        this.num2 = num2;
    }
    public CountFactTask(long number) {
        this(1,number);
    }  //Одно число, для удобства

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


    private long multiply(long num) {  //Факториал (рекурсия)
        if (num == num1) {
            return num1;
        }
        return num * multiply(num-1);
    }


}

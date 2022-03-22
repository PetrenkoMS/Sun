package main.java;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

import static java.lang.Thread.sleep;


public class Lock1 {
    public static void main(String[] args) {
//        ExecutorService executor = Executors.newFixedThreadPool(2); //Создание потоков (здесь фиксированное кол-во = 3) с помощью ExecutorService
//        ReentrantLock lock = new ReentrantLock(); //Только один поток может читать/записывать
//
//        executor.submit(() -> { //submit(Runnable task) -- направляет на выполнение задачу (поток)
//            lock.lock(); //Захват потоком ресурса
//            try {
//                sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } finally {
//                lock.unlock();
//            }
//        });
//
//        executor.submit(() -> { //Второй поток
//            //lock.lock();
//            System.out.println("Locked: " + lock.isLocked()); //Захвачен ли каким-то потоком?
//            System.out.println("Held by me: " + lock.isHeldByCurrentThread()); //Удерживает ли текущий блок этот поток?
//            //System.out.println("Owner: " + lock.getOwner()); //Текущий поток, который захватил ресурс
//            boolean locked = lock.tryLock();  //tryLock - получает блокировку, если не удерживается другим потоком во время вызова
//            System.out.println("Lock acquired: " + locked);
//            //lock.unlock();
//        });
//
//        executor.shutdown(); //Прерывает executor (надо, иначе программа продолжит работу). Сами потоки не прерываются.
//
//        ----------------------
//        ExecutorService executor = Executors.newFixedThreadPool(3);
//        Map<String, String> map = new HashMap<>(); //Map - словарь
//        ReadWriteLock lock = new ReentrantReadWriteLock(); //Локи для чтения неограничены, но для записи только 1. Читать можно, если нет на записи лока.
//
//        executor.submit(() -> {  //первый поток executor-а
//            lock.writeLock().lock(); //блок на запись
//            try {
//                sleep(3000);
//                map.put("key", "value");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } finally {
//                lock.writeLock().unlock();  //Разблокировка на запись
//            }
//        });
//        Runnable readTask = () -> {  //Ниже через executor
//            lock.readLock().lock();  //Лок на чтение
//            try {
//                System.out.println(map.get("key"));
//                sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } finally {
//                lock.readLock().unlock(); //Разблокировка на чтение
//            }
//        };
//
//        executor.submit(readTask); //Так тоже можно
//        executor.submit(readTask); //Читают два потока одновременно
//
//        executor.shutdown();
//---------
        //Как и ReadWriteLock, но возвращает штамп типа "long". Но может быть deadlock.
        //По штампу можно высвобождать ресурсы и блокировки
        //Также есть оптимистичная блокировка -- если ресурс захвачен, не мешает дулать захвать на чтение

        ExecutorService executor = Executors.newFixedThreadPool(3);
        StampedLock lock = new StampedLock();

        executor.submit(() -> {
            long stamp = lock.tryOptimisticRead(); //Оптимистичная блокировка
            try {
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));  //Валидна ли? После захвата становиться валидной
                sleep(1000);
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
                sleep(1000);
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
                System.out.println("long = " + stamp);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock(stamp);
            }
        });

        executor.submit(() -> {
            long stamp = lock.writeLock();
            try {
                System.out.println("Write Lock acquired");
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock(stamp);
                System.out.println("Write done");
            }
        });

        executor.submit(() -> {
            long stamp = lock.readLock();
            try {
                int count = 0;
                if (count == 0) {
                    stamp = lock.tryConvertToWriteLock(stamp); //Может менять с чтения на запись блокировку, ничего не теряя (не освобождая ресурсы)
                    if (stamp == 0L) { //штамп = 0, если ресурс заблокирован для записи
                        System.out.println("Could not convert to write lock");
                        stamp = lock.writeLock();
                    }
                    count = 23;
                }
                System.out.println(count);
            } finally {
                lock.unlock(stamp);
            }
        });


        executor.shutdown();


    }
}

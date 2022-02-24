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
        ExecutorService executor = Executors.newFixedThreadPool(3);
        StampedLock lock = new StampedLock();

        executor.submit(() -> {
            long stamp = lock.tryOptimisticRead();
            try {
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
                sleep(1000);
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
                sleep(2000);
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
                    stamp = lock.tryConvertToWriteLock(stamp);
                    if (stamp == 0L) {
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
//---------
//        ExecutorService executor = Executors.newFixedThreadPool(2);
//        Map<String, String> map = new HashMap<>();
//        ReadWriteLock lock = new ReentrantReadWriteLock();
//
//        executor.submit(() -> {
//            lock.writeLock().lock();
//            try {
//                sleep(1000);
//                map.put("foo", "bar");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } finally {
//                lock.writeLock().unlock();
//            }
//        });
//        Runnable readTask = () -> {
//            lock.readLock().lock();
//            try {
//                System.out.println(map.get("foo"));
//                sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } finally {
//                lock.readLock().unlock();
//            }
//        };
//
//        executor.submit(readTask);
//        executor.submit(readTask);
//
//        executor.shutdown();
//---------
//        ExecutorService executor = Executors.newFixedThreadPool(2);
//        ReentrantLock lock = new ReentrantLock();
//
//        executor.submit(() -> {
//            lock.lock();
//            try {
//                sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } finally {
//                lock.unlock();
//            }
//        });
//
//        executor.submit(() -> {
//            System.out.println("Locked: " + lock.isLocked());
//            System.out.println("Held by me: " + lock.isHeldByCurrentThread());
//            boolean locked = lock.tryLock();
//            System.out.println("Lock acquired: " + locked);
//        });
//
//        executor.shutdown();

    }
}

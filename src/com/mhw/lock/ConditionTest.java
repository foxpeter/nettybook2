/*
 *
 * Copyright (c) 2017-2018 All Rights Reserved.
 */
package com.mhw.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 类说明
 * @author mhw
 * @version ConditionTest.java, 2018/2/4 17:27
 */
public class ConditionTest {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition writeCondition = lock.newCondition();
    private static Condition readCondition = lock.newCondition();
    private static List<String> pool = new ArrayList<>(10);

    static class WriteThread extends Thread{
        public void write() {
            lock.lock();
            if(pool.size() == 10) {
                try {
                    writeCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            pool.add(System.currentTimeMillis()+"");
            System.out.println("add one , current size:" + pool.size());
            readCondition.signalAll();
            lock.unlock();
        }
        @Override
        public void run() {
            while (true) {
                write();
            }
        }
    }

    static class ReadThread extends Thread {
        public void read(){
            lock.lock();
            if(pool.size() == 0) {
                try {
                    readCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pool.remove(0);
            System.out.println("remove one , current size:" + pool.size());
            writeCondition.signalAll();
            lock.unlock();
        }
        @Override
        public void run() {
            while (true) {
                read();
            }
        }
    }

    public static void main(String[] args) {
        WriteThread w1 = new WriteThread();
        w1.start();
        ReadThread r1 = new ReadThread();
        r1.start();

    }
}

/*
 * GuoXiaoMei.com Inc.
 * Copyright (c) 2017-2017 All Rights Reserved.
 */
package com.mhw.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 构造场景 condition 上阻塞两个线程，然后release
 * <p>
 * 1. await() 必须要获得锁  否则抛出 IllegalMonitorStateException ，因为await方法会release锁，release过程中会校验当前线程是否获得了锁
 *
 * @author xushao@guoxiaomei.com
 * @version ConditionSimple.java, 2018/2/7 11:44
 */
public class ConditionSimple {
    public static ReentrantLock lock      = new ReentrantLock();
    public static Condition     condition = lock.newCondition();


    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1; i++) {
            Thread t = new Thread(() -> {
                try {
                    lock.lock();
                    condition.await();
                    System.out.println("Thread : " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
            );
            t.start();
        }
        TimeUnit.SECONDS.sleep(10);
        lock.lock();
        condition.signal();
        lock.unlock();
    }
}

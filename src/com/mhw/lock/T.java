/*
 *
 * Copyright (c) 2017-2018 All Rights Reserved.
 */
package com.mhw.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 类说明
 * @author mhw
 * @version T.java, 2018/2/3 22:45
 */
public class T {
    public static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        for(int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    lock.lock();
                    try {
                        System.out.println("lock");
                        try {
                            Thread.sleep(20000*1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } finally {
                        lock.unlock();
                    }
                }
            }).start();
        }
    }
}

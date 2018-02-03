/*
 * GuoXiaoMei.com Inc.
 * Copyright (c) 2017-2017 All Rights Reserved.
 */
package com.mhw.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 类说明
 *
 * @author xushao@guoxiaomei.com
 * @version ThreadPoolExecutorTest.java, 2018/1/18 19:38
 */
public class ThreadPoolExecutorTest {
    private static int test = 1;

    public static void main(String[] args) {
        /**
         * 前10个，add每一个时会新建线程
         * 11-30，会加到队列里
         * 第31个，会max-core的新线程
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 100, TimeUnit.NANOSECONDS, new ArrayBlockingQueue<Runnable>(3));
        for (int i = 0; i < 50; i++) {
            System.out.println(" === " + i);
            threadPoolExecutor.execute(new Runnable() {
                int index = test++;

                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " --- start " + index);
                    try {
                        TimeUnit.SECONDS.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

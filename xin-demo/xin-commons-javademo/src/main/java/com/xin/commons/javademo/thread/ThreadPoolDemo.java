package com.xin.commons.javademo.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo {

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(8, 16, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(2048), new ThreadPoolExecutor.DiscardPolicy());

        for(int i = 0;i<100;i++){
            executor.execute(new ThreadPoolDemoA("线程--->"+i));
        }

    }
}

class ThreadPoolDemoA implements Runnable{

    private String name;

    public ThreadPoolDemoA(String name){
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(name+"线程执行：线程id为："+Thread.currentThread().getId()+" 线程名称为：" + Thread.currentThread().getName());
    }
}
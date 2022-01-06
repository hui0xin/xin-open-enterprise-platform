package com.xin.commons.javademo.thread;


public class TestThread {

    public static void main(String[] args) {
        System.out.println("主线程ID是： " + Thread.currentThread().getId() + "   主线程名称是： " + Thread.currentThread().getName());
        for(int i = 0;i<1000;i++){
            Thread thread = new MyThread("线程"+i);
            thread.start();
        }
    }
}

/**
 * 线程执行类
 */
class MyThread extends Thread {

    //线程名称
    private String name;

    public MyThread(String name){
        this.name = name;
    }

    @Override
    public void run() {

        System.out.println(name+"线程执行：线程id为："+Thread.currentThread().getId()+" 线程名称为：" + Thread.currentThread().getName());

    }
}

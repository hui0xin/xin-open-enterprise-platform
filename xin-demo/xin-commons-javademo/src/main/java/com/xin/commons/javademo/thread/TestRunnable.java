package com.xin.commons.javademo.thread;

public class TestRunnable {

    public static void main(String[] args) {

        System.out.println("主线程ID是： " + Thread.currentThread().getId() + "   主线程名称是： " + Thread.currentThread().getName());

        for (int i = 0;i<100; i++){
            MyRunnable myRunnable = new MyRunnable("线程---->" +i);
            Thread t = new Thread(myRunnable);
            t.start();
        }
    }
}


class MyRunnable implements Runnable{

    private String name;

    public MyRunnable(String name){
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(name+"线程执行：线程id为："+Thread.currentThread().getId()+" 线程名称为：" + Thread.currentThread().getName());
    }
}

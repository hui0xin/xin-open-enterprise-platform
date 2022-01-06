package com.xin.commons.javademo.thread;

public class ShowMeBug {

    public static void main(String[] args) {
        Thread previousThread = Thread.currentThread();
        for (int i = 0; i < 1000; i++) {
            ThreadJoinDemo threadJoinDemo = new ThreadJoinDemo(previousThread);
            threadJoinDemo.start();
            previousThread = threadJoinDemo;
        }
        System.out.println("主线程执行完毕");
    }
}

class ThreadJoinDemo extends Thread {
    private Thread previousThread;
    public ThreadJoinDemo(Thread thread) {
        this.previousThread = thread;
    }
    public void run() {
        try {
            previousThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String a = Thread.currentThread().getName();
        int b = Integer.parseInt(a.split("Thread-")[1]);
        if(b%2==0){
            System.out.println("--------a");
        }else{
            System.out.println("********************b");
        }

    }


}
package com.company;
import java.util.*;

class theThread implements Runnable {//Runnable接口:用于 1.构造线程 2.驱动任务
    String name;
    Thread t;

    theThread(String thread) {//线程构造函数,需要一个Runnable对象。
        name = thread;
        t = new Thread(this,"线程"+name);
        t.start();//调用 ”Thread对象的start()方法“ 为该线程执行 ”初始化“ 操作。
        System.out.println("New thread: " + t);

    }

    public void run() {//输出函数
        System.out.println("hello" + " " + name);
    }
}

    public class Main {

        public static void main(String[] args) {
            System.out.print("输入所要创建的线程数:");
            Scanner in = new Scanner(System.in);  //输入一个数字,该数字为"希望创建的线程数量"。
            int i = in.nextInt();
            for (int x = 0; x < i; x++) {//用循环生成相应个数的线程
                new theThread(String.valueOf(x));
            }

            try {
                Thread.sleep(1000);//使线程休眠 1s
            } catch (InterruptedException e) {//异常处理（若有异常，则捕捉异常）
                System.out.println("捕获到中断异常"+Thread.interrupted());
            }
            System.out.println("线程结束");//线程结束

        }
    }


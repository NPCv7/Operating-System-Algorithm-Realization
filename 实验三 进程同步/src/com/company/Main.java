package com.company;
import java.util.LinkedList;
import java.util.Scanner;

//wait() / notify()方法是基类Object的两个方法
//wait()方法：当缓冲区已满/空时，生产者/消费者线程停止自己的执行，放弃锁，使自己处于等待状态，让其他线程执行。
//notify()方法：当生产者/消费者向缓冲区放入/取出一个产品时，向其他等待的线程发出可执行的通知，同时放弃锁，使自己处于等待状态。

class Storage
{
    Scanner in=new Scanner(System.in);
    int max=in.nextInt();
    private final int MAX_SIZE =max; // 自定义仓库（缓存区）最大存储量
    private LinkedList<Object> list = new LinkedList<Object>();  // 仓库存储的载体

    public void produce(String producer)                                        // 生产产品函数
    {
        synchronized (list)
        {

            while (list.size() == MAX_SIZE)                                      // 如果仓库已满
            {
                System.out.println("仓库（缓存区）已满，【"+producer+"】： 暂时不能执行生产任务!");
                try
                {
                    list.wait();// 由于条件不满足，生产阻塞
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            list.add(new Object());                                                 // 生产产品
            System.out.println("【"+producer+"】：生产了一个产品\t【仓库（缓存区）剩余空间为】:" + (MAX_SIZE-list.size()));
            list.notifyAll();
        }
    }


    public void consume(String consumer)                                            // 消费产品
    {
        synchronized (list)
        {
            while (list.size()==0)                                                  //如果仓库存储量不足
            {
                System.out.println("仓库（缓存区）已空，【"+consumer+"】： 暂时不能执行消费任务!");
                try
                {
                    list.wait();  // 由于条件不满足，消费阻塞
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            list.remove();
            System.out.println("【"+consumer+"】：消费了一个产品\t【仓库存货（缓存区中个数）为】:" + list.size());
            list.notifyAll();
        }
    }

    public LinkedList<Object> getList()
    {
        return list;
    }

    public void setList(LinkedList<Object> list)
    {
        this.list = list;
    }

    public int getMAX_SIZE()
    {
        return MAX_SIZE;
    }
}


public class Main {
    public static void main(String[] args) {
	// write your code here
        System.out.print("输入仓库（缓冲区）大小：");
        Storage storage=new Storage();//创建并定义缓冲区大小
        System.out.print("输入生产者和消费者的个数：");
        Scanner in=new Scanner(System.in);
        int pdcrs=in.nextInt();            //生产者个数
        int cnsmrs=in.nextInt();           //消费者个数
        for(int i=1;i<=pdcrs;i++)
        {
            final int FinalI=i;    //无法从内部类中访问i，必须要以final的形式
            new Thread(new Runnable() {
                @Override
                public void run() {
                    storage.produce(String.format("生产者%d", FinalI));
                }
            }).start();
        }

        for(int i=1;i<=cnsmrs;i++)
        {
            int finalI = i;         //理由同上
            new Thread(()-> storage.consume(String.format("消费者%d", finalI))).start();
        }
    }
}

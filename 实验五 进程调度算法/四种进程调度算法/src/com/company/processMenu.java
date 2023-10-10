package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class processMenu {
    ArrayList<JCB> jcb;// 存放所有进程（仅用于遍历）
    LinkedList<JCB> link;// 存放已经进入队列的进程（各种算法下的进出队列操作）
    ArrayList<JCB> new_jcb;// 存放按指定调度算法排序后的进程（即保存已经处理后的进程，输出结果，便于观察）
    JCB nowProess;// 当前应执行进程


    public void init() {//初始化
        jcb = new ArrayList<JCB>();
        link = new LinkedList<JCB>();
        new_jcb = new ArrayList<JCB>();
        JCB p1 = new JCB("1", 0, 4,1);
        JCB p2 = new JCB("2", 1, 3,2);
        JCB p3 = new JCB("3", 2, 5,3);
        JCB p4 = new JCB("4", 3, 2,4);
        JCB p5 = new JCB("5", 4, 4,5);
        jcb.add(p1);jcb.add(p2);jcb.add(p3);jcb.add(p4);jcb.add(p5);
        //先将jcb排序，便于下面的算法实现，就不需要再定义一个标识进程是否已到达的boolean,即无需每次都从头开始扫描jcb容器，
        //而是用一个K记录下当前已经扫描到的位置，一次遍历即可，提高了算法效率。
        Collections.sort(jcb, new compareAt_St());
    }


    public void PCA(){//非抢占式
        ProcessQueue pq=new ProcessQueue();
        pq.EnqueueLast();
        while(!link.isEmpty()) {
            //pq.DisplayQueue();//打印当前队列中的进程
            pq.Dequeue();//出队，一次一个
            pq.EnqueueLast();//已到达的进程入队尾
            Collections.sort(link, new compareP());//队列中的进程还需按服务优先级进行排序
        }
    }



    public void FCFS(){//先来先服务算法
        ProcessQueue pq=new ProcessQueue();//调用内部类
        pq.EnqueueLast();//让最先到达的进程先入队
        while(!link.isEmpty()) {//while(new_jcb.size()!=jcb.size())
            //pq.DisplayQueue();//打印当前队列中的进程
            pq.Dequeue();//出队，一次一个
            pq.EnqueueLast();//已到达的进程入队尾
        }
    }


    public void SJF() {// 短作业优先算法
        ProcessQueue pq=new ProcessQueue();
        pq.EnqueueLast();
        while(!link.isEmpty()) {
            //pq.DisplayQueue();//打印当前队列中的进程
            pq.Dequeue();//出队，一次一个
            pq.EnqueueLast();//已到达的进程入队尾
            Collections.sort(link, new compareSt());//队列中的进程还需按服务时间长度进行排序
        }
    }


    public void RR(double sliceTime) {//时间片轮转调度算法
        ProcessQueue pq=new ProcessQueue();
        pq.EnqueueLast();
        while(!link.isEmpty()) {
            //pq.DisplayQueue();//打印当前队列中的进程
            pq.Dequeue(sliceTime);//出队,方法参数表示时间片
        }
    }


    class ProcessQueue{
        int k = 0;// jcb中的进程遍历时的下标
        double nowTime = 0;// 当前时间
        int i=0;//记录当前出入队列的次数

        public void EnqueueLast() {//进程首次入队，可一次进多个,从队尾进入
            while (k < jcb.size()) {//当遍历完jcb中的所有进程时结束
                if (jcb.get(k).arriveTime <= nowTime) {//已经到达的进程按到达时间先后进入队列

                    link.addLast(jcb.get(k));
                    k++;
                } else {
                    break;//如果该进程还未入队，即先结束遍历，保留当前下标k值，注意：此处不要k--；
                }
            }
        }


        public void Dequeue() {//进程出队，一次只出一个
            nowProess = link.removeFirst();//移除队列的队首元素并且返回该对象元素
            System.out.print("-"+nowProess.name);
            nowProess.beginTime = nowTime;//计算开始时间，即为上一个进程的结束时间
            nowProess.finshTime = nowProess.beginTime + nowProess.serveTime;//计算结束时间，该进程开始时间+服务时间
            nowProess.roundTime = nowProess.finshTime - nowProess.arriveTime;//计算周转时间
            nowProess.aveRoundTime = nowProess.roundTime / nowProess.serveTime;//计算带权周转时间
            nowTime = nowProess.finshTime;//获得结束时间，即当前时间，方便判断剩下的进程是否已到达
            new_jcb.add(nowProess);//经处理过数据后加入new_jcb容器
        }


        public void Dequeue(double sliceTime) {//重载Dequeue方法，实现轮转调度算法的出队
            nowProess = link.removeFirst();//移除队列的队首元素并且返回该对象元素
            System.out.print("-"+nowProess.name);
            if(nowProess.firstTimeTag==false) {
                /*轮转调度进程可能会多次反复进出队列，不像FCFS和SJF的进程只会进出一次，所以计算开始时间可以设个标志位，让每个进程在
                 * 第一次执行时记录一遍即可*/
                nowProess.beginTime=nowTime;//进程开始执行的时间
                nowProess.firstTimeTag=true;//计算第一次即可，下次无需更新计算
            }
            if(nowProess.serveTime-nowProess.clock<=sliceTime){//如果当前进程在时间片内运行完，则当前时间累加实际运行时间
                nowTime+=nowProess.serveTime-nowProess.clock;
                nowProess.finshTime=nowTime;//计算该进程完成时间
                nowProess.roundTime = nowProess.finshTime - nowProess.arriveTime;//计算周转时间
                nowProess.aveRoundTime = (double) nowProess.roundTime / nowProess.serveTime;//计算平均周转时间
                new_jcb.add(nowProess);//经处理过数据后加入new_jcb容器
                EnqueueLast();//已到达的进程先入队
            }
            else{
                nowTime+=sliceTime;//否则，当前时间累加一个时间片
                nowProess.clock+=sliceTime;
                EnqueueLast();//已到达的进程先入队
                link.addLast(nowProess);//上一轮出的再紧接着进入队尾
            }
        }

        public void DisplayQueue(){//队列中等候的进程
            i++;
            System.out.println("第"+i+"次队列中排队的进程："+link);
        }
    }

    public void printProcess() {
        double AVGroundTime=0;
        double AVGaveRoundTime=0;
        System.out.println("进程名      到达时间      服务时间       开始时间       完成时间      周1转时间    带权周转时间 ");
        for (int i = 0; i < new_jcb.size(); ++i) {
            AVGroundTime=AVGroundTime+new_jcb.get(i).roundTime;
            AVGaveRoundTime=AVGaveRoundTime+new_jcb.get(i).aveRoundTime;
            System.out.println("P"+new_jcb.get(i).name + "          " + new_jcb.get(i).arriveTime + "          " +
                    new_jcb.get(i).serveTime+ "          " + new_jcb.get(i).beginTime + "          " + new_jcb.get(i).finshTime +
                    "          "+ new_jcb.get(i).roundTime + "          " + new_jcb.get(i).aveRoundTime);
        }
        AVGroundTime=AVGroundTime/5;
        AVGaveRoundTime=AVGaveRoundTime/5;
        System.out.print("平均周转时间："+AVGroundTime+"   "+"平均带权周转时间"+AVGaveRoundTime+"\n");
        new_jcb.clear();//清空new_jcb容器内的内容，方便存储各种算法的结果并展示
    }
}

class compareSt implements Comparator<JCB> {// 按服务时间升序
    public int compare(JCB arg0, JCB arg1) {
        double a=arg0.serveTime - arg1.serveTime;
        if(a>0)
            return 1;
        else
            return -1;
    }
}

class compareP implements Comparator<JCB> {// 按优先级时间升序
    public int compare(JCB arg0, JCB arg1) {
        double a=arg0.priority - arg1.priority;
        if(a>0)
            return 1;
        else
            return -1;
    }
}

class compareAt_St implements Comparator<JCB> {// 按到达时间升序，若到达时间相同，按服务时间升序
    public int compare(JCB o1, JCB o2) {            //对compare函数的重写，主要计算o1到达时间-o2到达时间的正负值，用于collection.sort()函数
        double a = o1.arriveTime - o2.arriveTime;
        if (a > 0)
            return 1;                               //若o1到达时间在o2后，则代表o1排在o2前面
        else if (a == 0){
            return o1.serveTime > o2.serveTime ? 1 : -1;//若到达时间相同，则比较需要被服务的时间
        } else
            return -1;                             //若o1到达时间o2前，则代表o1排在o2后面
    }

}

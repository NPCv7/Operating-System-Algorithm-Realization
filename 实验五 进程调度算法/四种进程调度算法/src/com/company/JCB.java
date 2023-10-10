package com.company;

public class JCB {//进程控制块，记录进程的状态量（动态属性）。PCB是进程存在的唯一标志！！！

    String name;//进程名
    double arriveTime;//到达时间
    double serveTime;//需要被服务的时间
    double beginTime;//开始时间
    double finshTime;//结束时间
    double roundTime;//周转时间
    double aveRoundTime;//带权周转时间
    double priority;
    double clock=0;//在时间轮转调度算法中，记录该进程真实服务时间已经用时的时长
    boolean firstTimeTag=false;//在RR算法中标识开始时间是否第一次计算

    public JCB() {}

    public JCB(String name, double arriveTime, double serveTime,double priority) {
        //super();
        this.name = name;
        this.arriveTime = arriveTime;
        this.serveTime = serveTime;
        this.priority=priority;
    }
    public String toString() {
        String info=new String("进程名：P"+this.name);
        return info;
    }

}

package com.company;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
	// write your code here
        boolean Choose = true;                          //用于判断程序是否继续运行，初始值为true（此处为第一遍）
        String C;                                       //用于接收用户的“y/n（是/否继续运行）”字符指令
        Scanner in=new Scanner(System.in);
        processMenu pm=new processMenu();
        while(Choose==true){
            pm.init();//初始化容器
            System.out.print("请输入要选择的算法:(0-FCFS; 1-SJ(P)F; 2-优先权算法; 3-时间片轮转算法):");
            int select=in.nextInt();
            switch (select){
                case 0:
                    System.out.println("*****************************************************");
                    System.out.println("您选择了FCFS(先来先服务算法),运行结果如下:");
                    System.out.print("该算法的轮转顺序为:");
                    pm.FCFS();
                    System.out.println();
                    pm.printProcess();
                    break;
                case 1:
                    System.out.println("*****************************************************");
                    System.out.println("您选择了SJ(P)F(短作业/短进程优先算法),运行结果如下:");
                    System.out.print("该算法的轮转顺序为:");
                    pm.SJF();
                    System.out.println();
                    pm.printProcess();
                    break;
                case 2:
                    System.out.println("*****************************************************");
                    System.out.print("请进一步选择(0-非抢占优先权算法；1-抢占优先权算法):");
                    int chse=in.nextInt();
                    if(chse==0){
                        System.out.println("您选择了非抢占优先权算法,运行结果如下:");
                        System.out.print("该算法的轮转顺序为:");
                        pm.PCA();
                        System.out.println();
                        pm.printProcess();
                    }
                    break;
                case 3:
                    System.out.println("*****************************************************");
                    System.out.print("请进一步选择时间片大小:Q=1到MAX区间的整数:");
                    double sliceT=in.nextDouble();
                    System.out.println("您选择了时间片轮转算法,时间片为"+sliceT+",运行结果如下:");
                    System.out.print("该算法的轮转顺序为:");
                    pm.RR(sliceT);
                    System.out.println();
                    pm.printProcess();
                    break;
                default:
                    System.out.print("错误字符，请重新输入数字0-3选择算法");
            }
            System.out.println("您是否还要进行算法选择：y/n?");
            C = in.next();
            if(C.endsWith("n")){
                Choose=false;
            }
        }
    }
}







package com.company;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
	// write your code here
        boolean Choose = true;                          //用于判断程序是否继续运行，初始值为true
        String C;                                       //用于接收用户的“y/n（是/否继续运行）”字符指令
        Scanner in = new Scanner(System.in);
        BankerClass T = new BankerClass();
        System.out.println("这是一个五个进程，初始系统可用三类资源为{10,5,7}的银行家算法：");

        T.setSystemVariable();
        while (Choose == true) {
            T.setRequest();                        //此处跳转到BankerClass.java类的setRequest()函数，开始银行家算法的各向量初始化
            System.out.println("您是否还要进行请求：y/n?");
            C = in.nextLine();
            if (C.endsWith("n")) {                            //当输入的字符是n，则结束该程序。若为"y"则继续下一个时刻的运行
                Choose = false;
            }
        }
    }
}

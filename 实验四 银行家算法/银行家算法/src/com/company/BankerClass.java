package com.company;
import java.util.Scanner;

public class BankerClass {
    int[] Available = {10, 5, 7};              //可用资源数{10，5，7}
    int[][] Max = new int[5][3];               //最大需求资源向量
    int[][] Allocation = new int[5][3];         //已分配（拥有）资源数向量
    int[][] Need = new int[5][3];              //还需要资源数向量
    int[][] Request = new int[5][3];           //本次申请的资源量
    int[] Work = new int[3];                   //工作（实时状态资源数）向量（在安全算法中使用）

    int num = 0;//进程编号
    int time=0;//时刻编号（记录如T"0"，T"1"，T"2"...）
    Scanner in = new Scanner(System.in);

    public BankerClass() {}
    public void setSystemVariable(){//设置各初始系统变量，并判断是否处于安全状态。
        setMax();
        setAllocation();
        printSystemVariable();
        SecurityAlgorithm();
    }

    public void setMax() {//设置Max矩阵
        System.out.println("现在设置各进程的最大需求矩阵Max");
        for (int i = 0; i < 5; i++) {
            System.out.print("请输入进程P" + i + "的最大资源需求量：");
            for (int j = 0; j < 3; j++) {
                Max[i][j] = in.nextInt();
            }
        }
    }

    public void setAllocation() {                            //设置已分配矩阵Allocation
        System.out.println("请设置请各进程分配矩阵Allocation");
        for (int i = 0; i < 5; i++) {
            System.out.print("请输入进程P" + i + "的分配资源量：");
            for (int j = 0; j < 3; j++) {
                Allocation[i][j] = in.nextInt();
            }
        }
        System.out.println("Available=Available-Allocation.");
        System.out.println("Need=Max-Allocation.");
        for (int i = 0; i < 3; i++) {//设置Allocation矩阵
            for (int j = 0; j < 5; j++) {
                Available[i] = Available[i] - Allocation[j][i];
            }
        }
        for (int i = 0; i < 5; i++) {//设置Need矩阵
            for (int j = 0; j < 3; j++) {
                Need[i][j] = Max[i][j] - Allocation[i][j];
            }
        }
    }

    public void printSystemVariable(){
        System.out.println("此时资源分配量如下：");
        System.out.println("进程  "+"   Max   "+"   Need "+"    Allocation  "+"     Available ");
        for(int i=0;i<5;i++){
            System.out.print("P"+i+"  ");
            for(int j=0;j<3;j++) {
                System.out.print(Max[i][j] + "  ");
            }
            System.out.print("|  ");
            for(int j=0;j<3;j++){
                System.out.print(Need[i][j]+"  ");
            }
            System.out.print("|  ");
            for(int j=0;j<3;j++){
                System.out.print(Allocation[i][j]+"  ");
            }
            System.out.print("|  ");
            if(i==0){
                for(int j=0;j<3;j++){
                    System.out.print(Available[j]+"  ");
                }
            }
            System.out.println();
        }
    }

    public void setRequest() {//设置请求资源量Request                              <---主函数入口


        System.out.print("请输入需要申请资源的进程号（0-4）：");
        num= in.nextInt();//设置全局变量进程编号num
        System.out.print("请输入需要申请的资源数：");
        for (int j = 0; j < 3; j++) {
            Request[num][j] = in.nextInt();
        }
        System.out.println("即进程P" + num + "对各资源请求Request：(" + Request[num][0] + "," + Request[num][1] + "," + Request[num][2] + ").");

        BankerAlgorithm();
    }

    public void BankerAlgorithm() {//银行家算法
        boolean T=true;
        if (Request[num][0] <= Need[num][0] && Request[num][1] <= Need[num][1] && Request[num][2] <= Need[num][2]) {//判断Request是否小于Need
            if (Request[num][0] <= Available[0] && Request[num][1] <= Available[1] && Request[num][2] <= Available[2]) {//判断Request是否小于Available
                for (int i = 0; i < 3; i++) {
                    Available[i] -= Request[num][i];
                    Allocation[num][i] += Request[num][i];
                    Need[num][i] -= Request[num][i];
                }
            } else {
                System.out.println(" 您对于进程P" + num + "的要求暂时无法满足，导致BLOCK，请重新输入~");
                T=false;
            }
        } else {
            System.out.println("您的要求超出了需要范围，导致进程P" + num + " ERROR，请重新输入！");
            T=false;
        }
        if(T==true){
            printSystemVariable();
            System.out.println("现在进入安全算法：");
            SecurityAlgorithm();
        }
    }


    public void SecurityAlgorithm() {//安全算法
        boolean[] Finish = {false, false, false,false,false};//初始化Finish
        int count = 0;//完成进程数
        int circle=0;//循环圈数
        int[] S=new int[5];//安全序列
        for (int i = 0; i < 3; i++) {//设置工作向量
            Work[i] = Available[i];
        }
        boolean flag = true;
        while (count < 5) {
            if(flag){
                System.out.println("进程  "+"   Work  "+"   Allocation "+"    Need  "+"     Work+Allocation "+"     Finish ");
                flag = false;
            }
            for (int i = 0; i < 5; i++) {

                if (Finish[i]==false&&Need[i][0]<=Work[0]&&Need[i][1]<=Work[1]&&Need[i][2]<=Work[2]) {//判断条件
                    System.out.print("P"+i+"  ");
                    for (int k = 0; k < 3; k++){
                        System.out.print(Work[k]+"  ");
                    }
                    System.out.print("|  ");
                    for (int j = 0; j<3;j++){
                        Work[j]+=Allocation[i][j];
                    }
                    Finish[i]=true;                                      //当当前进程能满足时
                    S[count]=i;                                           //设置当前安全序列排号

                    count++;//满足进程数加1
                    for(int j=0;j<3;j++){
                        System.out.print(Allocation[i][j]+"  ");
                    }
                    System.out.print("|  ");
                    for(int j=0;j<3;j++){
                        System.out.print(Need[i][j]+"  ");
                    }
                    System.out.print("|  ");
                    for(int j=0;j<3;j++){
                        System.out.print(Work[j]+"  ");
                    }
                    System.out.print("      ");
                    System.out.print("|  ");
                    System.out.print(Finish[i]);
                    System.out.println();
                }

            }
            circle++;//循环圈数加1

            if(count==5){//判断是否满足所有进程需要
                System.out.print("T"+time+"时刻安全，存在一个安全序列 "); //因为可能有很多个安全序列，所以这里写“一个”
                for (int i = 0; i<5;i++){                            //输出安全序列
                if(i!=0)
                    System.out.print(">"+"P"+S[i]);
                else System.out.print("P"+S[i]);
                }
                System.out.println(" 故当前可分配！");
                time++;
                break;                                                  //跳出循环
            }
            if(count<circle){                                   //判断完成进程数是否小于循环圈数
                count=5;                                        //使其不满足循环条件（count>=5）
                System.out.println("未找到安全序列，继续分配将导致系统进入不安全状态，进而可能引发死锁，不予分配资源！！");
                for (int i = 0; i < 3; i++) {                     //若安全算法发现处于不安全状态，则归还银行家算法中的request资源
                    Available[i] += Request[num][i];
                    Allocation[num][i] -= Request[num][i];
                    Need[num][i] += Request[num][i];
                }
                break;                                                            //跳出循环
            }
        }
    }

}

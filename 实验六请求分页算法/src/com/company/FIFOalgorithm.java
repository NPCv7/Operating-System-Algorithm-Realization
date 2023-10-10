package com.company;

public class FIFOalgorithm {
    private String order;             //页面访问顺序顺序（String字符串）
    private int num;                  //物理块数量
    private int len;                  //由基类Javabean继承
    private char Output[][];          //输出矩阵，对应序列中每个页面的情况
    private char buffer[];            //缓冲区（内存）
    private char Order[];             //页面访问顺序字符型数组
    private int k = 0;                //当前buffer中的页面数
    private int cnt = 0;              //缺页数统计
    private double result;            //缺页率
    static int aa = 0;

    public FIFOalgorithm(JavaBean javaBean)
    {
        len = javaBean.len;
        num = javaBean.num;
        order = javaBean.order;
        Order = new char[len];
        Output = new char[len][num+2];
        buffer = new char[num];
        Islegal.OutputInit(Output,Order,order,len,num);//对输出矩阵初始化
    }

    public void Handle()
    {
        System.out.println("order:"+order);
        System.out.println("num:"+num);
        for (int i = 0; i < len; i++)//遍历每一个页面访问序列中的页面
        {
            char t = Output[i][0];
            if(k < num)//如果buffer中页面数少于物理块数量
            {
                if(Islegal.Isexists(buffer,t)){//如果buffer中存在要访问的页面
                    programmeF(t,i);
                }
                else{                 //如果不存在
                    buffer[k++] = t;
                    programmeT(t,i);
                }
            }
            else//如果buffer中物理块都有页面
            {
                if(Islegal.Isexists(buffer,t)){     //如果要访问的页面号在缓冲区中，在缺页中断那一栏打上'F'
                    programmeF(t,i);
                }
                else{
                    int index = Islegal.Maxm(Output,i,num,buffer,len);//寻找当前buffer中最早进来的页面数字下标，赋给index
                    buffer[index] = t;              //用t替换内存中index位置的页面（最早进的页面）
                    programmeT(t,i);
                }
            }
        }
        result = 1.0*cnt/len;
    }

    private void programmeT(char t, int i)//
    {
        for (int j = 1;j <= k; j++)
        {
            Output[i][j] = buffer[j-1];
        }
        Output[i][num+1] = 'T';
        cnt++;
    }

    private void programmeF(char t, int i)
    {
        for (int j = 1;j <= k; j++)
        {
            Output[i][j] = buffer[j-1];
        }
        Output[i][num+1] = 'F';
    }

    public void display()
    {

        System.out.println("您选择了FIFO算法，执行结果如下：");
        System.out.println("访问页面        物 理 块              缺页中断");
        for (int i = 0; i < len; i++)
        {
            for (int j = 0; j < num+2; j++)
                System.out.print(Output[i][j]+"        ");
            System.out.println();
        }

        System.out.printf("页面访问序列总长%d，FIFO算法共发生缺页中断%d次，缺页率为%.2f%%\n",len,cnt,result*100);
    }

}

package com.company;

public class LRUalgorithm {
    private String order;             //访问页面顺序
    private int num;                  //访问页面序列长度
    private int len;                  //物理块数量
    private char Output[][];          //输出矩阵，即访问每个页面时缓冲区的状态
    private char buffer[];            //缓冲区，相当于内存
    private char Order[];             //访问页面序列
    private int k = 0;                //对应当前buffer[]的指针，指向第一个空的位置
    private int cnt = 0;              //缺页数统计
    private double result;            //缺页率

    public LRUalgorithm(JavaBean javaBean)
    {
        len = javaBean.len;
        num = javaBean.num;
        order = javaBean.order;
        Order = new char[len];
        Output = new char[len][num+2];
        buffer = new char[num];
        Islegal.OutputInit(Output,Order,order,len,num);
    }

    public void Handle()                     //作用同OPT的handle
    {
        System.out.println("order:"+order);
        System.out.println("num:"+num);
        for (int i = 0; i < len ; i++)
        {
            char t = Output[i][0];         //访问第i个被访问的页面
            if(k < num)                   //如果buffer中页数小于物理块号
            {
                if(Islegal.Isexists(buffer,t)){
                    programmeF(t,i);
                }
                else{
                    buffer[k++] = t;
                    programmeT(t,i);
                }
            }
            else{
                if(Islegal.Isexists(buffer,t)){
                    programmeF(t,i);
                }
                else
                {
                    int index = Islegal.FindTheLongest(Order,buffer,i,num);//将时间最长的代码块处于buffer内存中的位置赋值给index
                    buffer[index] = t;                                 //将新页面t取代原来index位置的页面
                    programmeT(t,i);
                }
            }
        }

        result = 1.0*cnt/len;
    }


    public void programmeT(char t, int i)
    {
        for (int j = 1;j <= k; j++)
        {
            Output[i][j] = buffer[j-1];
        }
        Output[i][num+1] = 'T';
        cnt++;
    }
    public void programmeF(char t, int i)
    {
        for (int j = 1;j <= k; j++)
        {
            Output[i][j] = buffer[j-1];
        }
        Output[i][num+1] = 'F';
    }

    public void display()
    {
        System.out.println("您选择了LRU算法，执行结果如下：");
        System.out.println("访问页面        物 理 块              缺页中断");
        for (int i = 0; i < len; i++)
        {
            for (int j = 0; j < num+2; j++)
                System.out.print(Output[i][j]+"        ");
            System.out.println();
        }

        System.out.printf("页面访问序列总长%d，LRU算法共发生缺页中断%d次，缺页率为%.2f%%\n",len,cnt,result*100);
    }

}

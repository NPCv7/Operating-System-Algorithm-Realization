package com.company;

public class OPTalgorithm {
    private String order;            //访问页面顺序
    private int num;                 //访问页面序列长度
    private int len;                 //物理块数量
    private char Order[];            //访问页面序列
    private char Output[][];         //输出矩阵，即访问每个页面时缓冲区的状态
    private int cnt = 0;             //计算缺页中断的次数
    private char buffer[];           //缓冲区，相当于内存
    private int k = 0;               //对应当前buffer[]的指针，指向第一个空的位置
    private double result;           //缺页率

    public OPTalgorithm(JavaBean javaBean)
    {
        len = javaBean.len;
        num = javaBean.num;
        order = javaBean.order;
        Order = new char[len];
        Output = new char[len][num+2];
        buffer = new char[num];
        Islegal.OutputInit(Output,Order,order,len,num);     //初始化Output
    }

    public void Handle()
    {
        System.out.println("order:"+order);
        System.out.println("num:"+num);
        for (int i = 0; i < len; i++)
        {
            char t = Output[i][0];
            if(k < num)      //buffer未满
            {
                if(Islegal.Isexists(buffer,t)){      //若buffer中存在指定页面，就不用缺页中断，在最后输出F
                    programmeF(t,i);
                }
                else{                              //不存在，则需要缺页中断，在buffer中加上这一页，在最后输出T
                    buffer[k++] = t;
                    programmeT(t,i);
                }
            }
            else                     //buffer满了
            {
                if(Islegal.Isexists(buffer,t)){     //存在
                    programmeF(t,i);
                }
                else{                           //不存在
                    int index = Islegal.FindMaxId(Order,buffer,i);
                    buffer[index] = t;
                    programmeT(t,i);
                }
            }
        }
        result = 1.0*cnt/len;              //计算缺页率
    }

    private void programmeT(char t, int i)
    {
        for (int j = 1;j <= k; j++)       //将buffer中的内容输入到当前的页面对应物理块状态
        {
            Output[i][j] = buffer[j-1];
        }
        Output[i][num+1] = 'T';                //在对应页面的对应输出矩阵最后加上需要缺页中断'T'
        cnt++;                                 //缺页中断数+1
    }

    private void programmeF(char t, int i)
    {
        for (int j = 1;j <= k; j++)           //将buffer中的内容输入到当前的页面对应物理块状态
        {
            Output[i][j] = buffer[j-1];
        }
        Output[i][num+1] = 'F';                  //在对应页面的对应输出矩阵最后加上不需要缺页中断'F'

    }

    public void display()
    {
        System.out.println("您选择了OPT算法，执行结果如下：");
        System.out.println("访问页面        物 理 块              缺页中断");
        for (int i = 0; i < len; i++)
        {
            for (int j = 0; j < num+2; j++)
                System.out.print(Output[i][j]+"        ");
            System.out.println();
        }
        System.out.printf("页面访问序列总长%d，OPT算法共发生缺页中断%d次，缺页率为%.2f%%\n",len,cnt,result*100);
    }


}

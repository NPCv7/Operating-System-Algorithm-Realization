package com.company;

public class CLOCKalgorithm {
    private String order;            //页面访问序列（String字符串）
    private int num;                 //物理块数量
    private int len;                 //页面访问序列长度                //以上三项JavaBean基类都有
    private char Output[][];         //输出矩阵
    private char buffer[];           //当前缓冲区
    private int Auxiliary1[];          //访问位记录数组
    private char Auxiliary2[][];        //缺页中断标记页
    private char Order[];              //页面访问序列（字符型数组）
    private int k = 0;                 //当前buffer内存中的内存块数
    private int cnt = 0;               //当前缺页中断数
    private double result;             //缺页率
    private int P = 0;

    public CLOCKalgorithm(JavaBean javaBean) //CLOCKalgorithm对象构造函数
    {
        len = javaBean.len;
        num = javaBean.num;
        order = javaBean.order;
        buffer = new char[num];    //缓冲区——内存（大小和物理块数一样）
        Auxiliary1 = new int[num];  //访问位记录数组，记录每个物理块中页面的访问位状态
        Auxiliary2 = new char[len][num];//
        Order = new char[len];           //
        Output = new char[len][num+2];
        for (int i = 0; i < num; i++)    //每个物理块号访问位设为默认值-1
            Auxiliary1[i] = -1;
        for(int i = 0; i < len; i++)    //将辅助队列2中每次访问页面时缓存区里的物理块内容设为" "
            for(int j = 0; j < num; j++)
                Auxiliary2[i][j] = ' ';
        Islegal.OutputInit(Output,Order,order,len,num);     //初始化Output
    }

    public void Handle()
    {
        System.out.println("order:"+order);
        System.out.println("num:"+num);
        for (int i = 0; i < len; i++){
            char t = Output[i][0];
            if(k < num){//缓冲区内页面数量<物理块数量
                if(Islegal.Isexists(buffer,t)){             //如果在缓冲区里存在这个页面
                    P = Islegal.FindId(buffer,t);            //在内存（缓冲区）内找到要访问的页面下标，赋值给P
                    Auxiliary1[P] = 1;                      //表示找到该页面，将其访问位从-1改为1
                    programmeF(t,i);                         //将缺页中断打上标志'F'
                    AdjustAuxiliary2(i);                    //将对应的buffer中的页面访问位对应打上’*‘，方便之后的输出
                }
                else{//缓冲区里没这个页面
                    buffer[k] = t;//将该页面放入缓存区
                    Auxiliary1[k] = 1;//修改其访问位为1
                    k++;
                    P = (P+1)%num;  //维护循环队列Auxiliary1,此时队列大小为k.
                    programmeT(t,i);
                    AdjustAuxiliary2(i);
                }
            }
            else
            {
                if(Islegal.Isexists(buffer,t)){     //要访问的字符在缓冲区中
                    P = Islegal.FindId(buffer,t);
                    Auxiliary1[P] = 1;
                    programmeF(t,i);
                    AdjustAuxiliary2(i);
                }
                else
                {
                    while(Auxiliary1[P] == 1){
                        Auxiliary1[P] = 0;
                        P = (P+1)%num;
                    }
                    buffer[P] = t;
                    Auxiliary1[P] = 1;
                    P = (P+1)%num;
                    programmeT(t,i);
                    AdjustAuxiliary2(i);
                }
            }
        }
        result = cnt*1.0/len;
    }

    private void programmeT(char t, int i)
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
        System.out.println("您选择了CLOCK算法，执行结果如下：");
        System.out.println("访问页面        物理块          缺页中断");
        for (int i = 0; i < len; i++)
        {
            System.out.print(Output[i][0]+"       ");
            for (int j = 1; j < num+1; j++)
                System.out.printf("%c%c     ",Output[i][j],Auxiliary2[i][j-1]);
            System.out.println(Output[i][num+1]);
        }

        System.out.printf("页面访问序列总长%d，FIFO算法共发生缺页中断%d次，缺页率为%.2f%%\n",len,cnt,result*100);
    }

    public void AdjustAuxiliary2(int index)     //将访问位为1的模块打上"*"
    {
        for (int i = 0; i < num; i++)
        {
            if(Auxiliary1[i] == 1){
                Auxiliary2[index][i] = '*';
            }

        }
    }

}

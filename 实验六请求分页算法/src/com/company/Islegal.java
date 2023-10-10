package com.company;


public class Islegal {                      //检验函数集
    static boolean test(String st)
    {
        char arr[];                        //字符数组，用于核对数列中是否存在非法输入
        arr = st.toCharArray();
        for(int i = 0; i < arr.length; i++)
        {
            if(arr[i]< '0' || arr[i] > '5')
            {
                return true;
            }
        }
        return false;
    }

    static boolean Isexists(char buffer[], char ch)//用于搜索缓冲区中是否存在目标页
    {
        for (int i = 0; i < buffer.length;i++)
        {
            if(buffer[i] == ch)
            {
                return true;
            }
        }
        return false;
    }

    public static  boolean Find(char Order[],char ch, int now)//用于寻找页面访问序列中从指定位置开始到最后，是否存在指定页面
    {
        for (int i = now+1; i< Order.length; i++)//从当前页面访问序列位置往后找，看是否存在相同页面，若有返回true
        {
            if(Order[i] == ch)
                return true;
        }
        return false;
    }

    static int FindMaxId(char Order[],char buffer[],int begin)
    {
        int u = 0,dis = -1;
        for (int i = 0; i< buffer.length; i++)
        {
            char t = buffer[i];
            if(!Find(Order,t,i))        //从i+1位置开始往后，没不存在相同的页号
            {
                for (int k = 0; k < buffer.length; k++)//查找该页号的第一次出现的下标
                    if(t == buffer[k])
                        return k;
            }
            else//若从i+1开始往后存在相同页号
            {
                for (int j = begin; j < Order.length; j++)
                {
                    if(t == Order[j]){
                        if(j >= dis){
                            u = i;
                            dis = j;
                        }
                        break;
                    }
                }
            }
        }
        return u;
    }


    static int Maxm(char Output[][],int now,int num,char buffer[], int len)//寻找当前buffer中最早进来的页面数字下标
    {
        int tempbuffer[] = new int [len];
        for (int i = 0;i < tempbuffer.length;i++)//初始化tempbuffer
            tempbuffer[i] = 0;
        for (int i = 1; i < num+1; i++)     //寻找当前buffer中最早进来的页面数字下标
        {
            char t = Output[now][i];
            for (int j = now; j >= 0; j--)
            {
                if(Output[j][i] == Output[j-1][i])
                {
                    tempbuffer[t-'0']++;
                }
                else
                    break;
            }
        }
        int u = maxx(tempbuffer);
        char ch = (char)(u+'0');

        for (int i = 0; i < num; i++)
        {
            if(buffer[i] == ch)
                return i;
        }
        return 0;
    }

    static void OutputInit(char Output[][], char Order[], String order, int len, int num)//初始化每个算法的Output矩阵
    {
        Order = order.toCharArray();
        for (int i = 0; i < len; i++)
        {
            for (int j = 0; j < num+2; j++)
            {
                Output[i][j] = '&';
            }
            Output[i][0] = Order[i];            //将每个输出矩阵的第一位都设为要访问的页面
        }
    }

    static int FindTheLongest(char Order[],char buffer[], int now, int num)//将时间最长的代码块处于buffer内存中的位置下标
    {
        int tempbuff[] = new int[Order.length];       //创建一个长度为序列
        for(int i = 0; i < tempbuff.length;i++)       //将临时缓冲区每位对应数字设定为0
            tempbuff[i] = 0;

        for(int i = 0; i < buffer.length; i++)
        {
            char t = buffer[i];              //依此遍历当前缓冲区中的所有物理块
            for (int j = now-1; j>= 0; j--) //检索now之前的物理块
            {
                if(Order[j] != t)        //检查每个模块距离其上一次被访问距离多少，将其结果存在tempbuff中
                {
                    tempbuff[t-'0']++;
                }
                else
                    break;
            }
        }
        int u = maxx(tempbuff);         //找到未被访问时间最长的页面，将其值复制给u
        char ch = (char)(u+'0');
        for (int i = 0; i < num; i++)   //找到在当前buffer内存中的该数位置，并返回其值
        {
            if(buffer[i] == ch)
                return i;
        }
        return 0;
    }

    public static int maxx(int tempbuffer[])      //找到距离最远的页号的第一次出现位置
    {
        int u = -1,maxm = -1;
        for (int i = 0; i < tempbuffer.length; i++)
        {
            if(tempbuffer[i] != 0 && tempbuffer[i] > maxm)//找到页号最大的数
            {                                             //这里的tempbuffer[i]!=0主要是为了配合上方的FindtheLongest()函数，因为其在初始化时全部设为0
                u = i;
                maxm = tempbuffer[i];
            }
        }
        return u;
    }

    static int FindId(char buffer[], char t)  //查找缓存区中指定页面的位置
    {
        for(int i = 0; i < buffer.length; i++)
        {
            if(buffer[i] == t)
                return i;
        }
        return -1;
    }

}

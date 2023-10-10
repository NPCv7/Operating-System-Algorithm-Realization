package com.company;

import java.util.Scanner;

public class JavaBean {                          //四种算法的基类对象
    public int len; //页面访问序列长度
    public int num;//物理块个数
    public String order;//用于储存页面访问序列（String形式）
    public char Order[] = new char[len];

    Scanner in = new Scanner(System.in);
    MyRandom myRandom = new MyRandom(); //用于生成随机数的对象


    public int getLen(){return this.len;};
    public void setLen(int len){this.len = len;};
    public int getNum(){return this.num;};
    public void setNum(int num){this.num = num;}
    public String getOrder(){return this.order;};
    public void setOrder(String order) {this.order = order;};

    public void init()//初始化
    {

        System.out.println("请输入页面访问序列长度(15-25，含端点):");
        len = in.nextInt();
        while(len < 15 || len > 25)
        {
            System.out.println("您输入的页面访问序列长度超出给定范围，请重新输入15-25(含端点)的数字:");
            len = in.nextInt();
        }
        System.out.println("请输入物理块个数(3-5，含端点):");
        num = in.nextInt();
        while(num < 3 || num > 5)
        {
            System.out.println("您输入的物理块个数超出给定范围，请重新输入3-5(含端点)的数字:");
            num = in.nextInt();
        }
        System.out.println("请选择页面访问序列生成方式: 1、用户输入；2、随机生成");
        int type;  //储存下面用户选择的页面访问序列生成方式
        type = in.nextInt();
        while(type < 1 || type > 2)
        {
            System.out.println("type="+type);
            System.out.println("您选择的方式无效，请重新输入:");
            type = in.nextInt();
        }
        switch(type)
        {
            case 1:
            {
                order = Manualinput();             //用户手动输入序列
            }
            break;
            case 2:
            {
                order = Randomgenerate();         //自动生成序列
            }
            break;
        }
    }

    private String Manualinput()
    {
        System.out.println("您选择了手动输入方式:");
        System.out.printf("请输入0-5数字组成的无规律字符串作为页面访问序列，长度为%d\n",len);
        String st;            //第一次输入序列（如果符合格式，则为最终序列Finalst）
        String Finalst = "";  //用于存最终的序列


        while(true)
        {
            st = in.next();
            System.out.println("您输入的顺序为:" + st);
            if(Islegal.test(st))//检测是否有不合法的输入
            {
                System.out.printf("您输入的序列有误，请重新输入0-5数字组成的无规律字符串作为页面访问序列，长度为%d\n",len);
                continue;
            }

            if(st.length() < len)       //检测长度问题1:没到设定长度
            {
                System.out.printf("您已输入%d长度的字符串，还需输入%d长度的字符串\n",st.length(),len-st.length());
                System.out.printf("请继续输入0-5数字组成的无规律字符串作为页面访问序列，长度为:%d\n",len-st.length());
                String st2;     //补全余下字符串，假设第二次输入字符串合法，并且数量足够
                st2 = in.next();
                Finalst = st+st2;
                break;
            }
            else if (st.length() > len)  //问题2:超过设定长度
            {
                System.out.println("您输入的字符串超长，系统将进行自动截取Y或由用户重新输入N（Y/N）");
                char ch;
                ch = in.next().charAt(0);
                if(ch == 'N')
                {
                    System.out.println("您选择了重新输入:");
                    continue;
                }
                else if(ch == 'Y')
                {
                    char arr[];
                    arr = st.toCharArray();
                    for(int i = 0; i < len; i++)  //自动截取直到Finalst到达
                        Finalst += arr[i];
                    break;
                }
            }
            else
                break;
        }
        return Finalst;
    }



    private String Randomgenerate()
    {
        System.out.println("您选择了随机生成方式");
        return myRandom.Randomgenerate(len);
    }


}

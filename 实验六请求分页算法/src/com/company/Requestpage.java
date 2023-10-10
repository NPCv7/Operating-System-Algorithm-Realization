package com.company;

import java.util.Scanner;

public class Requestpage {               //用户操作页面——显示（当前用户设定的页面访问序列+用户设定的物理块个数）+选择算法页面（1、OPT；2、FIFO；3、LRU；4、CLOCK）
    Scanner in = new Scanner(System.in);
    JavaBean javaBean = new JavaBean();

    public void display()
    {
        javaBean.init();
        System.out.println("页面访问序列为:"+javaBean.order);
        System.out.println("物理块个数为:"+javaBean.num);
    }

    public void Handle()
    {

        System.out.println("请选择要执行的页面选择算法:1、OPT；2、FIFO；3、LRU；4、CLOCK");
        while(true)
        {
            int type;
            type = in.nextInt();
            if(type < 1 || type > 4){
                System.out.println("请选择正确的算法");
                continue;
            }
            switch (type)
            {
                case 1:
                {
                    System.out.println("您选择了OPT算法");
                    OPTalgorithm opTalgorithm = new OPTalgorithm(javaBean);
                    opTalgorithm.Handle();
                    opTalgorithm.display();
                }
                break;
                case 2:
                {
                    System.out.println("您选了FIFO算法");
                    FIFOalgorithm fifOalgorithm = new FIFOalgorithm(javaBean);
                    fifOalgorithm.Handle();
                    fifOalgorithm.display();
                }
                break;
                case 3:
                {
                    System.out.println("您选了LRU算法");
                    LRUalgorithm lrUalgorithm = new LRUalgorithm(javaBean);
                    lrUalgorithm.Handle();
                    lrUalgorithm.display();
                }
                break;
                case 4:
                {
                    System.out.println("您选了CLOCK算法");
                    CLOCKalgorithm clocKalgorithm = new CLOCKalgorithm(javaBean);
                    clocKalgorithm.Handle();
                    clocKalgorithm.display();

                }
                break;

            }
            System.out.println("您是否继续尝试其他算法Y/N");
            char ch = in.next().charAt(0);
            if(ch == 'Y')
            {
                System.out.println("您选择了尝试其他算法");
                System.out.println("请选择要执行的页面选择算法:1、OPT；2、FIFO；3、LRU；4、CLOCK");
                continue;
            }
            else
                break;
        }
    }

}

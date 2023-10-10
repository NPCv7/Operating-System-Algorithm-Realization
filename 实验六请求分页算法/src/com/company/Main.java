package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Scanner in = new Scanner(System.in);
        while(true)
        {
            Requestpage requestpage = new Requestpage();
            requestpage.display();
            requestpage.Handle();
            System.out.println("您是否要更新数据Y、更换数据；N、结束程序");
            char ch = in.next().charAt(0);
            if(ch == 'Y')
            {
                System.out.println("您选择了更新数据");
                continue;
            }
            else
            {
                System.out.println("您选择了结束程序");
                break;
            }
        }


    }
}

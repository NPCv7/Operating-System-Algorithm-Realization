package com.company;

import java.util.Random;

public class MyRandom {
    private Random random = new Random();

    public String Randomgenerate(int len)
    {
        String st ="";
        for (int i = 0; i < len; i++)
        {
            try {
                Thread.sleep(18);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            st += (char)(Math.abs(random.nextInt()%6)+'0');
        }

        return st;
    }

}

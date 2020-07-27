package com.atguigu.springcloud.opp;


public class DiGui {

    public static void main(String[] args) {
        int sum = sum(100);
        System.out.println(sum);
    }

    //100以内数求和
    public static int sum(int n) {
        if (n==1){
            return 1;
        }else {
            return n+sum(n-1);
        }
    }
}

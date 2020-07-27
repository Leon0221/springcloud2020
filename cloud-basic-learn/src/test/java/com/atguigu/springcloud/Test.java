package com.atguigu.springcloud;


public class Test {
    public static void main(String[] args) {

//    }
//
//    public static void test1(){
        //输出100以内的质数，只能被1和他本身整除的数
        //立flag
        boolean flag = true;
        for (int i = 2; i <= 100; i++) {

            for (int j = 0; j < i; j++) {

                if (i%j==0) {
                    //能除尽
                    flag = false;
                    break;
                }
                //if循环完i-1个都没有，则他就为质数
                System.out.println(i);
            }
            flag = true;//将flag重新制成true，进行下一个i
        }

    }
}

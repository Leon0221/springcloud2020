package com.atguigu.springcloud;

import cn.hutool.core.math.MathUtil;

import java.util.Scanner;
import java.util.stream.IntStream;


public class Test {
    public static void main(String[] args) {
        //test1();//这种情况不包括最后一个数是质数的情况
       // test2(); //这种情况都能包括而且数度很快
       // test3();
        //test4();
        //test6();
        //test7();
        //test8();
        //test9();
        test10();
    }

    public static void test1() {
        //输出100以内的质数，只能被1和他本身整除的数
        //立flag
        boolean flag = true;
        for (int i = 2; i <= 100; i++) {

            for (int j = 2; j < i; j++) {

                if (i % j == 0) {
                    //能除尽
                    flag = false;
                    break;
                }

            }
            if (flag) {
                //if循环完i-1个都没有，则他就为质数
                System.out.println(i);
            }
            flag = true;//将flag重新制成true，进行下一个i
        }

    }

    public static void test2(){
        //立 flag
        boolean flag = true;
        for (int i = 2; i <= 100; i++) {

            for (int j = 2; j <= Math.sqrt(i); j++) { //根号i Math.sqrt(i)
                if (i%j==0) {
                    //能除尽
                    flag = false;
                    break;
                }
            }
            if (flag){
                System.out.println(i);
            }
            //重置flag
            flag = true;

        }
    }
    public static void test3(){
        for (int i = 1; i < 11; i++) {

            if (i%4==0){
               // break;//终止当前循环1，2，3
                continue;//跳过本次循环，进入下一次循环
            }
            System.out.println(i);
        }
    }
    public static void test4(){
        //数组创建，一旦初始化完成，其长度就确定了
        //1一维数组的初始化
        int[] ids={1,2,3,4};
        int[] arr=new int[]{5,6,7,8};//静态初始化，数组的声明和初始化同时进行
        //动态初始化,声明和初始化分开进行
        String [] arrstr;
        arrstr= new String[5];
        //2调用数组中的元素,数组的索引开始是0，长度减一结束
        arrstr[0]="aaa";
        arrstr[1]="sss";
        arrstr[2]="ddd";
        arrstr[3]="ccc";
        arrstr[4]="fff";
        //获取数组长度
        int length = arrstr.length;
        System.out.println(length);
        IntStream chars = arrstr[0].chars();
        System.out.println(chars);
        long count = chars.count();
        System.out.println(count);

        //数组的默认初始化值
        //整型数据类型 默认是0
        // 浮点数默认类型0.0；
        //数组元素是char型，0
        //boolean类型 false（对应的二进制0）

        //数组元素是应用类型默认值：null  空值


        //数组的内存解析
        //



    }
    public static void test5(){
        //使用scanner输入学生人数，学生分数，
        //成绩>=最高分-10  A
        //成绩>=最高分-20  B
        //成绩>=最高分-30  C
        // 其余为D

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入学生人数");
        int next = scanner.nextInt();
        int[] ints = new int[next];
        int i=0;
        while (i < next) {
            System.out.println("请输入第"+i+1+"个同学的成绩：");
            int i1 = scanner.nextInt();
            ints[i]=i1;
            i++;
        }
        int temp=ints[0];

        for (int j = 1; j < next; j++) {
            if (temp<ints[j]){
                temp=ints[i];
            }
        }

//        for (int i = 0; i < next; i++) {
//            System.out.println("请输入第"+i+1+"个同学的成绩：");
//            int i1 = scanner.nextInt();
//            ints[i]=i1;
//            if (i==next-1){
//                //最后一位同学
//                System.out.println("");
//            }
 //       }


    }

    public static void test6(){
        //求二维数组中所有元素的和
        //定义二维数组，静态初始化
        int[][] arr={{3,5,8},{12,9},{7,0,6,4}};

        int sum=0;
        for (int i = 0; i < arr.length; i++) {

            for (int j = 0; j < arr[i].length; j++) {
                int i1 = arr[i][j];
                sum+=i1;
            }
        }
        System.out.println(sum);

    }

    //杨辉三角
    public static void test7(){
        //1,声明并初始化二维数组
        int[][] yanghui=new int[10][];
        //2，给数组元素赋值
        for (int i = 0; i < yanghui.length; i++) {
            yanghui[i]=new int[i+1];
            yanghui[i][0]=1;
            yanghui[i][i]=1;
            
            if (i>1){
                for (int j = 1; j < yanghui[i].length-1; j++) {
yanghui[i][j]=yanghui[i-1][j-1]+yanghui[i-1][j];
                }
            }
        }

        //3，打印输出展示
        for (int i = 0; i < yanghui.length; i++) {

            for (int j = 0; j < yanghui[i].length; j++) {
                System.out.print(yanghui[i][j]+" ");
            }
            System.out.println();
        }
    }

    //数组反转
    public static void test8(){
        //创建数组，并初始化
        String [] arr= {"aa","vv","ff","gg","tt","jj","kk","rr"};
        //打印数组
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+"  ");
        }
        System.out.println();
        String temp;
        //反转数组
        //方式一：
//        for (int i = 0; i < arr.length / 2; i++) {
//            temp=arr[i];
//            arr[i]=arr[arr.length-1-i];
//            arr[arr.length-i-1]=temp;
//        }
        //方式二：
        for (int i = 0,j = arr.length-1; i <j ; i++,j--) {
            temp=arr[i];
            arr[i]=arr[j];
            arr[j]=temp;
        }

        System.out.println("========反转后========");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+"  ");
        }
    }

    //排序
    public static void test9(){
        int [] arr1={-11,33,-33,3,25,24,13,88,111,8,26,30,99};

       // int temp=arr1[0];
        //冒泡排序
        for (int i = 0; i < arr1.length; i++) {

            for (int j = 0; j < arr1.length-1-i; j++) {
                if (arr1[j]>arr1[j+1]) {
                    int temp =arr1[j];
                    arr1[j]=arr1[j+1];
                    arr1[j+1]=temp;
                }
            }

        }
        for (int i = 0; i < arr1.length; i++) {
            System.out.println(arr1[i]);
        }

    }

    //测试
    public static void test10(){

        int[] arr1= new int[]{1,2,4,5};
        System.out.println(arr1);//[I@39a054a5地址值

        char[] chars = new char[]{'a', 'g', 'h', 'k'};
        System.out.println(chars);//aghk

        char[] chars2 = new char[6];
        System.out.println(chars2);//      

    }

}

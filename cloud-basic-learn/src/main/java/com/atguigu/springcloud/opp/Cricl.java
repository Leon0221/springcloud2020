package com.atguigu.springcloud.opp;

public class Cricl {

    public static void main(String[] args) {

        yuan yuan = new yuan();
        yuan.dir=10;
        double cricle = yuan.cricle();
        System.out.println(cricle);

    }

}

class yuan{

    //属性 半径
    //private double dir;
     double dir;

    //方法，计算面积

    public double cricle(){
        double v = Math.PI * dir * dir;
        return v;
    }
}
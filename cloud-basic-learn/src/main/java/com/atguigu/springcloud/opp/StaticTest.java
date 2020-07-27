package com.atguigu.springcloud.opp;

public class StaticTest {
    public static void main(String[] args) {

        Cricls cricls = new Cricls();
        Cricls cricls2 = new Cricls();
        Cricls cricls3 = new Cricls(10.0);
        System.out.println(cricls.getId());
        System.out.println(cricls2.getId());
        System.out.println(cricls3.finare(cricls3.getRadus()));
        System.out.println(Cricls.getTotal());

    }
}

class Cricls{
    private double radus;//半径
    private int id;//自动增长

    private static int total;//创建的个数

    public static int initId=1001;
    public Cricls(){
        id=initId++;
        total++;
    }
    public Cricls(double radus){
        this();
        this.radus=radus;
    }

    public double getRadus() {
        return radus;
    }

    public void setRadus(double radus) {
        this.radus = radus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getTotal() {
        return total;
    }

    public double finare(double radus){
        return Math.PI*radus*radus;
    }

}
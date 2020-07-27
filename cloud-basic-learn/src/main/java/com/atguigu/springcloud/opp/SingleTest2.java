package com.atguigu.springcloud.opp;

public class SingleTest2 {
    public static void main(String[] args) {

        Order instance = Order.getInstance();
        Order instance2 = Order.getInstance();
        System.out.println(instance==instance2);
    }
}

class Order{
    //私有化构造器
    private Order(){

    }

    //声明类的私有的静态的实力对象，但是不实例话
    private static Order order=null;

    //对外提供，共有的访问方法

    public static Order getInstance(){
        if (order==null){
            order=new Order();
        }
        return order;
    }
}

package com.atguigu.springcloud.opp;

/**
 * 创建饿汉式的单例模式
 */
public class SingletonTest1 {
    public static void main(String[] args) {

        Bank bank1 = Bank.getInstance();
        Bank bank2 = Bank.getInstance();
        System.out.println(bank1==bank2);
    }
}

class Bank{

    //1,私有化类的构造器
    private Bank(){
    }
    //2,在类的内部创建私有对象,并且静态话，用static修饰
    private static Bank bank = new Bank();
    //3,创建类的公共的访问方法，返回私有化创建的对象
    public static Bank getInstance(){
        return bank;
    }

}

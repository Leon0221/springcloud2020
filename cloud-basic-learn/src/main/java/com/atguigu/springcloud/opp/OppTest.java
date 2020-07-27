package com.atguigu.springcloud.opp;

/**
 * 1，类
 *
 *
 * 属性=成员变量=filed=域
 * 行为=成员方法
 *
 */
public class OppTest {
    public static void main(String[] args) {
//        Person p1 = new Person();
//        p1.name="Tom";
//        p1.age=2;
//        p1.isman=true;
//
//        System.out.println(p1.name);
//
//        p1.eat();
//        p1.sleep();
//        p1.talk("国语");

        Object o1=true ? new Integer(1):new Double(2.0);
        System.out.println(o1);//1.0

    }

}
class Person{
    //创建类

    //创建类的属性
    String name;
    int age;
    boolean isman;

    //行为，即成员方法

    public void eat(){
        System.out.println("人需要吃饭");
    }
    public void sleep(){
        System.out.println("人需要睡觉");
    }
    public void talk(String language){
        System.out.println("使用"+language+"说话");
    }
}
package com.wby.thread.manythread.Chapetor10内部类.node1创建内部类;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/9 20:22
 * @Description:
 */
public class 创建内部类2 {
    public Destination to(String s){
        return new Destination(s);
    }
    public Contents contents(){
        return new Contents();
    }
    public void ship(String dest){
        Contents contents = contents();
        Destination to = to(dest);
        System.out.println(to.readLabeler());
    }

    public static void main(String[] args) {
        创建内部类2 p2 = new 创建内部类2();
        p2.ship("Timasa");
        创建内部类2 p3 = new 创建内部类2();
        Contents contents = p3.contents();
        Destination bornor = p3.to("Bornor");

    }


    class Contents{
        private int i=11;
        public int value(){
            return i;
        }
    }
    class Destination{
        private String label;
        Destination(String whereTO){
            label = whereTO;
        }
        String readLabeler(){
            return label;
        }
    }
}

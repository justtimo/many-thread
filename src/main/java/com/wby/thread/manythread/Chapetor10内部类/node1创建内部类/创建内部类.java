package com.wby.thread.manythread.Chapetor10内部类.node1创建内部类;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/9 20:11
 * @Description:
 */
public class 创建内部类 {
    public void ship(String dest){
        Contents contents = new Contents();
        Destination destination = new Destination(dest);
        System.out.println(destination.readLabeler());
    }

    public static void main(String[] args) {
        创建内部类 p1 = new 创建内部类();
        p1.ship("Tamisas");
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

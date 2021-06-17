package com.wby.thread.manythread.Chapetor10内部类.node4内部类与向上转型;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/16 17:24
 * @Description:
 */
public class TestParcel {
    public static void main(String[] args) {
        Parcel4 p = new Parcel4();
        Contents c = p.contents();
        System.out.println(c.value());
        Destination1 d = p.destination("wwwww");
        System.out.println(d.readLeable());
        //非法：can`t access private class
        //Parcel4.PContents pContents = p.new PContents();

    }
}
/**
* @Description: TODO
 * PContents是private的。除了Parcel4，没人能够访问它。
 * PDestination是protect的。
 * 这意味着，客户端程序员想要访问这些成员会受到限制。甚至不能向下转型为private内部类（或protect内部类，除非是继承自他的子类），因为不能访问其名字。见17行代码示例。
 * 于是，private内部类给类的设计者提供了一种途径。通过这种方式可以完全阻止任何依赖于类型的编码，并完全隐藏了实现细节。
*/
class Parcel4{
    public Destination1 destination(String s){
        return new PDestination(s);
    }
    public Contents contents(){
        return new PContents();
    }

    private class PContents implements Contents{
        private int i=11;
        @Override
        public int value() {
            return i;
        }
    }

    protected class PDestination implements Destination1{
        private String label;
        private PDestination(String whereTo){
            label = whereTo;
        }
        @Override
        public String readLeable() {
            return label;
        }
    }

}





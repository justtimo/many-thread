package com.wby.thread.manythread.Chapetor10内部类.node10内部类的覆盖;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/30 09:33
 * @Description: 如果创建了一个内部类，然后继承其外围类并重新定义此内部类时，也就是说内部类可以被覆盖吗？
 *  下面的例子说明，覆盖内部类就好像他外围类的一个方法，其实并不起什么作用
 */
public class BigEgg extends Egg{
    public class Yolk {
        public Yolk() {
            System.out.println("BigEgg .Yolk()");
        }
    }

    public static void main(String[] args) {
        BigEgg bigEgg = new BigEgg();
    }
}
class Egg{
    private Yolk y;
    protected class Yolk {
        public Yolk() {
            System.out.println("Egg .Yolk()");
        }
    }

    public Egg() {
        System.out.println("new Egg()");
        y=new Yolk();
    }
}
/**
* @Description: 默认构造器时编译器自动生成的，这里是调用基类的构造器。你可能认为，既然创建了BigEgg对象，那么应该使用覆盖后的Yolk版本。但是从输出中可以看到，并不是这样的
 *  这说明了，当继承了某个外围类的时候，内部类并没有什么神奇的变化，这两个内部类是两个完全独立的实体，各自在自己的命名空间内。
 *
 *  当然，明确的继承某个内部类可以使用下面这种方式：
*/
 class BigEgg2 extends Egg2{
    public class Yolk extends Egg2.Yolk{
        public Yolk() {
            System.out.println("BigEgg2 .Yolk()");
        }
        public void f(){
            System.out.println("BigEgg2.Yolk.f()");
        }
    }
    public BigEgg2(){
        insertYolk(new Yolk());
    }

    public static void main(String[] args) {
        BigEgg2 bigEgg2 = new BigEgg2();
        bigEgg2.g();
    }
}
class Egg2{
    protected class Yolk {
        public Yolk() {
            System.out.println("Egg2 .Yolk()");
        }
        public void f(){
            System.out.println("Egg2.Yolk.f()");
        }
    }
    private Yolk y=new Yolk();

    public Egg2() {
        System.out.println("new Egg2()");
        y=new Yolk();
    }
    public void insertYolk(Yolk yy){
        y=yy;
    }
    public void g(){
        y.f();
    }
}
/**
* @Description: 现在BIgEgg2.Yolk通过extends Egg2.Yolk明确继承了此内部类，并且覆盖了其中的方法，insertYolk方法允许BigEgg2将自己的Yolk对象向上转型为Egg2中的引用y
 *  所以当g()调用y.f()时，覆盖后的新版本f()被执行。
 *  第二次调用Egg2.Yolk时，结果是BigEgg2.Yolk的构造器调用了它的基类的构造器。可以看到，在调用g()时，新版f()被调用了
*/

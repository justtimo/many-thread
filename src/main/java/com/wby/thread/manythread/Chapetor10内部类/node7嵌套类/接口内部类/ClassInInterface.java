package com.wby.thread.manythread.Chapetor10内部类.node7嵌套类.接口内部类;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/17 20:13
 * @Description: 正常情况下。不能再接口内部放置任何代码，但嵌套类可以作为接口的一部分。在接口中的任何类都是public和static的。
 * 因为类是static的，只是将嵌套类放置于接口的命名空间内，并不违反接口的规则。甚至可以在内部类中实现其外围接口
 */
public interface ClassInInterface {
    void howdy();
    class Test implements ClassInInterface{

        @Override
        public void howdy() {
            System.out.println("howdy");
        }

        public static void main(String[] args) {
            new Test().howdy();
        }
    }
}
/**
* @Description: TODO 如果想要创建某些公共代码，使得他们可以被某个接口的所有实现所共用，name使用接口内部的嵌套类会很方便
*/


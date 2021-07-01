package com.wby.thread.manythread.Chaptor11持有对象.node0序文.node8Stack;

import java.util.LinkedList;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/30 18:19
 * @Description:    栈 通常指 后进先出(LIFO) 的容器.有时栈也会被称作 叠加栈 ，因为最后压入栈的元素，第一个弹出。
 *  LinkedList具有能够直接实现栈的所有功能的方法，因此可以直接将LinkedList作为栈使用，。
 *  不过有时一个真正的栈能够把事情讲的更清楚
 */
class Stack<T>{
    private LinkedList<T> storage=new LinkedList<T>();
    public void push(T v){
        storage.addFirst(v);
    }
    public T peek(){
        return storage.getFirst();
    }
    public T pop() {
        return storage.removeFirst();
    }
    public boolean isEmpty() {
        return storage.isEmpty();
    }
    public String toString() {
        return storage.toString();
    }
}
/**
* @Description: 这里通过使用泛型，引入了在栈的类定义中最简单的可行性示例。
 * 类名之后的<T>告诉编译器这将是一个参数化类型，而其中的类型参数，即在类被使用的将会被实际类型替换的参数，就是T。
 * Stack是用LinkedList实现的，而LinkedList也被告知它将持有T类型对象，
 * 注意push接受的是T类型的对象，而peek和pop将会返回T类型的对象。
 *
 * 如果你只需要栈的行为，这里使用继承就不合适了。因为这样会产生具有LinkedList的其他所有方法的类。
 * 下面演示了这个新的Stack类：
*/
class StackTest{
    public static void main(String[] args) {
        Stack<String> stringStack = new Stack<>();
        for (String s : "My dog has fleas".split(" ")) {
            stringStack.push(s);
        }
        while (!stringStack.isEmpty()){
            System.out.println(stringStack.pop()+" ");
        }

        java.util.Stack<String> strings = new java.util.Stack<>();
        for (String s : "My1 dog1 has1 fleas1".split(" ")) {
            strings.push(s);
        }
        while (!strings.isEmpty()){
            System.out.println(strings.pop()+" ");
        }
    }
}
/**
* @Description: 这两个Stack具有相同的接口，尽管已经有了java.util.Stack，但是LinkedList可以产生更好的Stack，因此自己手写的Stack所采用的方式更可取
*/
public class StackCollision {
}

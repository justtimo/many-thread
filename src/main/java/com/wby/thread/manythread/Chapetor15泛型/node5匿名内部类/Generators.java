package com.wby.thread.manythread.Chapetor15泛型.node5匿名内部类;

import com.wby.thread.manythread.Chapetor15泛型.node3泛型接口.Coffee;
import com.wby.thread.manythread.Chapetor15泛型.node3泛型接口.CoffeeGenerator;
import com.wby.thread.manythread.Chapetor15泛型.node3泛型接口.Fibonacci;
import com.wby.thread.manythread.net.mindview.util.Generator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/9 17:52
 * @Description:
 */
public class Generators {
    public static <T> Collection<T> fill(Collection<T> coll, Generator<T> gen, int n) {
        for(int i = 0; i < n; i++){
            coll.add(gen.next());
        }
        return coll;
    }
    public static void main(String[] args) {
        Collection<Coffee> coffee = fill(
                new ArrayList<Coffee>(), new CoffeeGenerator(), 4);
        for(Coffee c : coffee)
            System.out.println(c);
        Collection<Integer> fnumbers = fill(
                new ArrayList<Integer>(), new Fibonacci(), 12);
        for(int i : fnumbers)
            System.out.print(i + ", ");
    }
}

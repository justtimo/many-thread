package com.wby.thread.manythread.Chaptor11持有对象.node0序文.node10Map;

import java.util.HashMap;
import java.util.Random;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/1 09:35
 * @Description:    考虑一个程序，它将检查Random类的随机性。
 *  理想状态下，Random可以生成理想的数字分布，但想要测试则需要生成大量的随机数，并对落入不同范围的数进行计数。
 *  下面的例子中，key是Random产生的数字，value是出现的次数
 */
public class Statistics {
    public static void main(String[] args) {
        Random random = new Random();
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < 10000; i++) {
            int r = random.nextInt(20);
            Integer freq = map.get(r);
            map.put(r,freq==null ? 1 : freq+1);
        }
        HashMap<Integer, String> map1 = new HashMap<>();
        HashMap<Integer, Integer> map2 = new HashMap<>();
        map1.put(1,"wby");
        map1.put(1, "wby111");
        map2.put(1,500);
        map2.put(2,500);
        System.out.println("----------------");
        System.out.println(map1);
        System.out.println("----------------");
        System.out.println(map);
        System.out.println("----------------");
        System.out.println(map.containsKey(1));
        System.out.println(map2.containsValue(500));
    }
}

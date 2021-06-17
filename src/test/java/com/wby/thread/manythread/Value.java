package com.wby.thread.manythread;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/5/24 14:17
 * @Description:
 */
public class Value {
    private int i;
    public Value(int i){
        this.i = i;
    }

    public Value() {
    }

    public static void main(String[] args) {
        Value value = new Value(11);
        System.out.println(value);
    }
}

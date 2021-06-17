package com.wby.thread.manythread;

import java.util.Arrays;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/5/25 17:50
 * @Description:
 */
public class Args {
    public void manyArgs(String name ,String addr,Long... args){
        System.out.println(name);
        System.out.println(addr);
        Arrays.asList(args);
    }
}

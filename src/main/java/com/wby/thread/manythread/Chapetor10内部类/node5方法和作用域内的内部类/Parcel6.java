package com.wby.thread.manythread.Chapetor10内部类.node5方法和作用域内的内部类;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/16 20:20
 * @Description: 在任意作用域内嵌入一个内部类
 */
public class Parcel6 {
    private void internalTracking(boolean b){
        if (b){
            class TrackingSkip{
                private String id;
                TrackingSkip(String s) {
                    id=s;
                }
                String getSkip(){
                    return id;
                }
            }
            TrackingSkip ts = new TrackingSkip("slip");
            String skip = ts.getSkip();
        }
        //can`t use here! out of scope
        //TrackingSkip ss = new TrackingSkip("ss");
    }
    public void track(){
        internalTracking(true);
    }

    public static void main(String[] args) {
        Parcel6 p = new Parcel6();
        p.track();
    }
}

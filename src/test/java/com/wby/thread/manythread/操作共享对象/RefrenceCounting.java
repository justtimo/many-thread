package com.wby.thread.manythread.操作共享对象;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/5/26 10:06
 * @Description:
 */
class Shared {
    private int refCount=0;
    private static long counter=0;
    private final long id=counter++;

    public Shared() {
        System.out.printf("Creating "+this);
    }
    public void addRef(){
        refCount++;
    }
    public void dispose(){
        if (--refCount==0){
            System.out.println("Disposing "+this);
        }
    }

    @Override
    public String toString() {
        return "Shared{}" +id;
    }
}
class Composing {
    private Shared shared;
    private static long counter=0;
    private final long id=counter++;
    public Composing(Shared shared){
        System.out.println("Creating "+this);
        this.shared = shared;
        this.shared.addRef();
    }
    protected void dispose(){
        System.out.println("disposing "+this);
        shared.dispose();
    }

    @Override
    public String toString() {
        return "Composing{}"+id;
    }
}

public class RefrenceCounting {
    public static void main(String[] args) {
        Shared shared = new Shared();
        Composing[] composing = {
                new Composing(shared),
                new Composing(shared),
                new Composing(shared),
                new Composing(shared),
                new Composing(shared),
                new Composing(shared)
        };
        for (Composing c : composing) {
            c.dispose();
        }
    }
}

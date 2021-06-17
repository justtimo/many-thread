package com.wby.thread.manythread;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/5/24 11:52
 * @Description:
 */

public class EJCharacter7 {
    private String s;

    public EJCharacter7() {
        System.out.println("EJCharacter7() ");
        s="Constructer";
    }

    @Override
    public String toString() {
        return s;
    }


    public static class Bath{
        private String
        s1="Happy",
        s2="happy",
        s3,s4;

        private EJCharacter7 ejCharacter7;
        private int i;
        private float tol;

        public Bath() {
            System.out.println("inside Bath");
            s3="Joy";
            tol=3.14f;
            ejCharacter7=new EJCharacter7();
        }
        {i=47;}

        @Override
        public String toString() {
            if (s4==null)
                s4 = "Joy";
                return
                        "s1= "+s1 +"\n" +
                                "s2= "+s2 +"\n" +
                                "s3= "+s3 +"\n" +
                                "s4= "+s4 +"\n" +
                                "i= "+i +"\n" +
                                "tol =" +tol +"\n"+
                                "ejCharacter7= "+ejCharacter7+"\n";
        }

        public static void main(String[] args) {
            Bath bath = new Bath();
            System.out.println(bath);
        }
    }

}




package com.wby.thread.manythread.constructor;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/5/31 18:05
 * @Description:
 */
public class PolymorphicBehaviorInsideTheConstructor {
    public static void main(String[] args) {
        new RoundGlyph(5);
    }
}
class Glyph{
    void draw(){
        System.out.println("Glyph.draw()");
    }
    Glyph(){
        System.out.println("Glyph() before draw()");
        draw();
        System.out.println("Glyph() after draw()");
    }
}
class RoundGlyph extends Glyph {
    private int radius=1;

    public RoundGlyph(int radius) {
        this.radius = radius;
        System.out.println("RoundGlyph.RoundGlyph().radius = "+radius);
    }

    public RoundGlyph() {
        System.out.println("RoundGlyph.RoundGlyph() ...... ");
    }
    void draw() {
        System.out.println("RoundGlyph.draw().radius = "+radius);
    }
}


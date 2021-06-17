package com.wby.thread.manythread.abstruct;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/1 10:43
 * @Description:
 */
public class Music4 {
    static void tune(Instrument i){
        i.play(Note.MIDDLE_C);
    }
    static void tuneAll(Instrument[] i){
        for (Instrument instrument : i) {
            tune(instrument);
        }
    }

    public static void main(String[] args) {
        Instrument[] instruments={new Wind(),new Percussion(),new Stringed(),new Brass(),new WoodWind()};
        tuneAll(instruments);
    }
}

enum Note{
    MIDDLE_C;
}

abstract class Instrument{
    private int i;
    public abstract void play(Note n);
    public String what() {
        return "Instrument";
    }
    public abstract void adjust();
}

class Wind extends Instrument {

    @Override
    public void play(Note n) {
        System.out.println("wind play() "+n);
    }

    @Override
    public String what() {
        return "wind";
    }

    @Override
    public void adjust() {

    }
}
class Percussion extends Instrument {

    @Override
    public void play(Note n) {
        System.out.println("percussion play() "+n);
    }

    @Override
    public String what() {
        return "percussion";
    }

    @Override
    public void adjust() {

    }
}

class Stringed extends Instrument {

    @Override
    public void play(Note n) {
        System.out.println("Stringed play() "+n);
    }

    @Override
    public String what() {
        return "stringed";
    }

    @Override
    public void adjust() {

    }
}

class Brass extends Wind{
    public void play(Note n) {
        System.out.println("Brass play() "+n);
    }
    public void adjust() {
        System.out.println("Brass adjust() ");
    }
}

class WoodWind extends Wind{
    public void play(Note n) {
        System.out.println("WoodWind play() "+n);
    }
    public void adjust() {
        System.out.println("WoodWind adjust() ");
    }
}

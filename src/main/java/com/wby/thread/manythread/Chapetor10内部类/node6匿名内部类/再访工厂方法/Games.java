package com.wby.thread.manythread.Chapetor10内部类.node6匿名内部类.再访工厂方法;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/17 19:19
 * @Description:
 */
public class Games {
    public static void playGame(GameFactory gf){
        Game game = gf.getGame();
        while (game.move())
            ;
    }

    public static void main(String[] args) {
        playGame(Checkers.gf);
        playGame(Chess.gf);
    }
}
interface Game{
    boolean move();
}
interface GameFactory {
    Game getGame();
}
class Checkers implements Game {
    private Checkers(){}
    private int moves=0;
    private static final int MOVES=3;
    @Override
    public boolean move() {
        System.out.println("Checkers move "+moves);
        return ++moves!=MOVES;
    }
    public static GameFactory gf=new GameFactory() {
        public Game getGame() {
            return new Checkers();
        }
    };
}
class Chess implements Game {
    private Chess(){}
    private int moves=0;
    private static final int MOVES=4;
    @Override
    public boolean move() {
        System.out.println("Chess move "+moves);
        return ++moves!=MOVES;
    }
    public static GameFactory gf=new GameFactory() {
        @Override
        public Game getGame() {
            return new Chess();
        }
    };
}

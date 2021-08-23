package com.wby.thread.manythread.character9接口.工厂方法设计模式;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/9 19:43
 * @Description:
 */
public class 工厂方法设计模式2 {
    public static void playGame(GameFactory gameFactory){
        Game game = gameFactory.getGame();
        while (game.move()){
            ;
        }
    }

    public static void main(String[] args) {
        playGame(new CheckersFactory());
        playGame(new ChessFactory());
    }
}


interface Game{
    Boolean move();
}
interface GameFactory{
    Game getGame();
}

class Checkers implements Game {
    private int moves=0;
    public static final int MOVES=3;
    @Override
    public Boolean move() {
        System.out.println("Checkers moves:" +moves);
        return  ++moves != MOVES;
    }
}
class CheckersFactory implements GameFactory{
    @Override
    public Game getGame() {
        return new Checkers();
    }
}

class Chess implements Game {
    private int moves=0;
    public static final int MOVES=4;

    @Override
    public Boolean move() {
        System.out.println("Chess moves:"+moves);
        return ++moves != MOVES;
    }
}
class ChessFactory implements GameFactory {
    @Override
    public Game getGame() {
        return new Chess();
    }
}

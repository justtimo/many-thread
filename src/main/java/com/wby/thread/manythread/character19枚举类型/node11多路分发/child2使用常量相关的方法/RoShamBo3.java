//: enumerated/RoShamBo3.java
// Using constant-specific methods.
package com.wby.thread.manythread.character19枚举类型.node11多路分发.child2使用常量相关的方法;

import com.wby.thread.manythread.character19枚举类型.node11多路分发.Outcome;
import com.wby.thread.manythread.character19枚举类型.node11多路分发.child1使用enum分发.Competitor;
import com.wby.thread.manythread.character19枚举类型.node11多路分发.child1使用enum分发.RoShamBo;

import static com.wby.thread.manythread.character19枚举类型.node11多路分发.Outcome.*;

/**
 * 常量相关的方法允许我们为每个enum实例提供方法的不同实现，这使得常量相关的方法似平是实现多路分发的完美解决方案。不过，通过这种方式，enum实例虽然可以具有不同的行为，
 * 但它们仍然不是类型，不能将其作为方法签名中的参数类型来使用。最好的办法是将enum用在switch语句中，见下例∶
 */
public enum RoShamBo3 implements Competitor<RoShamBo3> {
  PAPER {
    public Outcome compete(RoShamBo3 it) {
      switch(it) {
        default: // To placate the compiler
        case PAPER: return DRAW;
        case SCISSORS: return LOSE;
        case ROCK: return WIN;
      }
    }
  },
  SCISSORS {
    public Outcome compete(RoShamBo3 it) {
      switch(it) {
        default:
        case PAPER: return WIN;
        case SCISSORS: return DRAW;
        case ROCK: return LOSE;
      }
    }
  },
  ROCK {
    public Outcome compete(RoShamBo3 it) {
      switch(it) {
        default:
        case PAPER: return LOSE;
        case SCISSORS: return WIN;
        case ROCK: return DRAW;
      }
    }
  };
  public abstract Outcome compete(RoShamBo3 it);
  public static void main(String[] args) {
    RoShamBo.play(RoShamBo3.class, 20);
  }
} /* Same output as RoShamBo2.java *///:~
/**
 * 虽然这种方式可以工作，但是却不甚合理，如果采用RoShamBo2.java的解决方案，那么在添加一个新的类型时，只需更少的代码，而且也更直接。
 * 然而，RoShamBo3.java还可以压缩简化一下∶
 */
enum RoShamBo4 implements Competitor<RoShamBo4> {
  ROCK {
    public Outcome compete(RoShamBo4 opponent) {
      return compete(SCISSORS, opponent);
    }
  },
  SCISSORS {
    public Outcome compete(RoShamBo4 opponent) {
      return compete(PAPER, opponent);
    }
  },
  PAPER {
    public Outcome compete(RoShamBo4 opponent) {
      return compete(ROCK, opponent);
    }
  };
  Outcome compete(RoShamBo4 loser, RoShamBo4 opponent) {
    return ((opponent == this) ? Outcome.DRAW
            : ((opponent == loser) ? Outcome.WIN
            : Outcome.LOSE));
  }
  public static void main(String[] args) {
    RoShamBo.play(RoShamBo4.class, 20);
  }
} /* Same output as RoShamBo2.java *///:~
/**
 * 其中，具有两个参数的compete（）方法执行第二个分发，该方法执行一系列的比较，其行为类似switch语句。这个版本的程序更简短，不过却比较难理解。对于一个大型系统而言，
 * 难以理解的代码将导致整个系统不够健壮。
 */

package com.wby.thread.manythread.Chapetor15泛型.node12自限定的类型;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/13 19:43
 * @Description:    在java泛型中，有一个好像是经常性出现的惯用法，他相当令人费解：
 *      class SelfBounded<T extends SelfBounded<T>>{// ......}
 *
 * 这就像两面镜子彼此照向对方所引起的目眩效果一样，是一种无限反射。SelfBounded类接受泛型参数T，而T由一个边界类限定，这个边界就是拥有T
 * 作为其参数的SelfBounded。
 *
 * 当你首次看到他，很难去解析它，他强调的是当extends关键字用于边界与用来创建子类明显是不同的。
 */
public class Text {
}

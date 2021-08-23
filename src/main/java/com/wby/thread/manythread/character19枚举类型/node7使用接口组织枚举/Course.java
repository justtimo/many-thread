package com.wby.thread.manythread.character19枚举类型.node7使用接口组织枚举;

import com.wby.thread.manythread.character19枚举类型.node6随机选取.Enums;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/8/23 17:24
 * @Description:
 */
public enum Course {
    APPETIZER(Food.Appetizer.class),
    MAINCOURSE(Food.MainCourse.class),
    DESSERT(Food.Dessert.class),
    COFFEE(Food.Coffee.class);
    private Food[] values;
    private Course(Class<? extends Food> kind) {
        values = kind.getEnumConstants();
    }
    public Food randomSelection() {
        return Enums.random(values);
    }
} ///:~

package com.wby.thread.manythread.character20注解.node2编写注解处理器.child3生成外部文件;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/8/23 10:35
 * @Description:
 */
 @Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
 public @interface Constraints {
    boolean primaryKey() default false;
    boolean allowNull() default true;
    boolean unique() default false;
} ///:~

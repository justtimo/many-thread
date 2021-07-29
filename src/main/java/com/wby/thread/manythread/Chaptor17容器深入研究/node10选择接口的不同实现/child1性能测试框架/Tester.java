package com.wby.thread.manythread.Chaptor17容器深入研究.node10选择接口的不同实现.child1性能测试框架;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/29 19:02
 * @Description:
 */

import java.util.List;

/**
 * @Description: 为了使用这个框架，你需要将待测容器以及Test对象列表传递给Tester.runO方法（这些都是重载的泛型便利方法，它们可以减少在使用它们时所必需的类型检查）。
 * Tester.run（O方法调用适当的重栽构造器。然后调用timedTest（）。它会执行针对该容器的列表中的每一个测试。 timedTestO）会使用paramList中的每个TestParam对象进行重复测试。
 * 因为paramList是从静态的 defaultParams数组中初始化出来的，因此你可以人通过重新赋值defaultParams，来修改用干所有测试的paramList，或者可以通过传递针对
 * 某个测试的定制的paramList，来修改用干该测试的 paramList:
 */
public class Tester<C> {
    public static int fieldWidth = 8;
    public static TestParam[] defaultParams= TestParam.array(
            10, 5000, 100, 5000, 1000, 5000, 10000, 500);
    // Override this to modify pre-test initialization:
    protected C initialize(int size) { return container; }
    protected C container;
    private String headline = "";
    private List<Test<C>> tests;
    private static String stringField() {
        return "%" + fieldWidth + "s";
    }
    private static String numberField() {
        return "%" + fieldWidth + "d";
    }
    private static int sizeWidth = 5;
    private static String sizeField = "%" + sizeWidth + "s";
    private TestParam[] paramList = defaultParams;
    public Tester(C container, List<Test<C>> tests) {
        this.container = container;
        this.tests = tests;
        if(container != null)
            headline = container.getClass().getSimpleName();
    }
    public Tester(C container, List<Test<C>> tests,
                  TestParam[] paramList) {
        this(container, tests);
        this.paramList = paramList;
    }
    public void setHeadline(String newHeadline) {
        headline = newHeadline;
    }
    // Generic methods for convenience :
    public static <C> void run(C cntnr, List<Test<C>> tests){
        new Tester<C>(cntnr, tests).timedTest();
    }
    public static <C> void run(C cntnr,
                               List<Test<C>> tests, TestParam[] paramList) {
        new Tester<C>(cntnr, tests, paramList).timedTest();
    }
    private void displayHeader() {
        // Calculate width and pad with '-':
        int width = fieldWidth * tests.size() + sizeWidth;
        int dashLength = width - headline.length() - 1;
        StringBuilder head = new StringBuilder(width);
        for(int i = 0; i < dashLength/2; i++)
            head.append('-');
        head.append(' ');
        head.append(headline);
        head.append(' ');
        for(int i = 0; i < dashLength/2; i++)
            head.append('-');
        System.out.println(head);
        // Print column headers:
        System.out.format(sizeField, "size");
        for(Test test : tests)
            System.out.format(stringField(), test.name);
        System.out.println();
    }
    // Run the tests for this container:
    public void timedTest() {
        displayHeader();
        for(TestParam param : paramList) {
            System.out.format(sizeField, param.size);
            for(Test<C> test : tests) {
                C kontainer = initialize(param.size);
                long start = System.nanoTime();
                // Call the overriden method:
                int reps = test.test(kontainer, param);
                long duration = System.nanoTime() - start;
                long timePerRep = duration / reps; // Nanoseconds
                System.out.format(numberField(), timePerRep);
            }
            System.out.println();
        }
    }
} ///:~
/**
 * @Description: stringField（）和numberField（）方法会产生用于输出结果的格式化字符串，格式化的标准宽度可以通过修改静态的fieldWidth的值进行调整。
 * displayHeader（）方法为每个测试格式化和打印头信息。
 *
 * 如果你需要执行特殊的初始化，那么可以覆盖initializeO方法，这将产生具有恰当尺寸的容器对象——你可以修改现有的容器对象，或者创建新的容器对象。在test（）方法中可以看到，
 * 其结果被捕获在一个被称为kontainer的局部引用中，这使得你可以将所存储的成员container替换为完全不同的被初始化的容器。
 *
 * 每个Test.test（方法的返回值都必须是该测试执行的操作的数量，这些测试都会计算其所有操作所需的纳秒数。你应该意识到，通常System.nanoTime（）所产生的值的粒度都会
 * 大于1（这个粒度会随机器和操作系统的不同而不同），因此，在结果中可能会存在某些时间点上的重合。
 *
 * 执行的结果可能会随机器的不同而不同，这些测试只是想要比较不同容器的性能。
 */

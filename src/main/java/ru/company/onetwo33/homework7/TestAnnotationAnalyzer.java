package ru.company.onetwo33.homework7;

import java.lang.reflect.Method;

public class TestAnnotationAnalyzer {
    public static void start(Class testClass) throws Exception {
        Method[] methods = testClass.getMethods();
        int countBeforeSuite = 0;
        int countAfterSuite = 0;

        // Сначала считаем кол-во BeforeSuite и AfterSuite, и приоритеты @Test
        for (Method o : methods) {
            if (o.getAnnotation(BeforeSuite.class) != null) {
                countBeforeSuite++;
                if (countBeforeSuite > 1) throw new RuntimeException();
            }
            if (o.getAnnotation(AfterSuite.class) != null) {
                countAfterSuite++;
                if (countAfterSuite > 1) throw new RuntimeException();
            }
        }

        // Отсортируем по value тесты пузырьком
        for (int out = methods.length - 1; out >= 1; out--) {
            for (int in = 0; in < out; in++) {
                if (methods[in].getAnnotation(Test.class) != null &&
                        methods[in].getAnnotation(Test.class).value() > methods[in].getAnnotation(Test.class).value()) {
                    Method temp = methods[in];
                    methods[in] = methods[in + 1];
                    methods[in + 1] = temp;
                }
            }
        }

        // Затем всё вызываем
        for (Method o : methods) {
            if (o.getAnnotation(BeforeSuite.class) != null) {
                Method init = Tests.class.getDeclaredMethod("init", null);
                o.invoke(new Tests(), null);
            }
            if (o.getAnnotation(Test.class) != null) {
                if (o.getName().equals("sum")) {
                    Method sum = Tests.class.getDeclaredMethod("sum", int.class, int.class);
                    o.invoke(new Tests(), 3, 3); // Не понимаю как в тест передать параметры, если они неизвестны..
                }
                if (o.getName().equals("info")) {
                    Method test = Tests.class.getDeclaredMethod("info", null);
                    o.invoke(new Tests(), null);
                }
            }
            if (o.getAnnotation(AfterSuite.class) != null) {
                Method finish = Tests.class.getDeclaredMethod("finish", null);
                o.invoke(new Tests(), null);
            }
        }
    }
}

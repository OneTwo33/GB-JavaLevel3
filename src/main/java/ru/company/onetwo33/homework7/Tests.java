package ru.company.onetwo33.homework7;

public class Tests {

    @BeforeSuite
    public void init() {
        System.out.println("First test!");
    }

    @Test(value = 10)
    public void info() {
        System.out.println("Hello from Tests class!");
    }

    @Test(value = 3)
    public void sum(int a, int b) {
        System.out.println(a + b);
    }

    @AfterSuite
    public void finish() {
        System.out.println("Finish!");
    }
}

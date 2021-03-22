package ru.company.onetwo33.homework5;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private Race race;
    private int speed;
    private String name;
    private CyclicBarrier ready;
    private CountDownLatch start;
    private CountDownLatch finish;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CyclicBarrier ready, CountDownLatch start, CountDownLatch finish) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.ready = ready;
        this.start = start;
        this.finish = finish;
    }

    @Override
    public void run() {
        try {
            prepareGo(); // Готовимся к гонке
            ready.await(); // Ожидаем пока все участники приготовятся и
            start.await(); // start будет равен 0
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            go(); // Поехали
            finish.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void go() {
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        if (!this.race.isWinner()) {
            this.race.setWinner(true);
            System.out.println(this.name + " WIN!");
        }
        finish.countDown();
    }

    private void prepareGo() throws InterruptedException {
        System.out.println(this.name + " готовится");
        Thread.sleep(500 + (int)(Math.random() * 800));
        System.out.println(this.name + " готов");
        start.countDown();
    }
}

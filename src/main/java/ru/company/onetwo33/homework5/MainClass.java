package ru.company.onetwo33.homework5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class MainClass {
    public static final int CARS_COUNT = 4;
    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(CARS_COUNT), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        CyclicBarrier ready = new CyclicBarrier(CARS_COUNT); // Подготовим все машины к гонке
        CountDownLatch finish = new CountDownLatch(CARS_COUNT); // Ждем пока все финишируют
        CountDownLatch start = new CountDownLatch(CARS_COUNT); // Ждем пока все готовятся, уменьшаем счетчик когда машина приготовилась

        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), ready, start, finish);
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

        try {
            start.await(); // Дожидаемся пока все подготовятся и cdl будет равен 0, чтобы вывести сообщение о начале гонки
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");

        try {
            finish.await(); // Дожидаемся пока все финишируют и finish будет равен 0, чтобы вывести сообщение о окончании гонки
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}

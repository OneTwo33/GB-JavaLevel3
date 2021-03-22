package ru.company.onetwo33.homework5;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    private final Semaphore semaphore;

    public Tunnel(int members) {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        this.semaphore = new Semaphore(members / 2); // только половина участника могут въехать в тоннель
    }

    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                semaphore.acquire();
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                semaphore.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

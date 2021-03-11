package ru.company.onetwo33.homework1;

import java.util.ArrayList;
import java.util.List;

public class Box<T extends Fruit> {
    private List<T> list;

    public Box() {
        this.list = new ArrayList<>();
    }

    public Box(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public float getWeight() {
        float sum = 0;
        for (Fruit fruit : list) {
            sum += fruit.getWeight();
        }
        return sum;
    }

    public boolean compare(Box<T> box) {
        return this.getWeight() == box.getWeight();
    }

    public void move(Box<T> box) {
        try {
            if (box.list.get(0).getClass() != this.list.get(0).getClass()) {
                System.out.println("В коробках разные фрукты!");
            } else {
                box.list.addAll(this.list);
                this.list.clear();
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Одна из коробок пустая!");
        }
    }

    public void addFruit(T fruit) {
        // Если коробка не пуста и фрукт другого класса, чем первый в коробке, то не добавляем
        if (!this.list.isEmpty() && this.list.get(0).getClass() != fruit.getClass()) {
            System.out.println("Нельзя добавить в коробку другой фрукт!");
        } else
            this.getList().add(fruit);
    }
}

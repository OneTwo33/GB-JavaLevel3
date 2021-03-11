package ru.company.onetwo33.homework1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[] strings = {"test", "test1", "test2", "test3"};

        // 1. Написать метод, который меняет два элемента массива местами.(массив может быть любого ссылочного типа);
        Arrays.stream(strings).forEach(System.out::println);
        swap(strings,0, 3);
        Arrays.stream(strings).forEach(System.out::println);

        // 2. Написать метод, который преобразует массив в ArrayList;
        System.out.println(transformToArrayList(strings));

        // 3 Задача
        // Считаем вес
        Box box1 = new Box();
        box1.addFruit(new Apple());
        box1.addFruit(new Apple());
        System.out.println(box1.getWeight());
        Box box2 = new Box();
        box2.addFruit(new Orange());
        box2.addFruit(new Orange());
        System.out.println(box2.getWeight());

        // Сравним коробки
        System.out.println(box1.compare(box2));

        // Пересыпем box1 (яблоки) в box2 (апельсины)
        box1.move(box2);

        // Пересыпем box1 (яблоки) в box3 (яблоки)
        Box box3 = new Box();
        box3.addFruit(new Apple());
        box1.move(box3);
        box1.getList().stream().forEach(o -> System.out.println("яблоко в box1"));
        box3.getList().stream().forEach(o -> System.out.println("яблоко в box3"));

        // Добавляем яблоко в box1
        box1.addFruit(new Apple());
        box1.addFruit(new Orange());
        box1.getList().stream().forEach(o -> System.out.println("яблоко в box1"));
    }

    public static <T> void swap(T[] array, int indexFirstElem, int indexSecondElem) {
        try {
            T temp = array[indexFirstElem];
            array[indexFirstElem] = array[indexSecondElem];
            array[indexSecondElem] = temp;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Вы вышли за пределы массива");
        }
    }

    public static <T> ArrayList<T> transformToArrayList(T[] array) {
        ArrayList<T> arrayList = new ArrayList<>(Arrays.asList(array));
        return arrayList;
    }
}

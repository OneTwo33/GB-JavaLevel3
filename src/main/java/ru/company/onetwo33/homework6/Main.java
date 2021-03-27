package ru.company.onetwo33.homework6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {

    }

    public int[] newArr(int[] arr) {
        // Есть ли 4-ки в массиве
        boolean hasFour = false;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 4) {
                hasFour = true;
                break;
            }
        }
        if (hasFour) {
            List<Integer> arrList = new ArrayList<>();
            for (int i = arr.length - 1; i >= 0; i--) { // Идем с обратного конца массива к последней 4-ке
                if (arr[i] == 4) break;
                else arrList.add(arr[i]);
            }
            if (arrList.isEmpty())
                throw new RuntimeException();
            else {
                Collections.reverse(arrList); // перевернем лист
                int[] newArr = new int[arrList.size()];
                for (int i = 0; i < newArr.length; i++) {
                    newArr[i] = arrList.get(i);
                }
                return newArr;
            }
        }
        throw new RuntimeException();
    }

    public boolean findOneOrFourInArr(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 1 || arr[i] == 4) {
                return true;
            }
        }
        return false;
    }
}

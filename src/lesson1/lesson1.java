package lesson1;

import java.util.ArrayList;

class Matrix<T> {
    T[] array;

    public Matrix(T[] array) {
        this.array = array;
    }

    //1. Написать метод, который меняет два элемента массива местами.(массив может быть любого ссылочного типа);
    public void exchange(int elem1, int elem2) {
        T temp;

        temp = array[elem1];
        array[elem1] = array[elem2];
        array[elem2] = temp;
    }

    //2. Написать метод, который преобразует массив в ArrayList;
    public ArrayList<T> convert() {
        ArrayList<T> tmp = new ArrayList<T>();
        for (T t: array) {
            tmp.add(t);
        }
        return tmp;
    }

    public void showMatrix() {
        for (T t: array) {
            System.out.print(t+" ");
        }
        System.out.println();
    }
}

public class lesson1 {
    public static void main(String[] args) {
        String[] strArray = {"Alex","Brad","Cam","Den","Eugene"};
        Matrix<String> strMatr = new Matrix<String>(strArray);
        strMatr.showMatrix();
        strMatr.exchange(1,4);
        strMatr.showMatrix();

        ArrayList<String> alString = strMatr.convert();
        System.out.println(alString);
    }
}

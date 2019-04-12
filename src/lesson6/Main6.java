package lesson6;

import java.util.Arrays;

public class Main6 {
    public static int[] exclude (int[] array)
    {
        int last4 = -1;
        for (int i = array.length-1; i >= 0; i--) {
            if (array[i] == 4)
            {
                last4 = i;
                break;
            }
        }
        if (last4 < 0) throw new RuntimeException();
        int[] tmp = new int[array.length-last4-1];
        System.arraycopy(array,last4+1,tmp,0,tmp.length);
        return tmp;
    }

    public static boolean is1or4 (int[] array)
    {
        for (int i = 0; i < array.length; i++) {
            if ((array[i] == 1) || (array[i] == 4)) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        int[] array1 = {0,1,2,3,4,5,6,7,8};
        int[] array2 = {1,2,4,4,2,3,4,1,7};
        int[] array3 = {2,3,5,6,7};

        System.out.println(Arrays.toString(array1));
        try {
            System.out.println(Arrays.toString(exclude(array1)));
        }
        catch (RuntimeException e)
        {
            System.out.println("В массиве нет четверок");
        }
        System.out.println(is1or4(array1)?"В массиве есть 1 или 4":"В массиве нет ни 1 ни 4");
        System.out.println(Arrays.toString(array2));
        try {
            System.out.println(Arrays.toString(exclude(array2)));
        }
        catch (RuntimeException e)
        {
            System.out.println("В массиве нет четверок");
        }
        System.out.println(is1or4(array2)?"В массиве есть 1 или 4":"В массиве нет ни 1 ни 4");
        System.out.println(Arrays.toString(array3));
        try {
            System.out.println(Arrays.toString(exclude(array3)));
        }
        catch (RuntimeException e)
        {
            System.out.println("В массиве нет четверок");
        }
        System.out.println(is1or4(array3)?"В массиве есть 1 или 4":"В массиве нет ни 1 ни 4");
    }
}

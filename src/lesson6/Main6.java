package lesson6;

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
}

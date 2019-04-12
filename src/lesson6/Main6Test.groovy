package lesson6

import org.junit.Assert
import org.junit.Test

class Main6Test {

    @Test
    void testExclude1() {
        int[] array1 = [0, 1, 2, 3, 4, 5, 6, 7, 8];
        int[] result1 = [5, 6, 7, 8];
        Assert.assertArrayEquals(result1, Main6.exclude(array1));
    }

    @Test
    void testExclude2() {
        int[] array2 = [1, 2, 4, 4, 2, 3, 4, 1, 7];
        int[] result2 = [1, 7];
        Assert.assertArrayEquals(result2, Main6.exclude(array2));
    }

    @Test(expected = RuntimeException.class)
    void testExclude3() {
        int[] array3 = [2, 3, 5, 6, 7];
        Main6.exclude(array3);
    }

    @Test
    void testIs1or4_1() {
        int[] array1 = [0, 1, 2, 3, 4, 5, 6, 7, 8];
        Assert.assertTrue(Main6.is1or4(array1));
    }

    @Test
    void testIs1or4_2() {
        int[] array2 = [1, 2, 4, 4, 2, 3, 4, 1, 7];
        Assert.assertTrue(Main6.is1or4(array2));
    }

    @Test
    void testIs1or4_3() {
        int[] array3 = [2, 3, 5, 6, 7];
        Assert.assertFalse(Main6.is1or4(array3));
    }
}

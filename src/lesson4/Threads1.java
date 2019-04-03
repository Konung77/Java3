package lesson4;

public class Threads1 {
    private static final Object lock = new Object();
    private volatile static char currentLetter = 'A';

    public static void printA()
    {
        synchronized (lock)
        {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != 'A') lock.wait();
                    System.out.print("A");
                    currentLetter = 'B';
                    lock.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void printB()
    {
        synchronized (lock)
        {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != 'B') lock.wait();
                    System.out.print("B");
                    currentLetter = 'C';
                    lock.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void printC()
    {
        synchronized (lock)
        {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != 'C') lock.wait();
                    System.out.print("C");
                    currentLetter = 'A';
                    lock.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(()->printA()).start();
        new Thread(()->printB()).start();
        new Thread(()->printC()).start();
    }
}

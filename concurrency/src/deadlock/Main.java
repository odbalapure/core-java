package deadlock;

public class Main {

    private static Object lock1 = new Object();
    private static Object lock2 = new Object();

    public static void main(String[] args) {
        new Thread1().start();
        new Thread2().start();
    }

     /*To avoid deadlock we can
     * Have locks to be acquired a sequential manner
     * Have only one lock (impractical solution in real world applications)*/
    private static class Thread1 extends Thread {
        @Override
        public void run() {
            synchronized (lock1) {
                System.out.println("Thread1 has lock1");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }
                System.out.println("Thread1 waiting for lock2");
                synchronized (lock2) {
                    System.out.println("Thread1 has lock1 and lock2");
                }
                System.out.println("Thread1 has released lock2");
            }
            System.out.println("Thread1 has released lock1. Now exiting...");
        }
    }

    private static class Thread2 extends Thread {
        @Override
        public void run() {
            synchronized (lock2) {
                System.out.println("Thread2 has lock2");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }
                System.out.println("Thread2 waiting for lock1");
                synchronized (lock1) {
                    System.out.println("Thread2 has released lock2 and lock1");
                }
                System.out.println("Thread2 has released lock1");
            }
            System.out.println("Thread2 has released lock2. Now exiting...");
        }
    }
}

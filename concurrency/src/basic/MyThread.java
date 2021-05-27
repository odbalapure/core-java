package basic;

import static starvation.ThreadColor.*;

public class MyThread {

    public static void main(String[] args) {
        Thread anotherThread = new AnotherThread();
        anotherThread.setName("AnotherClass");
        anotherThread.start();

        System.out.println(ANSI_PURPLE + "The Main Thread");

        /*
        * new Thread() {
            public void run() {
                System.out.println(ANSI_GREEN + "The Anonymous class Thread");
            }
        }.start();
        * */

        Thread myRunnableThread = new Thread(() -> {
            System.out.println(ANSI_RED + "The Anonymous MyRunnable class Thread");
            try {
                anotherThread.join();
                System.out.println(ANSI_RED + "Another thread TERMINATED or TIMED OUT, I am back again up and running!");
            } catch (InterruptedException e) {
                System.out.println(ANSI_RED + "I couldn't wait! I was interrupted");
            }
        });
        myRunnableThread.start();
        /* anotherThread.interrupt(); */
    }
}

class AnotherThread extends Thread {
    @Override
    public void run() {
        System.out.println(ANSI_BLUE + "The Another class Thread. Thread name is: " + currentThread().getName() +".");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println(ANSI_BLUE + "Another thread woke me up!");
            return;
        }

        System.out.println(ANSI_BLUE + "Slept for 5 seconds...");
    }
}

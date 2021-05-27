package basic;

import static starvation.ThreadColor.*;

public class MyRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println(ANSI_RED + "The MyRunnable Thread");
    }

    public static void main(String[] args) {
        Thread myRunnableThread = new Thread(new MyRunnable());
        myRunnableThread.start();

        Thread myRunThreadThread2 = new Thread(() -> {
            System.out.println(ANSI_CYAN + "The Lambda MyRunnable class Thread");
        });

        new Thread(new MyRunnable()) {
            @Override
            public void run() {
                System.out.println(ANSI_BLACK + "The Anonymous MyRunnable class Thread");
            }
        }.start();

        myRunThreadThread2.start();
    }
}

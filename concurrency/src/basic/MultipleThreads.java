package basic;

import starvation.ThreadColor;

public class MultipleThreads {

    public static void main(String[] args) {
        CountDown countDown = new CountDown();

        CountDownThread t1 = new CountDownThread(countDown);
        t1.setName("Thread 1");

        CountDownThread t2 = new CountDownThread(countDown);
        t2.setName("Thread 2");

        t1.start();
        t2.start();
    }
}

class CountDown {

    private  int i;

    /*when multiple threads are writing for a shared resource
    it causes thread interference or a race condition
    so we need to make such area of the code as synchronized
    so that only one thread can access that block of code one at a time
    */

    public void doCountDown() {
        String color;

        switch (Thread.currentThread().getName()) {
            case "Thread 1":
                color = ThreadColor.ANSI_CYAN;
                break;
            case "Thread 2":
                color = ThreadColor.ANSI_PURPLE;
                break;
            default:
                color = ThreadColor.ANSI_GREEN;
        }

        synchronized (this) {
            for (i = 10; i > 0; i--) {
                System.out.println(color + Thread.currentThread().getName() + ": i = " + i);
            }
        }
    }
}

/*
synchronization is reentrant if a thread acquires a LOCK, it can still own that LOCK to enter a CS.
*/
class CountDownThread extends Thread {

    private CountDown countDown;

    public CountDownThread(CountDown countDown) {
        this.countDown = countDown;
    }

    @Override
    public void run() {
        countDown.doCountDown();
    }

}

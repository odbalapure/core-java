package producerconsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/*
* Before Java 5 we had to rely solely on the synchronized block. Main problems related to it were:
* 1. The main problem is that when a thread acquires a lock for a CS
*   Then that thread cannot be interrupted, it will hold the lock until it finishes its job
*   Also that thread might hold the lock forever causing other threads to starve i.e create a deadlock
*
* 2. Synchronized block cannot be started in one method and end in another method
*
* 3. We can't check if an object's intrinsic lock is available or find any other info of that lock
*   If we don't have the lock, we can't time out after we have waited for long
*
* 4. If multiple threads demand a lock. If it is not FCFS then JVM will choose the next thread that gets the lock
*
* P.S: We can use ReentrantLock (Lock Interface) to tackle this situation
* */

public class Main {
    public static final String EOF = "EOF";

    public static void main(String[] args) {
        List<String> buffer = new ArrayList<>();
        /* If a thread holds a reentrant lock and it is about to enter a CS
        * that requires the same lock then it can continue execution
        * NOTE: Not all implementations of Lock interface are reentrant
        *  */
        ReentrantLock reentrantLock = new ReentrantLock();

        MyProducer producer = new MyProducer(buffer, ThreadColor.ANSI_RED);
        MyConsumer consumer1 = new MyConsumer(buffer, ThreadColor.ANSI_PURPLE);
        MyConsumer consumer2 = new MyConsumer(buffer, ThreadColor.ANSI_CYAN);

        new Thread(producer).start();
        new Thread(consumer1).start();
        new Thread(consumer2).start();
    }
}

class MyProducer implements  Runnable {
    private List<String> buffer;
    private String color;

    public MyProducer(List<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;
    }

    @Override
    public void run() {
        Random random = new Random();
        String[] nums = { "1", "2", "3", "4", "5"};

        for(String num: nums) {
            try {
                System.out.println(color + "Adding..." + num);
                synchronized(buffer) {
                    buffer.add(num);
                }

                Thread.sleep(random.nextInt(1000));
            } catch(InterruptedException e) {
                System.out.println("Producer was interrupted");
            }
        }

        System.out.println(color + "Adding EOF and exiting....");
        synchronized(buffer) {
            buffer.add("EOF");
        }
    }
}

class MyConsumer implements Runnable {
    private List<String> buffer;
    private String color;

    public MyConsumer(List<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;
    }

    @Override
    public void run() {
        while(true) {
            synchronized(buffer) {
                if(buffer.isEmpty()) {
                    continue;
                }
                if(buffer.get(0).equals(Main.EOF)) {
                    System.out.println(color + "Exiting");
                    break;
                } else {
                    System.out.println(color + "Removed " + buffer.remove(0));
                }
            }
        }
    }
}

















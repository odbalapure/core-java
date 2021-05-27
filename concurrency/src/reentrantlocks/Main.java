package reentrantlocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static final String EOF = "EOF";

    public static void main(String[] args) {
        List<String> buffer = new ArrayList<>();
        /* If a thread holds a reentrant lock and it is about to enter a CS
        * that requires the same lock then it can continue execution
        * NOTE: Not all implementations of Lock interface are reentrant so we use ReentrantLock specifically
        *  */
        ReentrantLock bufferLock = new ReentrantLock();
        MyProducer producer = new MyProducer(buffer, ThreadColor.ANSI_RED, bufferLock);
        MyConsumer consumer1 = new MyConsumer(buffer, ThreadColor.ANSI_PURPLE, bufferLock);
        MyConsumer consumer2 = new MyConsumer(buffer, ThreadColor.ANSI_CYAN, bufferLock);

        new Thread(producer).start();
        new Thread(consumer1).start();
        new Thread(consumer2).start();
    }
}

class MyProducer implements  Runnable {
    private List<String> buffer;
    private String color;
    private ReentrantLock bufferLock;

    public MyProducer(List<String> buffer, String color, ReentrantLock bufferLock) {
        this.buffer = buffer;
        this.color = color;
        this.bufferLock = bufferLock;
    }

    @Override
    public void run() {
        Random random = new Random();
        String[] nums = { "1", "2", "3", "4", "5"};

        for(String num: nums) {
            try {
                System.out.println(color + "Adding..." + num);
                /*Now here is a downside of reentrant locks
                * We are responsible to put or release a lock object
                * Synchronized blocks use to take care of that by themselves
                * */

                /*It is good practice to use try finally while using LOCKS
                * No matter what happens the unlock will be given up in case something goes wrong
                * */
                bufferLock.lock();
                try {
                    buffer.add(num);
                } finally {
                    bufferLock.unlock();
                }

                Thread.sleep(random.nextInt(1000));
            } catch(InterruptedException e) {
                System.out.println("Producer was interrupted");
            }
        }

        System.out.println(color + "Adding EOF and exiting....");
        bufferLock.lock();
        try {
            buffer.add("EOF");
        } finally {
            bufferLock.unlock();
        }
    }
}

class MyConsumer implements Runnable {
    private List<String> buffer;
    private String color;
    private ReentrantLock bufferLock;

    public MyConsumer(List<String> buffer, String color, ReentrantLock bufferLock) {
        this.buffer = buffer;
        this.color = color;
        this.bufferLock = bufferLock;
    }

    @Override
    public void run() {
        int count = 0;

        while(true) {
            /*We can check if a thread owns a lock using the tryLock() method*/
            if (bufferLock.tryLock()) {
                try {
                    if(buffer.isEmpty()) {
                        /*We need to remove this statement using try - finally, as we are trying
                         * to release a lock that we don't own hence an IllegalThreadStateException
                         * will be thrown
                         *
                         * This block of code should only execute IFF we have a lock
                         * */
                        /*bufferLock.unlock();*/
                        continue;
                    }
                    System.out.println(color +"The counter: " + count);
                    count = 0;

                    if(buffer.get(0).equals(Main.EOF)) {
                        break;
                    } else {
                        System.out.println(color + "Removed " + buffer.remove(0));
                    }
                } finally {
                    bufferLock.unlock();
                }
            } else {
                count++;
            }
        }
    }
}


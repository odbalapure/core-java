package synchronization;

import java.util.Random;

public class Messages {

    public static void main(String[] args) {
        Messages messages = new Messages();
        new Thread(new Writer(messages)).start();
        new Thread(new Reader(messages)).start();
    }

    private String message;
    private boolean empty = true;

    public synchronized String read() {
        /*
         we must call wait() inside a loop rather than in if else
         there is no guarantee that a thread has woken up after calling
         notifyAll().
         - Possible that OS could have woken it
         - Condition to wake up has changed
         - wait() called though an InterruptedException
        */
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        empty = true;
        notifyAll();
        return message;
    }

    public synchronized void write(String message) {
        while(!empty) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        empty = false;
        this.message = message;
        notifyAll();
    }
}

class Writer implements Runnable {

    private Messages message;

    public Writer(Messages message) {
        this.message = message;
    }
  
    @Override
    public void run() {
        String messages[] = {
                "Hi there!", "My name is Om",
                "I live in Bangalore", "I am a Software Engineer",
                "Have a good day..."
        };

        Random random = new Random();
        for (int i=0; i<messages.length; i++) {
            message.write(messages[i]);

            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {
                System.out.println("I was interrupted by Reader...");
            }
        }
        message.write("Finished");
    }
}

class Reader implements Runnable {

    private Messages message;

    public Reader(Messages message) {
        this.message = message;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (String latestMessage = message.read(); !latestMessage.equals("Finished");
                latestMessage = message.read()) {
            System.out.println(latestMessage);

            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {
                System.out.println("I was interrupted by Writer...");
            }
        }
    }
}

/*
* There are some operations where thread can't be suspended
* For eg: Long and Double - JVM may require read and write for both of them
**/

/*
* There are some collections that are not thread safe, for eg: ArrayList, HashMap
*
* Vector is thread safe
* Hast Table is thread safe
* ArrayBlockingQueue methods are thread safe
* */
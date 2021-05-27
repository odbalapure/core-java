package blockingqueues;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static final String EOF = "EOF";

    public static void main(String[] args) {
        /*ArrayBlockingQueue is bounded i.e. they don't expand on demand like ArrayList
        * We have to mention the size before head
        * */
        ArrayBlockingQueue<String> buffer = new ArrayBlockingQueue<String>(6);
        ReentrantLock bufferLock = new ReentrantLock();
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        MyProducer producer = new MyProducer(buffer, ThreadColor.ANSI_RED);
        MyConsumer consumer1 = new MyConsumer(buffer, ThreadColor.ANSI_PURPLE);
        MyConsumer consumer2 = new MyConsumer(buffer, ThreadColor.ANSI_CYAN);

        executorService.execute(producer);
        executorService.execute(consumer1);
        executorService.execute(consumer2);

        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println(ThreadColor.ANSI_BLACK + "*****************************************");
                System.out.println(ThreadColor.ANSI_BLACK + "Inside call()");
                return "This is a CALLABLE result";
            }
        });

        try {
            System.out.println(future.get());
        } catch (ExecutionException | InterruptedException e) {
            System.out.println("Something went wrong while fetching FUTURE result...");
        }

        /*We MUST shutdown the service otherwise the application will never terminate*/
        executorService.shutdown();
    }
}

class MyProducer implements  Runnable {
    private ArrayBlockingQueue<String> buffer;
    private String color;

    public MyProducer(ArrayBlockingQueue<String> buffer, String color) {
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
                buffer.put(num);

                Thread.sleep(random.nextInt(1000));
            } catch(InterruptedException e) {
                System.out.println("Producer was interrupted");
            }
        }

        System.out.println(color + "Adding EOF and exiting....");
        try {
            buffer.put("EOF");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyConsumer implements Runnable {
    private ArrayBlockingQueue<String> buffer;
    private String color;

    public MyConsumer(ArrayBlockingQueue<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;
    }

    @Override
    public void run() {
        synchronized (buffer) {
            while(true) {
                try {
                    if(buffer.isEmpty()) {
                        continue;
                    }
                    if(buffer.peek().equals(Main.EOF)) {
                        break;
                    } else {
                        System.out.println(color + "Removed " + buffer.take());
                    }
                } catch (InterruptedException e){
                    System.out.println("Something went wrong while consuming...");
                }
            }
        }
    }
}


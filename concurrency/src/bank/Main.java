package bank;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        BankAccount account = new BankAccount("31654298", 100.00, lock);

        /*Thread t1 = new Thread(() -> {
            account.deposit(300.00);
            account.withdraw(50.00);
        });

        Thread t2 = new Thread(() -> {
            account.deposit(203.75);
            account.withdraw(100.00);
        });*/

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                account.deposit(300.00);
                account.withdraw(50.00);
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                account.deposit(203.75);
                account.withdraw(100.00);
            }
        });

        t1.start();
        account.printAccountNumber();
        t2.start();
        account.printAccountNumber();
    }

}

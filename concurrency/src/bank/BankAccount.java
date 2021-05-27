package bank;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BankAccount {

    private double balance;
    private String accountNumber;

    private Lock lock;

    public BankAccount(String accountNumber, double initialBalance, Lock lock) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.lock = new ReentrantLock();
    }

    public void deposit(double amount) {
        /*synchronized(this) {
            balance += amount;
        }*/

        /*lock.lock();
        try {
            balance += amount;
        } finally {
            lock.unlock();
        }*/

        boolean status = false;
        try {
            if(lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
                try {
                    balance += amount;
                    status = true;
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("Could not get the lock");
            }
        } catch (InterruptedException e) {
            System.out.println("Something went wrong while depositing amount...");
        }

        System.out.println("Transaction status: " + status);
    }

    public synchronized void withdraw(double amount) {
        /*synchronized (this) {
            balance -= amount;
        }*/

        /*lock.lock();
        try {
            balance -= amount;
        } finally {
            lock.unlock();
        }*/

        boolean status = false;
        try {
            if (lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
                try {
                    balance -= amount;
                    status = true;
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("Could not get the lock");
            }
        } catch (InterruptedException e) {
            System.out.println("Something went wrong while withdrawing amount...");
        }

        System.out.println("Transaction status: " + status);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void printAccountNumber() {
        System.out.println("The account number: " + accountNumber);
    }

}

/*
* NOTE: Local variables are thread safe
* As each thread maintains it's own stack*/
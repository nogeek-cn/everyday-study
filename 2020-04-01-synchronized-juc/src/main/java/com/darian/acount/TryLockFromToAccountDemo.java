package com.darian.acount;

import lombok.AllArgsConstructor;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/15  上午2:07
 */
public class TryLockFromToAccountDemo {
    public static void main(String[] args) {
        Account darian = new Account("darian", 1000);
        Account mercy = new Account("mercy", 10000);
        ReentrantLock darianLock = new ReentrantLock();
        ReentrantLock mercyLock = new ReentrantLock();

        new TryLockTransferAccount(darian, mercy, 10, darianLock, mercyLock).start();
        new TryLockTransferAccount(mercy, darian, 20, mercyLock, darianLock).start();

    }
}

@AllArgsConstructor
class TryLockTransferAccount extends Thread {
    private Account fromAccount;

    private Account toAccount;

    private int amount;

    private Lock fromLock;

    private Lock toLock;

    @Override
    public void run() {
        while (true) {
            if (fromLock.tryLock()) {
                if (toLock.tryLock()) {
                    if (fromAccount.getBalance() >= amount) {
                        fromAccount.debit(amount);
                        toAccount.credit(amount);
                    }
                }
            }

            System.out.println(String.format("转出账户:[%s],转入账户:[%s]",
                    fromAccount, toAccount));
        }
    }
}

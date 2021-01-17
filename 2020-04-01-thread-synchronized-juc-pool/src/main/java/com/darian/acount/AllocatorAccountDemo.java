package com.darian.acount;

import lombok.AllArgsConstructor;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

/***
 * 加入统一的协调者，防止死锁
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/15  上午1:44
 */
public class AllocatorAccountDemo {
    public static void main(String[] args) {
        Account darian = new Account("darian", 1000);
        Account mercy = new Account("mercy", 10000);
        Allocator allocator = new Allocator();

        new AllocatorTransferAccount(darian, mercy, 10, allocator).start();
        new AllocatorTransferAccount(mercy, darian, 20, allocator).start();
    }
}

@AllArgsConstructor
class AllocatorTransferAccount extends Thread {
    private Account fromAccount;

    private Account toAccount;

    private int amount;

    private Allocator allocator;

    @Override
    public void run() {
        while (true) {
            if (allocator.apply(fromAccount, toAccount)) {
                try {
                    synchronized (fromAccount) {
                        synchronized (toAccount) {
                            if (fromAccount.getBalance() >= amount) {
                                fromAccount.debit(amount);
                                toAccount.credit(amount);
                            }
                        }
                    }

                    System.out.println(String.format("转出账户:[%s],转入账户:[%s]",
                            fromAccount, toAccount));

                } finally {
                    allocator.free(fromAccount, toAccount);
                }
            }
        }
    }
}

class Allocator {
    private List<Object> hasLockList = new LinkedList<>();

    /**
     * 统一申请锁
     *
     * @param from
     * @param to
     * @return
     */
    public synchronized boolean apply(Object from, Object to) {
        if (hasLockList.contains(from) || hasLockList.contains(to)) {
            return false;
        }
        hasLockList.add(from);
        hasLockList.add(to);
        return true;
    }

    /**
     * 统一释放锁
     *
     * @param from
     * @param to
     */
    public synchronized void free(Object from, Object to) {
        hasLockList.remove(from);
        hasLockList.remove(to);
    }
}
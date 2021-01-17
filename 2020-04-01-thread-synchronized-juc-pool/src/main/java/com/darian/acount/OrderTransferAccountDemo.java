package com.darian.acount;

import lombok.AllArgsConstructor;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/15  上午2:17
 */
public class OrderTransferAccountDemo {
    public static void main(String[] args) {
        Account darian = new Account("darian", 1000);
        Account mercy = new Account("mercy", 10000);

        new OrderTransferAccount(darian, mercy, 10).start();
        new OrderTransferAccount(mercy, darian, 20).start();
    }
}

@AllArgsConstructor
class OrderTransferAccount extends Thread {
    private Account fromAccount;

    private Account toAccount;

    private int amount;

    @Override
    public void run() {
        Account left = null;
        Account right = null;
        // 名字不能一样
        if (fromAccount.getAccountName().compareTo(toAccount.getAccountName()) > 0) {
            left = fromAccount;
            right = toAccount;
        } else {
            left = toAccount;
            right = fromAccount;
        }

        while (true) {
            synchronized (left) {
                synchronized (right) {
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

package com.darian.acount;

import lombok.AllArgsConstructor;
import lombok.Data;

/***
 * 死锁 例子
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/15  上午12:47
 */
public class TransferAccountDemo {

    public static void main(String[] args) {
        Account darian = new Account("Darian", 10_000);
        Account mercy = new Account("mercy", 10);
        new TransferAccount(darian, mercy, 10).start();
        new TransferAccount(mercy, darian, 20).start();
    }
}

@AllArgsConstructor
class TransferAccount extends Thread {
    private Account fromAccount;

    private Account toAccount;

    private int amount;

    @Override
    public void run() {
        while (true) {
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
        }
    }
}

/***
 *
 * 账户
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/15  上午12:47
 */
@Data
@AllArgsConstructor
class Account {
    /**
     * 账户名
     */
    private String accountName;

    /**
     * 账户余额
     */
    private int balance;

    /**
     * 转出钱
     *
     * @param amount
     */
    public void debit(int amount) {
        this.balance -= amount;
    }

    public void credit(int amount) {
        this.balance += amount;
    }
}

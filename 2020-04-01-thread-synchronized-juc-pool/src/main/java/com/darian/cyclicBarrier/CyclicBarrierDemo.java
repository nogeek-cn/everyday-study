package com.darian.cyclicBarrier;

import javax.swing.plaf.TableHeaderUI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/17  上午12:12
 */
public class CyclicBarrierDemo {

    private static List<PersonThread> personThreadList = new ArrayList<>();

    static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        int personCount = 3;

        CyclicBarrier cyclicBarrier = new CyclicBarrier(personCount, () -> {
            // 先达到栅栏
            System.out.println(String.format("[%s] 一轮吃饭结束", Thread.currentThread().getName()));
            System.out.println("**************************************************\n");

            for (PersonThread personThread : personThreadList) {
                if (personThread.getHasEatCount() > 20) {
                    System.out.println(String.format("[%s] 最先吃完饭。。。。。吃饭结束", Thread.currentThread().getName()));
                    executorService.shutdownNow(); // 立马停止
                    printEveryOneEatCount();
                    return;
                }
            }
        });


        for (int i = 0; i < personCount; i++) {
            personThreadList.add(new PersonThread(cyclicBarrier, i));
        }

        for (PersonThread personThread : personThreadList) {
            executorService.submit(personThread);
        }
    }

    /**
     * 打印每个人吃了多少饭
     */
    public static void printEveryOneEatCount() {
        for (PersonThread onePerson : personThreadList) {
            System.out.println(String.format("[%s]已经吃了[%s]碗饭",
                    onePerson.getName(),
                    onePerson.getHasEatCount()));
        }

    }
}

class PersonThread extends Thread {

    private final CyclicBarrier cyclicBarrier;

    private int eatCount = 1;

    /**
     * 已经吃了多少饭了
     */
    private int hasEatCount = 0;

    public int getHasEatCount() {
        return hasEatCount;
    }

    public PersonThread(CyclicBarrier cyclicBarrier, int i) {
        setName(PersonThread.class.getSimpleName() + "-" + i);
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            System.out.println(String.format("[%s]Person 第[%s]轮吃饭 ...",
                    Thread.currentThread().getName(), eatCount));
            try {
                Thread.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("[%s]Person 第[%s]轮吃饭吃完了....",
                    Thread.currentThread().getName(), eatCount));
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            eatCount++;


            hasEatCount += new Random().nextInt(5);
        }
    }
}

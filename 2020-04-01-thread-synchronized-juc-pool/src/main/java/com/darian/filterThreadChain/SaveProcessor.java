package com.darian.filterThreadChain;


import lombok.RequiredArgsConstructor;

import java.util.concurrent.LinkedBlockingQueue;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/16  下午12:26
 */
@RequiredArgsConstructor
public class SaveProcessor extends Thread implements RequestProcessor {

    LinkedBlockingQueue<Request> linkedBlockingQueue = new LinkedBlockingQueue<>();

    @Override
    public void processorRequest(Request request) {
        linkedBlockingQueue.add(request);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Request request = linkedBlockingQueue.take();
                System.out.println("[" + Thread.currentThread().getName() + "] " + "save data:" + request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

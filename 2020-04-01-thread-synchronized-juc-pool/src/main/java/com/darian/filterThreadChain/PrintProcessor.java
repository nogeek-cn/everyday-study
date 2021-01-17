package com.darian.filterThreadChain;


import lombok.RequiredArgsConstructor;


import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.System.*;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/16  下午12:24
 */
@RequiredArgsConstructor
public class PrintProcessor extends Thread implements RequestProcessor {
    LinkedBlockingQueue<Request> linkedBlockingQueue = new LinkedBlockingQueue<>();
    private final RequestProcessor nextProcess;

    @Override
    public void processorRequest(Request request) {
        linkedBlockingQueue.add(request);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Request request = linkedBlockingQueue.take();
                out.println("[" + Thread.currentThread().getName() + "] " + "print Data:" + request);
                nextProcess.processorRequest(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

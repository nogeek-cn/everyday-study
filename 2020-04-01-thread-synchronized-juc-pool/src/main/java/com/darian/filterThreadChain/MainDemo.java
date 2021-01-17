package com.darian.filterThreadChain;

/***
 * 我们去处理的时候，用异步线程去处理。
 * 当我们把一个请求丢过来的时候，不是直接去处理，而是通过异步线程去处理。
 * zookeeper 就是类似的处理，一方面，你可以通过你的处理把职责划分开，
 * 一方面你可以通过异步线程的处理去提升你程序的性能
 * 合理的利用你 CPU 的资源
 *
 * 这个和 zookeeper 里边非常像
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/16  下午12:27
 */
public class MainDemo {
    private final PrintProcessor printProcessor;

    public MainDemo() {
        SaveProcessor saveProcessor = new SaveProcessor();
        saveProcessor.start();
        printProcessor = new PrintProcessor(saveProcessor);
        printProcessor.start();
    }

    public static void main(String[] args) {
        Request request = new Request();
        request.setName("darian");
        MainDemo demo = new MainDemo();
        demo.doTest(request);
    }

    public void doTest(Request request) {
        printProcessor.processorRequest(request);
    }
}

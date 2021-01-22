package com.darian.nameFactory;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompleteFutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        String result = CompletableFuture.supplyAsync(() ->
                String.format("[{%s}start]", Thread.currentThread().getName()))
                .thenApplyAsync(str ->
                        str + String.format(",[{%s}第1次加工]", Thread.currentThread().getName()))
                .thenApplyAsync(str ->
                        str + String.format(",[{%s}第2次加工]", Thread.currentThread().getName()))
                .thenApplyAsync(str ->
                        str + String.format(",[{%s}第3次加工]", Thread.currentThread().getName()))
                .thenApplyAsync(str ->
                        str + String.format(",[{%s}第4次加工]", Thread.currentThread().getName()))
                .get();

        System.out.println(result);

    }
}
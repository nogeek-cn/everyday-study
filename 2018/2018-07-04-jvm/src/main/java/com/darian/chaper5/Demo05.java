package com.darian.chaper5;

import java.io.IOException;
import java.io.InputStream;


public class Demo05 {

    public static void main(String[] args) {

    }

    public synchronized void f1(){

    }

    public  void f2(){
           synchronized (this){

           }
    }
}

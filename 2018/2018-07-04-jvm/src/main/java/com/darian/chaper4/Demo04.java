package com.darian.chaper4;



public class Demo04 {


    public static void main(String[] args) {

//       throw  new RuntimeException("exception");
       try {
           int i = 1 / 0;
       }catch (RuntimeException e){
           int b = 100;
       }

    }
}



package com.darian.chaper4.service;


import com.darian.chaper4.api.Service;

public class ServiceImpl implements Service  {

    @Override
    public int add(int a, int b) {
        return a + b;
    }
}

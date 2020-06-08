package com.darian.controller;

import com.darian.domain.Person;
import com.darian.utils.MetaspaceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    List<Person> personList = new ArrayList<Person>();

    /**
     * java.lang.OutOfMemoryError
     */
    @GetMapping("/heap")
    public String heap() throws Exception {

        while (true) {
            personList.add(new Person());
        }

    }

    @GetMapping("/heapBySteep")
    public String heapBySteep() throws Exception {
        while (true) {
            personList.add(new Person());
            Thread.sleep(1);
        }
    }

    List<Class<?>> clazzList = new ArrayList<Class<?>>();

    @GetMapping("/nonheap")
    public String nonheap() throws InterruptedException {

        while (true) {
            clazzList.addAll(MetaspaceUtils.createClasses());
            Thread.sleep(5);
        }
    }
}
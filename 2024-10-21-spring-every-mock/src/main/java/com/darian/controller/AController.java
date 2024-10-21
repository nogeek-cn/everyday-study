package com.darian.controller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/***
 *
 *
 * @author <a href="mailto:">notOnlyGeek</a>
 * @date 2024/10/21  下午10:40
 */
@RestController
public class AController implements ApplicationContextAware {

    @Resource
    private AService aService;

    public String sayHello() {
        return aService.sayHello();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        String result = applicationContext.getBean(AController.class).sayHello();
        System.out.println("AController.sayHello: " + result);
    }
}

package com.darian;

import com.darian.dubbo.injvm.DubboInJvmConsumerService;
import lombok.Data;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.Resource;

@EnableDubbo
@SpringBootApplication
@PropertySource("classpath:META-INF/spring/demo-context.properties")
@ImportResource({"classpath:META-INF/spring/*.xml"})
public class DemoApplication implements InitializingBean {

    private static Logger LOGGER = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);
        DubboInJvmConsumerService bean = run.getBean(DubboInJvmConsumerService.class);
        bean.say();
    }

    @Value("${project.user.name}")
    private String name;

    @Resource
    private UserInfo userInfo;

    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.info(userInfo.toString());
        LOGGER.info(name);
    }

    @Data
    public static class UserInfo {
        private String name;
    }
}


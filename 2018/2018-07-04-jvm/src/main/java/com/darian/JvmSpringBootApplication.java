package com.darian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// -XX:+PrintGCDetails -Xmx32M -Xms32M -XX:+HeapDumpOnOutOfMemoryError
@SpringBootApplication
public class JvmSpringBootApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(JvmSpringBootApplication.class);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        builder.build("");
        return builder.sources(JvmSpringBootApplication.class);
    }


}

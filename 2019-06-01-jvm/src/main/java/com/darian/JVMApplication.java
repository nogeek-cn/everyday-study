package com.darian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * -Xmx16m -Xms16m -XX:MetaspaceSize=50M -XX:MaxMetaspaceSize=50M
 */
@SpringBootApplication
public class JVMApplication {
    public static void main(String[] args) {
        new SpringApplication(JVMApplication.class).run(args);
    }
}
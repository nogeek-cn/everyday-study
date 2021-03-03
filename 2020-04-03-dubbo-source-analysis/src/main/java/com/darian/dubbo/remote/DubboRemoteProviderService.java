
package com.darian.dubbo.remote;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@DubboService
@Profile("remote")
public class DubboRemoteProviderService implements DubboRemoteInterface {

    @Override
    public String say() {
        try {
            TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.getClass().getName() + ".say";
    }
}
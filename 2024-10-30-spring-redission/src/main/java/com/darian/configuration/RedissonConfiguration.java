package com.darian.configuration;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/***
 *
 *
 * @author <a href="mailto:">notOnlyGeek</a>
 * @date 2024/10/30  06:11
 */
@Configuration
public class RedissonConfiguration implements ApplicationContextAware {

    @Bean
    public RedissonClient client() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        return Redisson.create(config);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        List<String> lockIdList = new ArrayList<>();
        lockIdList.add("lockKey1");
        lockIdList.add("lockKey2");

        RedissonClient redissonClient = applicationContext.getBean(RedissonClient.class);

        RLock[] rLocks = lockIdList.stream()
                .map(redissonClient::getLock)
                .toArray(RLock[]::new);

        RLock multiLock = redissonClient.getMultiLock(rLocks);

        try {
            boolean isLocked = multiLock.tryLock(10, 10, TimeUnit.SECONDS);
            if (!isLocked) {
                throw new RuntimeException("没有获取到锁！！！ 不进行业务处理");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            multiLock.unlock();
        }
    }
}

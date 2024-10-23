package com.darian.config.spring.beans.factory;

import com.darian.config.config.DDDViewConfigurationProperties;
import com.darian.config.enums.DDDViewLevel;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanExpressionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.Resource;

/***
 *
 *
 * @author <a href="mailto:">notOnlyGeek</a>
 * @date 2024/10/23  下午12:15
 */
public class InjectManageBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    public InjectManageBeanPostProcessor() {
        System.out.println("InjectManageBeanPostProcessor init");
    }

    @Resource
    private DDDViewConfigurationProperties dddViewConfigurationProperties;



    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // todo: 这里进行扫描扫描依赖关系，
        // 开关级别：NO、VIEW、CONTROLLER
        if (DDDViewLevel.NO.equals(dddViewConfigurationProperties.getLevel())) {
            return bean;
        }
        // 生成依赖关系图

        // generator_to_plantUML
        // - 合法
        // - 不合法的

        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 这里校验是否合法
        throw new BeanExpressionException("check failed");
    }
}

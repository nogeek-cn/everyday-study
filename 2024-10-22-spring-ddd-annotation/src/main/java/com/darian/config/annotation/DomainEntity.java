package com.darian.config.annotation;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/***
 *
 *
 * @author <a href="mailto:">notOnlyGeek</a>
 * @date 2024/10/22  下午10:47
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public @interface DomainEntity {
    @AliasFor(
            annotation = Component.class
    )
    String value() default "";
}

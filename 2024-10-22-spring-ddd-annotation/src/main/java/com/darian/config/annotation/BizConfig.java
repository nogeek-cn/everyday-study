package com.darian.config.annotation;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/***
 *
 *
 * @author <a href="mailto:">notOnlyGeek</a>
 * @date 2024/10/22  下午10:58
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
public @interface BizConfig {
    @AliasFor(
            annotation = Configuration.class
    )
    String value() default "";
}

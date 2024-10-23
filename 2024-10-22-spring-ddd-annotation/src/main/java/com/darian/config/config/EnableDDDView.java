package com.darian.config.config;

import com.darian.context.DDDViewScanRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/***
 *
 *
 * @author <a href="mailto:">notOnlyGeek</a>
 * @date 2024/10/23  下午12:51
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DDDViewScanRegistrar.class)
public @interface EnableDDDView {
    String[] value() default {};

    String[] basePackages() default {};


    Class<?>[] basePackageClasses() default {};
}

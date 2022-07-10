package com.aminbhst.quartzautoconfigboot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation which must be used on the class annotated with SpringBootApplication.
 * <p>Used in order to retrieve the name of the package in which the component scanning takes place</p>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableQuartzConfiguration {
}

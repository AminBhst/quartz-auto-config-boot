package com.aminbhst.quartzautoconfigboot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface QuartzJob {

    String cron() default "";

    long repeatInterval() default Long.MAX_VALUE;

    String jobName() default "";

    String triggerName() default "";
}

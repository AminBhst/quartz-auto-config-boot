package com.aminbhst.quartzautoconfigboot.generator;

import com.aminbhst.quartzautoconfigboot.annotation.QuartzJob;
import com.aminbhst.quartzautoconfigboot.builder.ClassBuilder;
import com.aminbhst.quartzautoconfigboot.util.GeneratedMethodBodies;
import org.apache.commons.lang.WordUtils;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import javax.lang.model.element.TypeElement;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class QuartzJobsClassGenerator {

    private static final Set<String> beanAnnotation = Collections.singleton(Bean.class.getName());
    private final ClassBuilder builder = new ClassBuilder();

    public String generate(String pkg, Collection<TypeElement> elements) {
        builder.pkg(pkg).clazz(Collections.singleton(Configuration.class.getName()), "QuartzJobs", false);
        for (final TypeElement element : elements) {
            QuartzJob quartzJob = element.getAnnotation(QuartzJob.class);
            createJobDetail(element, quartzJob);
            createTrigger(element, quartzJob);
        }
        return builder.build();
    }


    private void createTrigger(TypeElement element, QuartzJob quartzJob) {
        String qualifier = getQualifier(WordUtils.uncapitalize(element.getSimpleName() + "JobDetail"));
        Map<String, String> args = Collections.singletonMap(qualifier + JobDetail.class.getName(), "jobDetail");
        String triggerName = quartzJob.triggerName().equals("") ? element.getSimpleName().toString() + " Trigger" : quartzJob.triggerName();
        String methodBody = String.format(GeneratedMethodBodies.createCronTriggerMethodBody, quartzJob.cron(), triggerName);
        String returnType = CronTriggerFactoryBean.class.getName();

        if (quartzJob.repeatInterval() != Long.MAX_VALUE) {
            methodBody = String.format(GeneratedMethodBodies.createSimpleTriggerMethodBody, quartzJob.repeatInterval(), triggerName);
            returnType = SimpleTriggerFactoryBean.class.getName();
        }

        builder.method(beanAnnotation, "public", returnType, element.getSimpleName() + "Trigger", args, methodBody, null);
    }

    private void createJobDetail(TypeElement element, QuartzJob quartzJob) {
        String jobName = quartzJob.jobName().equals("") ? element.getSimpleName().toString() + " Job" : quartzJob.jobName();
        String methodBody = String.format(GeneratedMethodBodies.createJobDetailMethodBody, element.getQualifiedName().toString(), "\"" + jobName + "\"");
        String methodName = WordUtils.uncapitalize(element.getSimpleName().toString()) + "JobDetail";
        builder.method(beanAnnotation, "public", JobDetailFactoryBean.class.getName(), methodName, null, methodBody, null);
    }

    private String getQualifier(String value) {
        return "@" + Qualifier.class.getName() + "(\"" + value + "\")";
    }

}

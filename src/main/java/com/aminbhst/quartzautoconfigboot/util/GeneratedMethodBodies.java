package com.aminbhst.quartzautoconfigboot.util;

public interface GeneratedMethodBodies {
    String createJobInstanceBody = "final Object job = super.createJobInstance(bundle);\n" +
            "        beanFactory.autowireBean(job);\n" +
            "        return job;";
    String setApplicationContextBody = "beanFactory = context.getAutowireCapableBeanFactory();";
    String createJobDetailMethodBody = "return " + QuartzUtil.class.getName() + ".createJobDetail(%s.class, %s);";
    String createCronTriggerMethodBody = "return " + QuartzUtil.class.getName() + ".createCronTrigger(jobDetail, \"%s\", \"%s\");";
    String createSimpleTriggerMethodBody = "return " + QuartzUtil.class.getName() + ".createSimpleTrigger(jobDetail, %s, \"%s\");";
}

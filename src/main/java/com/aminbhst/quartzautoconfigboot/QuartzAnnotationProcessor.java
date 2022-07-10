package com.aminbhst.quartzautoconfigboot;

import com.google.auto.service.AutoService;
import com.aminbhst.quartzautoconfigboot.annotation.EnableQuartzConfiguration;
import com.aminbhst.quartzautoconfigboot.annotation.QuartzJob;
import com.aminbhst.quartzautoconfigboot.builder.ClassBuilder;
import com.aminbhst.quartzautoconfigboot.generator.QuartzJobsClassGenerator;
import com.aminbhst.quartzautoconfigboot.util.GeneratedMethodBodies;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

@SupportedAnnotationTypes({
        "com.aminbhst.quartzautoconfigboot.annotation.EnableQuartzConfigurator",
        "com.aminbhst.quartzautoconfigboot.annotation.QuartzJob"
})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class QuartzAnnotationProcessor extends AbstractProcessor {

    private Filer filer;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        Optional<? extends Element> configuratorElement = roundEnv.getElementsAnnotatedWith(EnableQuartzConfiguration.class)
                .stream()
                .findFirst();

        if (!configuratorElement.isPresent())
            return false;

        Set<TypeElement> quartzJobs = roundEnv.getElementsAnnotatedWith(QuartzJob.class)
                .stream()
                .map(element -> (TypeElement) element)
                .collect(Collectors.toSet());


        String className = ((TypeElement) configuratorElement.get()).getQualifiedName().toString();
        String pkg = className.substring(0, className.lastIndexOf(".")) + ".quartz";
        writeSourceFile("AutowiringSpringBeanJobFactory", buildSpringBeanJobFactoryClass(pkg));
        writeSourceFile("QuartzJobs", new QuartzJobsClassGenerator().generate(pkg, quartzJobs));
        return true;
    }


    private void writeSourceFile(String name, String content) {
        try {
            JavaFileObject javaFileObject = filer.createSourceFile(name);
            try (PrintWriter out = new PrintWriter(javaFileObject.openWriter())) {
                out.print(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String buildSpringBeanJobFactoryClass(String pkg) {
        ClassBuilder builder = new ClassBuilder();
        return builder.pkg(pkg)
                .clazz("AutowiringSpringBeanJobFactory extends " +
                        SpringBeanJobFactory.class.getName() + " implements " +
                        ApplicationContextAware.class.getName(), true)

                .field(AutowireCapableBeanFactory.class, "beanFactory")

                .method(null, "protected", Object.class.getName(), "createJobInstance",
                        Collections.singletonMap(TriggerFiredBundle.class.getName(), "bundle"),
                        GeneratedMethodBodies.createJobInstanceBody, Collections.singleton(Exception.class.getName()))

                .method("void", "setApplicationContext",
                        Collections.singletonMap(ApplicationContext.class.getName(), "context"), GeneratedMethodBodies.setApplicationContextBody)

                .build();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        filer = processingEnv.getFiler();
    }
}

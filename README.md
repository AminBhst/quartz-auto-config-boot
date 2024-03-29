[![license](https://img.shields.io/github/license/aminbhst/quartz-autoconfig-boot)](https://github.com/AminBhst/quartz-autoconfig-boot/blob/main/LICENSE.md)
[![release](https://img.shields.io/github/v/release/aminbhst/quartz-auto-config-boot)](https://github.com/aminbhst/quartz-auto-config-boot/releases)

## Usage Guide

#### Annotate your SpringBootApplication Class with `@EnableQuartzConfiguration`

```java

@SpringBootApplication
@EnableQuartzConfiguration
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
```

#### Annotate your job classes with `@QuartzJob` and provide either the `cron` or the `repeatInterval` property

```java

@QuartzJob(cron = "0 0/1 * * * ?")
public class TestJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        System.out.println("Running Job every minute");
    }
}
```

## Get Quartz Auto Config Boot

#### You can add Quartz AutoConfig to your project as a maven dependency

#### It is distributed through [Maven Central](https://search.maven.org/artifact/io.github.aminbhst/quartz-auto-config-boot)

### Apache Maven

```xml

<dependency>
    <groupId>io.github.aminbhst</groupId>
    <artifactId>quartz-auto-config-boot</artifactId>
    <version>1.0.0</version>
    <scope>compile</scope>
</dependency>
``` 

### Gradle

```groovy
dependencies {
    compileOnly 'io.github.aminbhst:quartz-auto-config-boot:1.0.0'
    annotationProcessor 'io.github.aminbhst:quartz-auto-config-boot:1.0.0'
}
```

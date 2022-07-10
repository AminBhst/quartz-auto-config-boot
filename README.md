## How To Use
#### Annotate your SpringBootApplication Class with @EnableQuartzConfiguration
```java

@SpringBootApplication
@EnableQuartzConfiguration
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

}
```

#### Annotate your job class with @QuartzJob and provide either the cron or the repeatInterval property
```java
@QuartzJob(cron = "0 0/1 * * * ?")
public class TestJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        System.out.println("Running Job every minute");
    }
}
```



## Get Quartz AutoConfig Boot
#### You can add Quartz AutoConfig to your project as a maven dependency
It is distributed through [Maven Central](http://search.maven.org/):
```xml
<dependency>
    <groupId>io.github.aminbhst</groupId>
    <artifactId>quartz-autoconfig-boot</artifactId>
    <version>1.0</version>
    <scope>compile</scope>
</dependency>
``` 
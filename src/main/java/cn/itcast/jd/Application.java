package cn.itcast.jd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author toxicant123
 * @Description
 * @create 2021-11-11 12:08
 */
@SpringBootApplication
//使用定时任务，需要先开启定时任务，需要添加注解
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

}

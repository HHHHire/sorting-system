package cn.edu.jxust.sort;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class SortingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SortingSystemApplication.class, args);
    }

}

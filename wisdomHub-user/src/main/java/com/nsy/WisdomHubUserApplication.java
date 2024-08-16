package com.nsy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@SpringBootApplication
public class WisdomHubUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(WisdomHubUserApplication.class, args);
    }

}

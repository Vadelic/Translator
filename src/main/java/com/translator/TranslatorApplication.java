package com.translator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class TranslatorApplication {

    public static void main(String[] args) throws ClassNotFoundException {
        SpringApplication.run(TranslatorApplication.class, args);

    }
}

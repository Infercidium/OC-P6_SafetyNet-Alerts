package com.infercidium.safetynet.config;

import com.jsoniter.JsonIterator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.InputStream;

@Configuration
public class DataBaseInit {


    public DataBaseInit() {

    }


    @Bean
    public CommandLineRunner beginInit() {
        return args -> {

            System.out.println("Initialisation");
        };
    }
}

package org.simarro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Adt6Practica1Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Adt6Practica1Application.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(Adt6Practica1Application.class);
    }

}

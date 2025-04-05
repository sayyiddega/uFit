package com.chencorp.ufit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LoginApplication {

    public static void main(String[] args) {
        // Menentukan port secara dinamis
        System.setProperty("server.port", "8085");

        ConfigurableApplicationContext context = SpringApplication.run(LoginApplication.class, args);

        // Menampilkan port yang digunakan aplikasi
        String port = context.getEnvironment().getProperty("local.server.port");
        System.out.println("Server is running on port: " + port);
    }
}

package com.github.dannil.httpdownloader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for Spring Boot application.
 *
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 2.0.0-SNAPSHOT
 */
@SpringBootApplication
public class Application {

    /**
     * Default constructor.
     */
    public Application() {

    }

    /**
     * Main method for application.
     *
     * @param args the arguments for the application
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

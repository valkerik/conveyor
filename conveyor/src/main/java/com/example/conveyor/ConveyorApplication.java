package com.example.conveyor;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Credit Conveyor Microservice",
                description = "Service for preScoring and scoring credits",
                version = "0.0.1",
                contact = @Contact(
                        name = "Silina Valeria",
                        email = "valkerik@gmail.com",
                        url = "https://github.com/valkerik"
                )
        )
)
public class ConveyorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConveyorApplication.class, args);
    }

}

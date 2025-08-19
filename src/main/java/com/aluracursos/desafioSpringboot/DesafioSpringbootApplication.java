package com.aluracursos.desafioSpringboot;

import com.aluracursos.desafioSpringboot.Principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DesafioSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioSpringbootApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(Principal principal) {
		return args -> {
			principal.muestraMenu();
		};
	}
}
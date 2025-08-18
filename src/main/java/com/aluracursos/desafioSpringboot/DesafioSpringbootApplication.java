package com.aluracursos.desafioSpringboot;

import com.aluracursos.desafioSpringboot.Principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesafioSpringbootApplication  implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DesafioSpringbootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.muestraMenu();
	}
}

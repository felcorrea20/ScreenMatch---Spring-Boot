package dev.felipe.screenmatch;

import dev.felipe.screenmatch.principal.Principal;
import org.jspecify.annotations.NullMarked;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

    @NullMarked
	@Override
	public void run(String... args) {

        var principal = new Principal();
        principal.exibeMenu();

	}
}

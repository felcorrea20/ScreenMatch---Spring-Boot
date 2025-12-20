package dev.felipe.screenmatch;

import dev.felipe.screenmatch.model.DadosSerie;
import dev.felipe.screenmatch.service.ConsumoApi;
import dev.felipe.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		var consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=eb57dae1");
		System.out.println(json);

        var converteDados = new ConverteDados();
        DadosSerie objetoDadosDaSerie = converteDados.obterDados(json, DadosSerie.class);
        System.out.println(objetoDadosDaSerie);

	}
}

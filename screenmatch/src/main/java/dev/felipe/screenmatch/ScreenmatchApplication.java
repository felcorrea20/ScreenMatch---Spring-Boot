package dev.felipe.screenmatch;

import dev.felipe.screenmatch.model.DadosEpisodio;
import dev.felipe.screenmatch.model.DadosSerie;
import dev.felipe.screenmatch.model.DadosTemporada;
import dev.felipe.screenmatch.service.ConsumoApi;
import dev.felipe.screenmatch.service.ConverteDados;
import dev.felipe.screenmatch.service.EntradaDados;
import org.jspecify.annotations.NullMarked;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

    @NullMarked
	@Override
	public void run(String... args) {

		var consumoApi    = new ConsumoApi();
		var jsonSerie     = consumoApi.obterDados("https://www.omdbapi.com/?t=behind+her+eyes&apikey=eb57dae1");
		var jsonEpisodio  = consumoApi.obterDados("https://www.omdbapi.com/?t=behind+her+eyes&season=1&episode=2&apikey=eb57dae1");
		var jsonTemporada = consumoApi.obterDados("https://www.omdbapi.com/?t=behind+her+eyes&season=1&apikey=eb57dae1");

        var converteDados          = new ConverteDados();
        var objetoDadosDaSerie     = converteDados.obterDados(jsonSerie    , DadosSerie.class    );
        var objetoDadosDoEpisodio  = converteDados.obterDados(jsonEpisodio , DadosEpisodio.class );
        var objetoDadosDaTemporada = converteDados.obterDados(jsonTemporada, DadosTemporada.class);

        System.out.println(objetoDadosDaSerie);
        System.out.println(objetoDadosDoEpisodio);
        System.out.println(objetoDadosDaTemporada);

        List<DadosTemporada> dadosTodasTemporadas = new ArrayList<>();


        for (int i = 1; i <= objetoDadosDaSerie.totalTemporadas(); i++) {
            var jsonTodasTemporadas = consumoApi.obterDados("https://www.omdbapi.com/?t=behind+her+eyes&season=" + i + "&apikey=eb57dae1");
            dadosTodasTemporadas.add(converteDados.obterDados(jsonTodasTemporadas, DadosTemporada.class));
        }

        System.out.println("Dados da serie: ");
        dadosTodasTemporadas.forEach(System.out::println);

	}
}

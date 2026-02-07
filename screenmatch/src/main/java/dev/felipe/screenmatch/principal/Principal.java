package dev.felipe.screenmatch.principal;

import dev.felipe.screenmatch.model.DadosEpisodio;
import dev.felipe.screenmatch.model.DadosSerie;
import dev.felipe.screenmatch.model.DadosTemporada;
import dev.felipe.screenmatch.model.Episodio;
import dev.felipe.screenmatch.service.ConsumoApi;
import dev.felipe.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados converteDados = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=eb57dae1";

    public void exibeMenu() {

        System.out.print("Digite o nome da serie para busca: ");
        var nomeSerie = scanner.nextLine();
        nomeSerie = nomeSerie.replace(" ", "+");

        var jsonSerie = consumoApi.obterDados(ENDERECO + nomeSerie + API_KEY);
        DadosSerie dadosSerie = converteDados.obterDados(jsonSerie, DadosSerie.class);

        List<DadosTemporada> dadosTemporadas = new ArrayList<>();

        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            var enderecoTemporada = ENDERECO + nomeSerie + "&season=" + i + API_KEY;
            var jsonTemporada = consumoApi.obterDados(enderecoTemporada);
            dadosTemporadas.add(converteDados.obterDados(jsonTemporada, DadosTemporada.class));
        }

        System.out.println(dadosSerie);
        dadosTemporadas.forEach(System.out::println);
        dadosTemporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DadosEpisodio> listaDadosEpisodio = dadosTemporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        List<Episodio> episodios = dadosTemporadas.stream()
                        .flatMap(t -> t.episodios().stream()
                                .map(d -> new Episodio(t.numeroTemporada(), d))
                        )
//                        .filter(e -> e.getAvaliacao() != 0 )
//                        .sorted(Comparator.comparing(Episodio::getAvaliacao).reversed())
//                        .limit(5)
                        .collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("A partir de qual ano você deseja ver os episódios?");
        var ano = scanner.nextInt();
        scanner.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 01, 01);

        DateTimeFormatter formatador  = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodios.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca) )
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() +
                                " Episodio: " + e.getTitulo() +
                                " Data de Lançamento: " + e.getDataLancamento().format(formatador)
                ));

    }
}

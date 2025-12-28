package dev.felipe.screenmatch.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class EntradaDados {
    Scanner scanner = new Scanner(System.in);

    public String mostraMenuPrincipal() {
        String menu = """
                    [1] Episodio
                    [2] Serie
                    [3] Temporada
                """;

        System.out.println("Consultar: ");
        System.out.print(menu);
        System.out.print("Escolha: ");
        int escolha = scanner.nextInt();

        if (escolha < 1 || escolha > 3) {
            throw new RuntimeException("Opcao invalida");
        }

        scanner.nextLine();
        System.out.print("Digite o nome da serie: ");
        String nomeSerie = scanner.nextLine();
        String endereco = "https://www.omdbapi.com/?t=" + nomeSerie;

        if (escolha != 2) {
            System.out.print("Digite o numero da temporada: ");
            int numeroTemporada = scanner.nextInt();
            endereco += "&season=" + numeroTemporada;

            if (escolha == 1) {
                System.out.print("Digite o numero do episodio: ");
                int numeroEpisodio = scanner.nextInt();
                endereco += "&episode=" + numeroEpisodio;
            }
        }

        endereco = endereco.replace(" ", "+") + "&apikey=eb57dae1";

        return endereco;

    }
}

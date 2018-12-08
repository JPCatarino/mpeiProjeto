package casasDeApostas;

import casasDeApostas.componentes.*;
import com.sun.org.apache.xpath.internal.SourceTree;
import componentes.CountingBloomFilter;
import componentes.DatasetReader;
import componentes.MinHash;

import java.awt.print.Book;
import java.util.*;

import java.util.Set;

import static casasDeApostas.componentes.BetOption.Away;
import static casasDeApostas.componentes.BetOption.Draw;
import static casasDeApostas.componentes.BetOption.Home;

/**
 * <h1>CasaDeApostasMain</h1>
 * Main program, where the interface with the user is made throughout a menu.
 * @author Jorge Catarino
 * @author Oscar Pimentel
 */

public class CasaDeApostasMain {

    /**
     * Main Function for out implementation.
     * @param args
     */

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        ArrayList<Bookmaker> listaDeCasas = new ArrayList<>();
        ArrayList<Gambler> listaDeApostadores= new ArrayList<>();

        do {
            int opcao= menu();


            switch (opcao) {

                case 1:
                    Match jogosDataStructure[] = DatasetReader.readMatches();
                    listaDeCasas = geraCasas(jogosDataStructure);
                    break;
                case 2:
                    listaCasas(listaDeCasas);
                    break;
                case 3:
                    listaDeApostadores = geraApostadoresInput();

                    break;
                case 4:
                    listaDeApostadores = geraApostadoresRandom();
                    break;
                case 5:
                    System.out.println("Qual o indice do Apostador que vai apostar?");
                    Gambler apostador = listaDeApostadores.get(inputScanner.nextInt());
                    System.out.println("Em que casa " + apostador.getNome() + " vai apostar? (por indice)");
                    Bookmaker b = listaDeCasas.get(inputScanner.nextInt());
                    fazApostadorApostar(apostador,b);
                    break;
                case 6:
                    imprimeApostadores(listaDeApostadores);
                    break;
                case 7:
                    System.out.println("Insira o index da casa");
                    int index = inputScanner.nextInt();
                    if(!(listaDeCasas.size() <= index) || index < 0){
                        Bookmaker casa = listaDeCasas.get(index);
                        System.out.println("Insira o nome do clube");
                        String team = inputScanner.next();
                        System.out.println("Estado do Jogo: 1- Vitoria, 2- Empate, 3- Derrota");
                        int ind = inputScanner.nextInt();
                        GameState gameState = null;
                        switch(ind){
                            case 1:
                                gameState = GameState.Win;
                                break;
                            case 2:
                                gameState = GameState.Draw;
                                break;
                            case 3:
                                gameState = GameState.Loss;
                                break;
                            default:
                                System.err.println("Not a valid state");
                                break;
                        }
                        System.out.println("Nome do dataset: ");
                        String fileName = inputScanner.next();
                        if(gameState != null) {
                            estimateAndPrintCorrectOdds(casa, fileName, team, gameState);
                        }
                        else{
                            System.err.println("Error: Select valid state");
                        }
                    }
                    else{
                        System.err.print("ERROR: There's no such index");
                    }
                    break;
                case 8:
                    System.out.println("De que Apostador pretender ver a lista de Apostas? (indice)");
                    listaApostasDeApostador(listaDeApostadores.get(inputScanner.nextInt()));
                    break;
                case 9:
                    System.out.println("De que Apostador pretender ver a lista de Apostas? (indice)");
                    verificaUmaAposta(listaDeApostadores.get(inputScanner.nextInt()));
                    break;
                case 10:
                    System.out.println("Qual o indice do Apostador que vai apostar?");
                    Gambler a = listaDeApostadores.get(inputScanner.nextInt());
                    System.out.println("Em que casa " + a.getNome() + " vai apostar? (por indice)");
                    Bookmaker bb = listaDeCasas.get(inputScanner.nextInt());
                    generateRandomBets(a,bb);
                    break;
                case 11:
                    verificaSeCasaTemjogosDeClube(listaDeCasas);
                    break;
                case 12:
                    System.out.println("Programa terminado com sucesso!");
                    System.exit(0);
                default:
                    System.err.println("Não é uma opção válida");
                    break;
            }
        }while (true);
    }

    /**
     * Seleciona um conjunto de jogos random do conjunto total dos jogos.
     * @param jogosDS
     * @return Set com jogos aleatórios.
     */

    public static Set<Match> selecionaConjuntoJogos(Match[] jogosDS) {
        int number = (int) (Math.random() * (jogosDS.length - 1) + (1));
        Set<Match> conjuntoDeJoosDaCasa = new LinkedHashSet<Match>();

        for (int i = 0; i < number; i++) {
            int n = new Random().nextInt(jogosDS.length);
            Match m = jogosDS[n];
            conjuntoDeJoosDaCasa.add(m);
        }
        return conjuntoDeJoosDaCasa;
    }

    /**
     * Função para gerar casas. O utilizador pode escolher quantas casas quer gerar.
     * @param jogosDS - Array com o total de jogos.
     * @return ArrayList com casas geradas.
     */

    public static ArrayList<Bookmaker> geraCasas(Match[] jogosDS) {
        Scanner input = new Scanner(System.in);
        System.out.println("Quantas casas de Apostas quer gerar?");
        int n = input.nextInt();

        ArrayList<Bookmaker> listaDeCasas = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            Set<Match> jogosDaCasa = selecionaConjuntoJogos(jogosDS);
            Bookmaker casa = new Bookmaker(jogosDaCasa);
            listaDeCasas.add(casa);
            System.out.println("Casa " + casa.getNome() + " gerada com sucesso!");
        }
        return listaDeCasas;
    }

    /**
     * Função para estimar o número de jogos de um dado clube, para qual uma casa teria as odds corretas.
     * Os jogos são retirados de outros datasets.
     * @param bookie - Casa de Apostas
     * @param fileName - Nome do dataset
     * @param team - Clue
     * @param gameState - Estado do jogo.
     */
    public static void estimateAndPrintCorrectOdds(Bookmaker bookie, String fileName, String team, GameState gameState){
        LinkedList<Match> nrJogos = DatasetReader.readMatches(team, fileName);
        String trueTeam = bookie.findSimilarTeam(team);
        if(!trueTeam.equals("")){
            int prob = bookie.estimateCorrectMatches(trueTeam,nrJogos.size(),gameState);
            System.out.println("A casa tem probabilidade de acertar as odds em " + prob + " dos jogos da equipa " + trueTeam + " no caso de " + gameState + " no dataset fornecido");
        }
        else{
            System.err.println("ERRO : Casa não possui jogos do clube " + team);
        }
    }

    public static void listaCasas(ArrayList<Bookmaker> bookmakerList){
        int count =0;
        for (Bookmaker b: bookmakerList
             ) {

            System.out.printf(count +" "+ b.getNome() + "\n");
            count++;
        }
    }

    public static ArrayList<Gambler> geraApostadoresInput(){
        Scanner input = new Scanner(System.in);

        System.out.println("Quantos Apostadores quer criar com input?");
        int n = input.nextInt();

        ArrayList<Gambler> apostadores = new ArrayList<>();
        for (int i=0; i<n; i++){
            System.out.println("Nome: ");
            String nome = input.next();
            Gambler a = new Gambler(nome);
           apostadores.add(a);

        }

        return apostadores;

    }

    public static ArrayList<Gambler> geraApostadoresRandom(){
        Scanner input = new Scanner(System.in);

        System.out.println("Quantos utilizadores quer criar Aleatóriamente?");
        int n = input.nextInt();
        ArrayList<String> nomesAleatorios = DatasetReader.readNames();

        ArrayList<Gambler> apostadores = new ArrayList<>();
        for (int i=0; i<n; i++){
            String nome = nomesAleatorios.get(new Random().nextInt(nomesAleatorios.size()));

            ArrayList<Bet> listaApostas = new ArrayList<>();
            Gambler a = new Gambler(nome);
            apostadores.add(a);

        }

        return apostadores;

    }

    public  static void imprimeApostadores(ArrayList<Gambler> apostadores){
        int count = 0;
        System.out.println("Lista de Apostadores: ");
        for (Gambler g: apostadores
             ) {System.out.println(count +" "+ g.getNome() + "\n");
                count++;

        }
    }
    public static void listaApostasDeApostador(Gambler apostador){
        for (Bet b: apostador.getListaApostas()
                ) {
            System.out.println(b);

        }

    }

    /**
     * Verifica e imprime o resultado das apostas de um dado apostador.
     * @param apostador
     */

    public static void verificaUmaAposta(Gambler apostador){
        for(Bet b : apostador.getListaApostas()){
            System.out.println("Casa: " + b.getJogo().getHome_team() + " Golos: " + b.getJogo().getHome_score());
            System.out.println("Fora: " + b.getJogo().getAway_team() + " Golos: " + b.getJogo().getAway_score());
            System.out.println("O apostador " + apostador.getNome() +" apostou em " + b.getOpcao());
            if(apostador.getListaApostasCorretas().contains(b)){
                System.out.println("O apostador " +apostador.getNome()+ " acertou a aposta");
            }
            else{
                System.out.println("O apostador "+apostador.getNome() +" errou a aposta");
                System.out.println();
            }
        }
    }

    /**
     * Função para fazer um dado apostador apostar num jogo.
     * @param apostador
     * @param casa
     */

    public static void fazApostadorApostar(Gambler apostador, Bookmaker casa) {
        Scanner input = new Scanner(System.in);
        boolean haJogo = false;

        Set<Match> teste = casa.getListaMatches();
        System.out.println(teste.iterator().next());
        System.out.println("Qual o jogo em que quer que " + apostador.getNome() + "aposte?\n");

        System.out.println("Formato: Equipa-casa, Equipa-fora");
        String jogo = input.nextLine();
        String[] match2Array = jogo.split(",");
        String equipaCasa = casa.findSimilarTeam(match2Array[0]);
        String equipaFora = casa.findSimilarTeam(match2Array[1]);

        for (Match m : casa.getListaMatches()) {
            if (m.getHome_team().equals(equipaCasa) && m.getAway_team().equals(equipaFora)) {
                apostador.makeBet(m);
                System.out.println("Aposta no jogo " + jogo + "realizada com sucesso!");
                haJogo = true;

            }

        }
        if(haJogo == false){
            System.err.println("O jogo não existe na casa");
        }

    }

    public static void generateRandomBets(Gambler g, Bookmaker b){
        for(Match m : b.getListaMatches()){
            g.makeBet(m);
        }
    }


    public static void verificaSeCasaTemjogosDeClube(ArrayList<Bookmaker> listaDeCasas) {
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Qual a casa onde quer verificar? (indice)");
        Bookmaker casa = listaDeCasas.get(inputScanner.nextInt());
        System.out.println("Qual o clube que pretende ver se existem jogos para apostar nesta casa?");
        String equipa = inputScanner.next();
        String trueEquipa = casa.findSimilarTeam(equipa);

        if (casa.getNrGames().isMember(trueEquipa)) {
            System.out.println("A equipa " + trueEquipa + " tem "+ casa.getNrGames().count(trueEquipa) + " jogos disponíveis para apostar na casa " + casa.getNome());
        } else {
            System.out.println("A equipa " + trueEquipa + " não tem jogos disponíveis para apostar na casa " + casa.getNome());
        }
    }


    public static int menu() {

        Scanner inputScanner = new Scanner(System.in);
        int opcao = -1;

        System.out.println("------------------------------------------------");
        System.out.println("|||            Casas de Apostas              |||");
        System.out.println("------------------------------------------------");
        System.out.println("|| 1->  Gerar Casas de Apostas                ||");
        System.out.println("|| 2->  Listar Casas de Apostas               ||");
        System.out.println("|| 3->  Criar Apostadores via input           ||");
        System.out.println("|| 4->  Criar Apostadores Aleatórios          ||");
        System.out.println("|| 5->  Fazer uma Aposta                      ||");
        System.out.println("|| 6->  Listar os Apostadores                 ||");
        System.out.println("|| 7->  Estimar odds corretos                 ||");
        System.out.println("|| 8->  Listar Apostas feitas por um Apostador||");
        System.out.println("|| 9->  Verificar se uma aposta está correta  ||");
        System.out.println("|| 10-> Gerar Apostas Aleatórias              ||");
        System.out.println("|| 11-> Verificar se casa tem equipa          ||");
        System.out.println("|| 12-> Terminar programa                     ||");
        System.out.println("-----------------------------------------------");
        System.out.print("Insira a sua opção->");

        return inputScanner.nextInt();
    }
}
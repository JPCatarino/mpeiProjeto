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

public class CasaDeApostasMain {

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
                    System.out.println("Em que casa " + apostador + " vai apostar? (por indice)");
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
                    System.out.println("De que Apostador pretender ver a lista de Apostas?");
                    String apostador1 = inputScanner.nextLine();
                    for (Gambler a: listaDeApostadores) {
                        if (a.getNome().equals(apostador1)) {
                            listaApostasDeApostador(a);
                        }
                    }
                       break;
                case 9:
                    System.out.println("De que Apostador pretender ver a lista de Apostas?");
                    String apostador2 = inputScanner.nextLine();
                    for (Gambler a: listaDeApostadores) {
                        if (a.getNome().equals(apostador2)) {
                            verificaUmaAposta(a);
                        }
                    }


                case 10:
                    System.out.println("Programa terminado com sucesso!");
                    System.exit(0);
                default:
                    System.err.println("Não é uma opção válida");
                    break;
            }
        }while (true);
    }


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

    public static void verificaUmaAposta(Gambler apostador){
        for(Bet b : apostador.getListaApostas()){
            System.out.println("Casa: " + b.getJogo().getHome_team() + "Golos: " + b.getJogo().getHome_score());
            System.out.println("Fora: " + b.getJogo().getAway_team() + "Golos: " + b.getJogo().getAway_score());
            System.out.println("O apostador apostou em " + b.getOpcao());
            if(apostador.getListaApostasCorretas().contains(b)){
                System.out.println("O apostador acertou a aposta");
            }
            else{
                System.out.println("O apostador errou a aposta");
            }
        }
    }



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



    public void verificaSeCasaTemjogosDeClube(Bookmaker casa, ArrayList<Bookmaker> listaDeCasas){
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Qual a casa onde quer verificar? ");
        String nomeCasa = inputScanner.nextLine();

        for (Bookmaker c:listaDeCasas) {
            if(c.getNome().equals(casa.getNome())){
                System.out.println("Qual o clube que pretende ver se existem jogos para apostar nesta casa?");
                String equipa = inputScanner.nextLine();

                if(casa.getNrGames().isMember(equipa)){
                    System.out.println("A equipa " + equipa + " tem jogos disponíveis para apostar na casa " + casa);
                }
                else{
                    System.out.println("A equipa " + equipa + " não tem jogos disponíveis para apostar na casa " + casa);
                }
            }

        }
    }


    public static int menu() {

        Scanner inputScanner = new Scanner(System.in);
        int opcao = -1;

        System.out.println("----------------------------------------------");
        System.out.println("|||           Casas de Apostas              |||");
        System.out.println("-----------------------------------------------");
        System.out.println("|| 1-> Gerar Casas de Apostas                ||");
        System.out.println("|| 2-> Listar Casas de Apostas               ||");
        System.out.println("|| 3-> Criar Apostadores via input           ||");
        System.out.println("|| 4-> Criar Apostadores Aleatórios          ||");
        System.out.println("|| 5-> Fazer uma Aposta                      ||");
        System.out.println("|| 6-> Listar os Apostadores                 ||");
        System.out.println("|| 7-> Estimar odds corretos                 ||");
        System.out.println("|| 8-> Listar Apostas feitas por um Apostador||");
        System.out.println("|| 9-> Verificar se uma aposta está correta  ||");
        System.out.println("|| 10->Terminar programa                     ||");
        System.out.println("-----------------------------------------------");
        System.out.print("Insira a sua opção->");

        return inputScanner.nextInt();
    }
}
package casasDeApostas;

import casasDeApostas.componentes.*;
import com.sun.org.apache.xpath.internal.SourceTree;
import componentes.CountingBloomFilter;
import componentes.DatasetReader;
import componentes.MinHash;

import java.util.*;

import java.util.Set;

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
                    System.out.println("Quantos Apostadores quer gerar? ");
                    listaDeApostadores = geraApostadoresInput();

                    break;
                case 4:
                    imprimeApostadores(listaDeApostadores);

                case 5:
                    System.out.println("");
                    break;

                case 10:
                    System.out.println("Programa terminado com sucesso!");
                    System.exit(0);
                default:
                    System.err.println("Não é uma opção válida");
            }
        }while (true);
    }


    public static Set<Match> selecionaConjuntoJogos(Match[] jogosDS) {
        int number = (int) (Math.random() * (jogosDS.length - 1) + (1));
        // Match listaDeEJogosSDaCasa[]= new Match[number];
        Set<Match> conjuntoDeJoosDaCasa = new LinkedHashSet<Match>();

        for (int i = 0; i < number; i++) {
            int n = new Random().nextInt(jogosDS.length);
            Match m = jogosDS[n];
            //System.out.println(n);
            //System.out.println(m.getHome_team());
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
            double prob = bookie.estimateCorrectMatches(trueTeam,nrJogos.size(),gameState);
            System.out.printf("A casa tem probabilidade de acertar as odds em %f dos jogos da equipa %s no dataset fornecido", prob,team);
        }
        else{
            System.err.println("ERRO : Casa não possui jogos do clube " + team);
        }
    }

    public static void listaCasas(ArrayList<Bookmaker> bookmakerList){
        for (Bookmaker b: bookmakerList
             ) {
            System.out.println(b.getNome());
        }
    }

    public static ArrayList<Gambler> geraApostadoresInput(){
        Scanner input = new Scanner(System.in);

        System.out.println("Quantos utilizadores quer criar com input?");
        int n = input.nextInt();

        ArrayList<Gambler> apostadores = new ArrayList<>();
        for (int i=0; i<n; i++){
            System.out.println("Nome: ");
            String nome = input.nextLine();
            System.out.println("Clube Favorito: ");
            String clube = input.nextLine();
            ArrayList<Bet> listaApostas = new ArrayList<>();
            CountingBloomFilter<String> apostasCorretas = new CountingBloomFilter<>(2, 2, 2);
            Gambler a = new Gambler(nome, clube, listaApostas);
           apostadores.add(a);

        }

        return apostadores;

    }

    public  static void imprimeApostadores(ArrayList<Gambler> apostadores){
        for (Gambler g: apostadores
             ) {System.out.println(g);}
    }


    //´TODO DÚVIDA: A função makeBet() recebe um Match. Como fazer com que o utilizador escreva esse match?
    // Ou podemos fazer a função makeBet() receber o ID do jogo em que vamos aposyat?
    public static void fazApostadorApostar(Gambler apostador, Bookmaker casa){
        Scanner input = new Scanner(System.in);


            System.out.println("Qual o jogo em que quer que " + apostador + "aposte?");
            System.out.println("Formato: Equipa-casa, Equipa-fora");
            String jogo = input.nextLine();
            String[] match2Array = jogo.split(",");
            String equipaCasa = casa.findSimilarTeam(match2Array[0]);
            String equipaFora = casa.findSimilarTeam(match2Array[1]);

        for (Match m: casa.getListaMatches()) {
            if(m.getHome_team().equals(equipaCasa) && m.getAway_team().equals(equipaFora)){
                apostador.makeBet(m);
                System.out.println("Aposta no jogo " + jogo + "realizada com sucesso!");

        }

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

    //TODO vamos precisar do ano na classe match para contar quantas vitórias a equipa conseguiu naquele ano

    /*

    public static void calculaProbabilidadeDeSerCampeaoParaOAno(String equipa){

        Match jogosDataStructure[] = DatasetReader.readMatches();

    }
    */

    public static int menu() {

        Scanner inputScanner = new Scanner(System.in);
        int opcao = -1;

        System.out.println("----------------------------------------------");
        System.out.println("|||           Casas de Apostas             |||");
        System.out.println("----------------------------------------------");
        System.out.println("|| 1-> Gerar Casas de Apostas               ||");
        System.out.println("|| 2-> Listar Casas de Apostas              ||");
        System.out.println("|| 3-> Criar Apostadores via input          ||");
        System.out.println("|| 4-> Criar Apostadores Aleatórios         ||");
        System.out.println("|| 5-> Fazer uma Aposta                     ||");
        System.out.println("|| 6-> Estimar odds corretos                ||");
        System.out.println("|| 10-> Terminar programa                   ||");
        System.out.println("----------------------------------------------");
        System.out.print("Insira a sua opção->");

        return inputScanner.nextInt();
    }
}
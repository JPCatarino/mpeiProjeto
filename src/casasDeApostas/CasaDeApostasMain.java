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
        /*
        Match jogosDataStructure[] = DatasetReader.readMatches();
        ArrayList<Bookmaker> ListaDeCasas = geraCasas(3, jogosDataStructure);

        for (Bookmaker i : ListaDeCasas) {
            HashMap<Match, double[]> aux = i.getListaJogos();
            for (Map.Entry<Match, double[]> m : aux.entrySet()) {
                if (i.isMemberCBF(m.getKey())) {
                    System.out.println(m.getKey().getHome_team());
                    System.out.println(i.isMemberCBF(m.getKey()));
                    System.out.println(Arrays.toString(aux.get(m.getKey())));
                    System.out.println("probability win: " + i.probabilityOfCorrectOdd(m.getKey().getHome_team(), GameState.Win));
                }
            }
        }
        ArrayList<Bet> listaApostas = new ArrayList<>();
        CountingBloomFilter<String> apostasCorretas = new CountingBloomFilter<>(2, 2, 2);
        Gambler Jorge = new Gambler("Jorge", "Porto", listaApostas, apostasCorretas);
        //preciso método para aceder às casas criadas


        for (Bookmaker i : ListaDeCasas) {
            HashMap<Match, double[]> aux = i.getListaJogos();
            for (Match j : aux.keySet()) {
                Jorge.makeBet(j);
            }
        }
        */
        menu();

    }


    public static Set<Match> selecionaConjuntoJogos(Match[] jogosDS) {
        int number = (int) (Math.random() * (jogosDS.length - 1) + (1));
        // Match listaDeEJogosSDaCasa[]= new Match[number];
        Set<Match> conjuntoDeJoosDaCasa = new LinkedHashSet<Match>();
        System.out.println(jogosDS.length);

        for (int i = 0; i < number; i++) {
            int n = new Random().nextInt(jogosDS.length);
            Match m = jogosDS[n];
            //System.out.println(n);
            //System.out.println(m.getHome_team());
            conjuntoDeJoosDaCasa.add(m);
        }
        return conjuntoDeJoosDaCasa;
    }


    public static ArrayList<Bookmaker> geraCasas(int numeroDeCasas, Match[] jogosDS) {
        ArrayList<Bookmaker> listaDeCasas = new ArrayList<>();

        for (int i = 0; i < numeroDeCasas; i++) {
            Set<Match> jogosDaCasa = selecionaConjuntoJogos(jogosDS);
            Bookmaker casa = new Bookmaker(jogosDaCasa);
            listaDeCasas.add(casa);
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
            System.out.println(b);
        }
    }

    public static ArrayList<Gambler> geraApostadores(int n){
        Scanner input = new Scanner(System.in);
        ArrayList<Gambler> apostadores = new ArrayList<>();
        for (int i=0; i<n; i++){
            System.out.println("Nome: ");
            String nome = input.nextLine();
            System.out.println("Clube Favorito: ");
            String clube = input.nextLine();
            ArrayList<Bet> listaApostas = new ArrayList<>();
            CountingBloomFilter<String> apostasCorretas = new CountingBloomFilter<>(2, 2, 2);
            Gambler a = new Gambler(nome, clube, listaApostas, apostasCorretas);
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

        HashMap<Match, double[]> jogosDaCasa = casa.getListaJogos();

            System.out.println("Qual o jogo em que quer que " + apostador + "aposte?");
            System.out.println("Formato: Equipa-casa, Equipa-fora");
            String jogo = input.nextLine();
            String[] match2Array = jogo.split(",");

            MinHash cmp = new MinHash(100);


            for(Map.Entry<Match, double[]> m : jogosDaCasa.entrySet()) {
               // int[] toCompare1 = cmp.getSignature(MinHash.shingleTextPairs(match[2]));
                //int[] toCompare2 = cmp.getSignature(MinHash.shingleTextPairs(match[3]));





        }





        for (Map.Entry<Match, double[]> m : jogosDaCasa.entrySet()) {
            if(m.equals(jogo)){
                //apostador.makeBet(jogo);
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

    public static void menu() {

        Scanner inputScanner = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 6) {
            System.out.println("---------------------------------------------");
            System.out.println("|||           Casas de Apostas           |||");
            System.out.println("---------------------------------------------");
            System.out.println("|| 1-> Gerar e Listar Casas de Apostas     ||");
            System.out.println("|| 2-> Gerar e Listar Apostadores          ||");
            System.out.println("|| 3-> Fazer um jogador apostar num jogo   ||");
            System.out.println("|| 4->                                     ||");
            System.out.println("|| 5->                                     ||");
            System.out.println("|| 6-> Terminar programa                   ||");
            System.out.println("---------------------------------------------");
            System.out.print("Insira a sua opção->");

            try {
                opcao = inputScanner.nextInt();
            } catch (Exception var4) {
                System.out.println("Opção inválida!");
                System.exit(0);
            }

            switch (opcao) {

                case 1:
                    Match jogosDataStructure[] = DatasetReader.readMatches();
                    ArrayList<Bookmaker> listaDeCasas = geraCasas(1, jogosDataStructure);
                    listaCasas(listaDeCasas);
                    break;
                case 2:
                    System.out.println("Quantos Apostadores quer gerar? ");
                    int n = inputScanner.nextInt();
                    ArrayList<Gambler> listaDeApostadores =geraApostadores(n);
                    imprimeApostadores(listaDeApostadores);

                    break;
                case 3:

                    break;
                case 4:

                case 5:

                    break;
                case 6:
                    System.out.println("Programa terminado com sucesso!");

            }


        }
    }
}
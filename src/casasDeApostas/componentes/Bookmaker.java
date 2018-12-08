package casasDeApostas.componentes;

import componentes.ContadorEstocastico;
import componentes.CountingBloomFilter;
import componentes.MinHash;

import java.util.*;

/**
 * <h1>Bookmaker</h1>
 * @author Jorge Catarino
 * @author Oscar Pimentel
 */
public class Bookmaker {
    public static String nomes[] = {"Bet365", "Betcris","Betfair", "Betfred", "BetOnline", "Betsson","BetUS", "BetVictor","Betway" ,"Boylesports", "Bwin", "Centrebet","Dafabet","Eurofootball","Gala Coral Group","Ladbrokes","Liga Stavok","Marathonbet","Paddy Power","Pinnacle Sports","SBOBET","Sky Bet","Sportingbet","Stan James","Star Sports", "The Tote","Unibet","William Hill"};
    private String nome;
    private HashMap<Match,double[]> listaJogos;
    private HashMap<String,int[]> listaJogosPorEquipa;
    private Set<Match> listaMatches;
    private CountingBloomFilter<Match> correctOdds;         // Jogos com odds corretas, ainda hei de pensar num uso
    private CountingBloomFilter<String> nrGames;            // nr jogos por equipa
    private CountingBloomFilter<String> correctWins;        // Conta quantas vezes as odds estavam corretas para a vitória de uma equipa
    private CountingBloomFilter<String> correctLoss;        // Conta quantas vezes as odds estavam corretas para a derrota de uma equipa
    private CountingBloomFilter<String> correctDraws;       // Conta quantas vezes as odds estavam corretas para o empate de uma equipa

   /**
    * Class Constructor for Bookmaker, the place were you make bets.
     * @param listaJogos List of matches that the Bookmaker offers you as options to bet.
    */

    public Bookmaker(Set<Match> listaJogos){
        this.nome = nomes[new Random().nextInt(nomes.length)];
        this.listaJogos = new HashMap<>();
        this.listaMatches = listaJogos;
        this.listaJogosPorEquipa = new HashMap<>();
        populateListaJogos(listaJogos);
        this.nrGames = new CountingBloomFilter(listaJogos.size() * 15, 32, CountingBloomFilter.calculateOptimalK(listaJogos.size() * 15, listaJogos.size()));
        this.correctOdds = new CountingBloomFilter(listaJogos.size() * 15, 32, CountingBloomFilter.calculateOptimalK(listaJogos.size() * 15, listaJogos.size()));
        insertNrMatches();
        insertCorrectOdds();
    }

    public CountingBloomFilter<Match> getCorrectOdds() {
        return correctOdds;
    }

    public CountingBloomFilter<String> getNrGames() {
        return nrGames;
    }

    public CountingBloomFilter<String> getCorrectWins() {
        return correctWins;
    }

    public CountingBloomFilter<String> getCorrectLoss() {
        return correctLoss;
    }

    public CountingBloomFilter<String> getCorrectDraws() {
        return correctDraws;
    }

    //gera as odds para uma determinada equipa
    private double oddGenerator(){
        return (Math.random()*(12- 0.1))+0.1;
   }

    public String getNome() {
        return nome;
    }

    public HashMap<Match,double[]> getListaJogos() {
        return listaJogos;
    }

    public void populateListaJogos(Set<Match> listaJogos){
        for(Match i : listaJogos){
            double aux[] = {oddGenerator(),oddGenerator(),oddGenerator()};
            this.listaJogos.put(i,aux);
        }
    }

    public double probabilityOfCorrectOdd(String equipa, GameState gameState){
        if(nrGames.count(equipa)>0) {
            switch (gameState) {
                case Win:
                    if(listaJogosPorEquipa.get(equipa)[0] > 0)
                        return correctWins.count(equipa) / listaJogosPorEquipa.get(equipa)[0];
                case Draw:
                    if(listaJogosPorEquipa.get(equipa)[1] > 0)
                        return correctDraws.count(equipa) / listaJogosPorEquipa.get(equipa)[1];
                case Loss:
                    if(listaJogosPorEquipa.get(equipa)[2] > 0)
                        return correctLoss.count(equipa) / listaJogosPorEquipa.get(equipa)[2];
            }
        }
        return 0;
    }

    public boolean isMemberCBF(Match toCheck){
        return correctOdds.isMember(toCheck);
    }

    public int estimateCorrectMatches(String team, int size, GameState gameState){
        return ContadorEstocastico.contadorEstocastico(size, probabilityOfCorrectOdd(team, gameState));
    }

    private void insertNrMatches(){
        for(Match i : listaMatches){
            nrGames.insertElem(i.getHome_team());
            nrGames.insertElem(i.getAway_team());
            listaJogosPorEquipa.put(i.getHome_team(), new int[] {0,0,0});
            listaJogosPorEquipa.put(i.getAway_team(), new int[] {0,0,0});
        }
    }

    private void insertCorrectOdds() {
        Iterator iterator = listaJogos.entrySet().iterator();
        LinkedList<String> correctWins = new LinkedList<>(), correctDraws = new LinkedList<>(), correctLoss = new LinkedList<>();
        while (iterator.hasNext()) {
            Map.Entry<Match, double[]> p = (Map.Entry<Match, double[]>) iterator.next();
            BetOption gameState = p.getKey().findStateGame();
            switch (gameState) {
                    case Home:
                        int[] aux1 = listaJogosPorEquipa.get(p.getKey().getHome_team());
                        aux1[0]++;
                        listaJogosPorEquipa.put(p.getKey().getHome_team(), aux1);

                        int[] aux2 = listaJogosPorEquipa.get(p.getKey().getAway_team());
                        aux2[2]++;
                        listaJogosPorEquipa.put(p.getKey().getAway_team(), aux2);

                        if (p.getValue()[0] < p.getValue()[1] && p.getValue()[0] < p.getValue()[2]) {
                            correctOdds.insertElem(p.getKey());
                            correctWins.add(p.getKey().getHome_team());
                            correctLoss.add(p.getKey().getAway_team());
                        }
                        break;
                    case Draw:
                        int[] aux3 = listaJogosPorEquipa.get(p.getKey().getHome_team());
                        aux3[1]++;
                        listaJogosPorEquipa.put(p.getKey().getHome_team(), aux3);

                        int[] aux4 = listaJogosPorEquipa.get(p.getKey().getAway_team());
                        aux4[1]++;
                        listaJogosPorEquipa.put(p.getKey().getAway_team(), aux4);

                        if (p.getValue()[1] < p.getValue()[0] && p.getValue()[1] < p.getValue()[2]) {
                            correctOdds.insertElem(p.getKey());
                            correctDraws.add(p.getKey().getHome_team());
                            correctDraws.add(p.getKey().getAway_team());
                        }
                        break;
                    case Away:
                        int[] aux5 = listaJogosPorEquipa.get(p.getKey().getHome_team());
                        aux5[2]++;
                        listaJogosPorEquipa.put(p.getKey().getHome_team(), aux5);

                        int[] aux6 = listaJogosPorEquipa.get(p.getKey().getAway_team());
                        aux6[0]++;
                        listaJogosPorEquipa.put(p.getKey().getAway_team(), aux6);
                        if (p.getValue()[2] < p.getValue()[0] && p.getValue()[2] < p.getValue()[1]) {
                            correctOdds.insertElem(p.getKey());
                            correctWins.add(p.getKey().getAway_team());
                            correctLoss.add(p.getKey().getHome_team());
                        }
                }
            }
        this.correctWins = new CountingBloomFilter<>(correctWins.size() * 15, 32, CountingBloomFilter.calculateOptimalK(correctWins.size() * 15, correctWins.size()));
        this.correctDraws = new CountingBloomFilter<>(correctDraws.size() * 15, 32, CountingBloomFilter.calculateOptimalK(correctDraws.size() * 15, correctDraws.size()));
        this.correctLoss = new CountingBloomFilter<>(correctLoss.size() * 15, 32, CountingBloomFilter.calculateOptimalK(correctLoss.size() * 15, correctLoss.size()));

        for(String i : correctWins){
            this.correctWins.insertElem(i);
        }
        for(String i : correctDraws){
            this.correctDraws.insertElem(i);
        }
        for(String i : correctLoss){
            this.correctLoss.insertElem(i);
        }
    }

    public String findSimilarTeam(String team){
        MinHash cmp = new MinHash(100);
        int[] sigTeam = cmp.getSignature(MinHash.shingleTextPairs(team));
        for(Match i : listaMatches){
            int[] toCompare1 = cmp.getSignature(MinHash.shingleTextPairs(i.getHome_team()));
            int[] toCompare2 = cmp.getSignature(MinHash.shingleTextPairs(i.getAway_team()));
            if(cmp.calculateSimilarity(sigTeam,toCompare1) > 0.7){
                return i.getHome_team();
            }
            else if(cmp.calculateSimilarity(sigTeam,toCompare2) > 0.7){
                return i.getAway_team();
            }
        }
        return "";
    }

    public boolean verificarSeCasaTemJogosDoClube(String clube){
        return nrGames.isMember(clube);
    }

    @Override
    public String toString() {
        return "Bookmaker{" +
                "nome='" + nome + '\'' +
                ", listaJogos=" + listaJogos +
                ", correctOdds=" + correctOdds +
                '}';
    }


}

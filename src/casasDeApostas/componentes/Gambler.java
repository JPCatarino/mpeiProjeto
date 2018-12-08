package casasDeApostas.componentes;

import componentes.CountingBloomFilter;
import componentes.MinHash;

import java.util.*;

import static casasDeApostas.componentes.BetOption.*;

/**
 * <h1>Gambler</h1>
 * @author Oscar Pimentel
 */
public class Gambler {
    private String nome;
    private Set<Bet> listaApostas;
    private Set<Bet> listaApostasCorretas;
    private CountingBloomFilter<Match> apostasCcorretas;
    private String clubeFavorito;


    /**
     * Class Constructor for Bookmaker, the place were you make bets.
     * @param nome Name of the Gambler.
     */

    public Gambler(String nome) {
        this.nome = nome;
        this.clubeFavorito = clubeFavorito;
        this.listaApostas = new HashSet<>();
        this.listaApostasCorretas = new HashSet<>();
        this.apostasCcorretas = new CountingBloomFilter<>(300000 * 15, 32, CountingBloomFilter.calculateOptimalK(300000 * 15, 300000));
    }

    public String getNome() {
        return nome;
    }



    public Set<Bet> getListaApostas() {
        return listaApostas;
    }

    public CountingBloomFilter<Match> getApostasCcorretas() {
        return apostasCcorretas;
    }


    @Override
    public String toString() {
        return "Gambler{" +
                "nome='" + nome + '\'' +
                ", listaApostas=" + listaApostas +
                ", apostasCcorretas=" + apostasCcorretas +
                '}';
    }

    public String makeBet(Match jogo) {

        String homeTeam = jogo.getHome_team();
        String awayTeam = jogo.getAway_team();
        String[] matchOptions = {homeTeam, awayTeam, "Draw"};
        //consideramos que o apostador aposta de forma aleatória (?)
        String escolhaEquipa = matchOptions[new Random().nextInt(matchOptions.length)];

        if (escolhaEquipa.equals(jogo.getHome_team())) {
            Bet aposta = new Bet(jogo, Home);
            listaApostas.add(aposta);
            System.out.println("Aposta feita na equipa da casa.");
        } else if (escolhaEquipa.equals(jogo.getAway_team())) {
            Bet aposta = new Bet(jogo, BetOption.Away);
            listaApostas.add(aposta);
            System.out.println("Aposta feita na equipa de fora.");
        } else if (escolhaEquipa.equals("Draw")) {
            Bet aposta = new Bet(jogo, BetOption.Draw);
            System.out.println("Aposta feita no empate.");
            listaApostas.add(aposta);
        }
        checkCorrectBets();
        return escolhaEquipa;

    }

    public static void compareTwoGamblers(Gambler g1, Gambler g2){
        MinHash cmp = new MinHash(100);
        Set<Bet> d1 = g1.getListaApostasCorretas();
        Set<Bet> d2 = g2.getListaApostasCorretas();

        HashMap<Bet, int[]> signatures = new HashMap<>();

        for(Bet i : d1){
            signatures.put(i, cmp.getSignature(MinHash.shingleTextPairs(i.toString())));
        }
        for(Bet i: d2){
            signatures.put(i, cmp.getSignature(MinHash.shingleTextPairs(i.toString())));
        }

        double similarities[][] = new double[d1.size()][d2.size()];

        for(int row = 0; row < d1.size(); row++){
            for(int column = 0; column < d2.size(); column++){
                similarities[row][column] = row > column ? 0 : cmp.calculateSimilarity(signatures.get(iterate(d1,row)), signatures.get(iterate(d2,column)));
            }
        }
    }

    private void checkCorrectBets(){
        for(Bet i : listaApostas){
            switch(i.getOpcao()){
                case Home:
                    if(i.getJogo().getHome_score() > i.getJogo().getAway_score()){
                        listaApostasCorretas.add(i);
                    }
                case Draw:
                    if(i.getJogo().getHome_score() == i.getJogo().getAway_score()){
                        listaApostasCorretas.add(i);
                    }
                case Away:
                    if(i.getJogo().getAway_score() > i.getJogo().getHome_score()){
                        listaApostasCorretas.add(i);
                    }
            }
        }
    }



    private static Bet iterate(Set<Bet> s, int i){
        Iterator e = s.iterator();
        while(i != 0){
            e.next();
        }
        return (Bet)e.next();
    }

    public Set<Bet> getListaApostasCorretas() {
        return listaApostasCorretas;
    }
}

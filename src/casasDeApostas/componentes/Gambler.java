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

    /**
     * Method to create a random bet to given game.
     * @param jogo - Game to create a bet for.
     * @return
     */
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

    /**
     * Method to check which bets are correct. It get the state the gambler bet and compares the results of the game.
     */
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

    public Set<Bet> getListaApostasCorretas() {
        return listaApostasCorretas;
    }
}

package casasDeApostas.componentes;

import componentes.CountingBloomFilter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * <h1>Gambler</h1>
 * @author Oscar Pimentel
 */
public class Gambler {
    private String nome;
    private ArrayList<Bet> listaApostas;
    private CountingBloomFilter<String> apostasCcorretas;

    public Gambler(String nome, String clubeFavorito, ArrayList<Bet> listaJogosEmQueApostou, CountingBloomFilter<String> apostasCcorretas){
        this.nome = nome;
        this.listaApostas = listaJogosEmQueApostou;
        this.apostasCcorretas = apostasCcorretas;
    }

    public String getNome() {
        return nome;
    }



    public ArrayList<Bet> getListaApostas() {
        return listaApostas;
    }

    public CountingBloomFilter<String> getApostasCcorretas() {
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
            Bet aposta = new Bet(jogo, BetOption.Home);
            listaApostas.add(aposta);
        } else if (escolhaEquipa.equals(jogo.getAway_team())) {
            Bet aposta = new Bet(jogo, BetOption.Away);
            listaApostas.add(aposta);
        } else if (escolhaEquipa.equals("Draw")) {
            Bet aposta = new Bet(jogo, BetOption.Draw);
            listaApostas.add(aposta);
        }
        return escolhaEquipa;

    }
}

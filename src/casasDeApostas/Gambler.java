package casasDeApostas;

import componentes.CountingBloomFilter;

import java.util.LinkedList;

/**
 * Created by Media Markt on 04/12/2018.
 */
public class Gambler {
    private String nome;
    private String clubeFavorito;
    private LinkedList<Match> listaJogosEmQueApostou = new LinkedList<>();
    private CountingBloomFilter<String> apostasCcorretas;

    public Gambler(String nome, String clubeFavorito, LinkedList<Match> listaJogosEmQueApostou, CountingBloomFilter<String> apostasCcorretas){
        this.nome = nome;
        this.clubeFavorito = clubeFavorito;
        this.listaJogosEmQueApostou = listaJogosEmQueApostou;
        this.apostasCcorretas = apostasCcorretas;


    }

    public String getNome() {
        return nome;
    }

    public String getClubeFavorito() {
        return clubeFavorito;
    }

    public LinkedList<Match> getListaJogosEmQueApostou() {
        return listaJogosEmQueApostou;
    }

    public CountingBloomFilter<String> getApostasCcorretas() {
        return apostasCcorretas;
    }

    @Override
    public String toString() {
        return "Gambler{" +
                "nome='" + nome + '\'' +
                ", clubeFavorito='" + clubeFavorito + '\'' +
                ", listaJogosEmQueApostou=" + listaJogosEmQueApostou +
                ", apostasCcorretas=" + apostasCcorretas +
                '}';
    }
}

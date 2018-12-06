package casasDeApostas.componentes;

import componentes.CountingBloomFilter;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * <h1>Gambles</h1>
 * @author Oscar Pimentel
 */
public class Gambler {
    private String nome;
    private ArrayList<Bet> listaApostas = new ArrayList<>();
    private CountingBloomFilter<String> apostasCcorretas;

    public Gambler(String nome, String clubeFavorito, LinkedList<Bet> listaJogosEmQueApostou, CountingBloomFilter<String> apostasCcorretas){
        this.nome = nome;
        this.listaApostas = listaApostas;
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
}

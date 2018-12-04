package casasDeApostas;

import java.util.LinkedList;

/**
 * Created by Media Markt on 27/11/2018.
 */
public class Bookmaker {
    private String nome;
    private LinkedList<Match> listaJogos = new LinkedList<>();

    public Bookmaker(String nome, LinkedList<Match> listaJogos){
        this.nome = nome;
        this.listaJogos = listaJogos;

   }

    //gera as odds para uma determinada equipa
   public double oddGenerator(){
        return (Math.random()*(12- 0.1))+0.1;
   }

    public String getNome() {
        return nome;
    }

    public LinkedList<Match> getListaJogos() {
        return listaJogos;
    }

}

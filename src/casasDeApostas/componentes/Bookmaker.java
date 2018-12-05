package casasDeApostas.componentes;

import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Media Markt on 27/11/2018.
 */
public class Bookmaker {
    public static String nomes[] = {"Bet365", "Betcris","Betfair", "Betfred", "BetOnline", "Betsson","BetUS", "BetVictor","Betway" ,"Boylesports", "Bwin", "Centrebet","Dafabet","Eurofootball","Gala Coral Group","Ladbrokes","Liga Stavok","Marathonbet","Paddy Power","Pinnacle Sports","SBOBET","Sky Bet","Sportingbet","Stan James","Star Sports", "The Tote","Unibet","William Hill"};
    private String nome = nomes[new Random().nextInt(nomes.length)];
    private Set<Match> listaJogos = new TreeSet<>();

    public Bookmaker(Set<Match> listaJogos){
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

    public Set<Match> getListaJogos() {
        return listaJogos;
    }

}

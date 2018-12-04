package casasDeApostas.componentes;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Media Markt on 27/11/2018.
 */
public class Bookmaker {
    public static String nomes[] = {"Bet365", "Betcris","Betfair", "Betfred", "BetOnline", "Betsson","BetUS", "BetVictor","Betway" ,"Boylesports", "Bwin", "Centrebet","Dafabet","Eurofootball","Gala Coral Group","Ladbrokes","Liga Stavok","Marathonbet","Paddy Power","Pinnacle Sports","SBOBET","Sky Bet","Sportingbet","Stan James","Star Sports", "The Tote","Unibet","William Hill"};
    private String nome = nomes[new Random().nextInt(nomes.length)];
    private LinkedList<Match> listaJogos = new LinkedList<>();

    public Bookmaker(LinkedList<Match> listaJogos){
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

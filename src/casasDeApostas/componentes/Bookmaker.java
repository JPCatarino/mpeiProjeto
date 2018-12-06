package casasDeApostas.componentes;

import componentes.CountingBloomFilter;
import org.apache.commons.lang3.ObjectUtils;

import java.util.*;

/**
 * Created by Media Markt on 27/11/2018.
 */
public class Bookmaker {
    public static String nomes[] = {"Bet365", "Betcris","Betfair", "Betfred", "BetOnline", "Betsson","BetUS", "BetVictor","Betway" ,"Boylesports", "Bwin", "Centrebet","Dafabet","Eurofootball","Gala Coral Group","Ladbrokes","Liga Stavok","Marathonbet","Paddy Power","Pinnacle Sports","SBOBET","Sky Bet","Sportingbet","Stan James","Star Sports", "The Tote","Unibet","William Hill"};
    private String nome = nomes[new Random().nextInt(nomes.length)];
    private HashMap<Match,double[]> listaJogos;
    private Set<Match> listaMatches;
    private CountingBloomFilter correctOdds;

    public Bookmaker(Set<Match> listaJogos){
        this.nome = nome;
        this.listaJogos = new HashMap<>();
        this.listaMatches = listaJogos;
        populateListaJogos(listaJogos);
        this.correctOdds = new CountingBloomFilter(500000 * 15, 32, 500000 * 15 / 500000); //change this values laer with optimal ones
        insertCorrectOdds();
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

    public long checkCBF(String toCheck){
        return correctOdds.count(toCheck);
    }

    // Maybe we should insert the actual matches on the bloom
    private void insertCorrectOdds() {
        Iterator iterator = listaJogos.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Match, double[]> p = (Map.Entry<Match, double[]>) iterator.next();
            BetOption gameState = p.getKey().findStateGame();
            switch (gameState) {
                    case Home:
                        if (p.getValue()[0] < p.getValue()[1] && p.getValue()[0] < p.getValue()[2]) {
                            correctOdds.insertElem(p.getKey().getHome_team());
                        }
                        break;
                    case Draw:
                        if (p.getValue()[1] < p.getValue()[0] && p.getValue()[1] < p.getValue()[2]) {
                            //ver o que meter aqui
                        }
                        break;
                    case Away:
                        if (p.getValue()[2] < p.getValue()[0] && p.getValue()[2] < p.getValue()[1]) {
                            correctOdds.insertElem(p.getKey().getAway_team());
                        }
                }
            }
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

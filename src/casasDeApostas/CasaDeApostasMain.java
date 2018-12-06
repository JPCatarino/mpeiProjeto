package casasDeApostas;

import casasDeApostas.componentes.Bet;
import casasDeApostas.componentes.Bookmaker;
import casasDeApostas.componentes.Match;
import componentes.DatasetReader;

import java.util.*;

import java.util.Set;

public class CasaDeApostasMain {

    public static void main(String[] args) {
        Match jogosDataStructure[] = DatasetReader.readMatches();
        ArrayList<Bookmaker> ListaDeCasas = geraCasas(3, jogosDataStructure);

        for(Bookmaker i: ListaDeCasas){
            HashMap<Match,double[]> aux = i.getListaJogos();
            for(Map.Entry<Match, double[]> m : aux.entrySet()){
                try {
                    if(i.checkCBF(m.getKey().getHome_team()) > 0) {
                        System.out.println(Arrays.toString(aux.get(m.getKey())));
                    }
                }
                catch(NullPointerException e){
                    e.printStackTrace();
                }
            }
        }

    }

    public static Set<Match> selecionaConjuntoJogos(Match[] jogosDS){
        int number = (int) (Math.random()*(jogosDS.length-1)+(1));
       // Match listaDeEJogosSDaCasa[]= new Match[number];
        Set<Match> conjuntoDeJoosDaCasa = new LinkedHashSet<Match>();
        System.out.println(number);

        for(int i=0; i< number; i++){
           Match m = jogosDS[new Random().nextInt(jogosDS.length)];
           conjuntoDeJoosDaCasa.add(m);

        }
        return conjuntoDeJoosDaCasa;
    }



    public static ArrayList<Bookmaker> geraCasas(int numeroDeCasas, Match[] jogosDS){
        ArrayList<Bookmaker> listaDeCasas = new ArrayList<>();

        for(int i =0; i< numeroDeCasas; i++){
            Set <Match> jogosDaCasa = selecionaConjuntoJogos(jogosDS);
            Bookmaker casa = new Bookmaker(jogosDaCasa);
            listaDeCasas.add(casa);
        }
        return listaDeCasas;
}

 /*   public static ArrayList<Bet> distribuiJogosPorCasas(Match[] jogos){

        for (Match m: jogos) {
          // opcao(m);

        }



    }*/




}
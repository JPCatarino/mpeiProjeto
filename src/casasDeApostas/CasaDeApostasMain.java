package casasDeApostas;

import casasDeApostas.componentes.Bet;
import casasDeApostas.componentes.Bookmaker;
import casasDeApostas.componentes.Match;
import componentes.DatasetReader;

import java.util.ArrayList;

public class CasaDeApostasMain {

    public static void main(String[] args) {
        Match jogosDataStructure[] = DatasetReader.readMatches();


    }

    public Match[] selecionaConjuntoJogos(Match[] jogosDS){
        int number = (int) (Math.random()*(jogosDS.length-1)+(1));

        for(int i=0; i< number; i++){
            
        }

    }



    public ArrayList<Bookmaker> geraCasas(int numeroDeCasas, Match[] jogosDS){

        for(int i =0; i< numeroDeCasas; i++){
            Bookmaker casa = new Bookmaker();
        }
}

    public static ArrayList<Bet> distribuiJogosPorCasas(Match[] jogos){

        for (Match m: jogos) {
          // opcao(m);

        }



    }




}
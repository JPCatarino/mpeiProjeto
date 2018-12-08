package testes;
import casasDeApostas.componentes.*;
import casasDeApostas.CasaDeApostasMain;
import componentes.*;

import java.util.*;

public class testImplementationFunctions {

    public static void main(String args[]){
        testEstimateCorrectOdds();
    }

    public static void testEstimateCorrectOdds(){
        LinkedList<Match> matchesList = DatasetReader.readMatches("Porto", "ligaPT1718.csv");
        Set<Match> matches = new LinkedHashSet<>();
        for(Match i: matchesList){
            matches.add(i);
        }
        matchesList = DatasetReader.readMatches("Benfic", "ligaPT1718.csv");
        for(Match i: matchesList){
            matches.add(i);
        }

        Bookmaker n1 = new Bookmaker(matches);

        CasaDeApostasMain.estimateAndPrintCorrectOdds(n1,"ligaPT1718.csv","Porto", GameState.Win);
        CasaDeApostasMain.estimateAndPrintCorrectOdds(n1,"ligaPT1718.csv","Benfica", GameState.Loss);

    }
}

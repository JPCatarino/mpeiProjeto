package componentes;

import casasDeApostas.componentes.Match;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;


public class DatasetReader {
    private static String csvPath = "src\\componentes\\dataset\\closing_odds.csv";
    private static String csvSplit = ",";


    public static Match[] readMatches(){
        BufferedReader nReader;
        String line;
        Match[] readMatches = new Match[500000];
        int i = 0;
        try{
            nReader = new BufferedReader(new FileReader(csvPath));
            nReader.readLine();
            while((line = nReader.readLine()) != null){
                String[] match = line.split(csvSplit);
                readMatches[i] = new Match(match[3],Integer.parseInt(match[4]), match[5],Integer.parseInt(match[6]),Integer.parseInt(match[0]), match[1]);
                i++;
            }
            nReader.close();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return readMatches;
    }

}

package componentes;

import casasDeApostas.componentes.Match;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;


public class DatasetReader {
    private static String csvPath = "src\\componentes\\dataset\\closing_odds.csv";
    private static String namesPath = "src\\componentes\\dataset\\NationalNames.csv";
    private static String datasetPath = "src\\componentes\\dataset\\";
    private static String csvSplit = ",";


    public static Match[] readMatches(){
        BufferedReader nReader;
        String line;
        Match[] readMatches = new Match[479440];
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

    public static LinkedList<Match> readMatches(String team, String filePath){
        BufferedReader nReader;
        String line;
        MinHash cmp = new MinHash(100);
        int[] clubSign = cmp.getSignature(MinHash.shingleTextPairs(team));
        LinkedList<Match> clubMatches = new LinkedList<>();

        try{
            nReader = new BufferedReader(new FileReader(datasetPath+filePath));
            nReader.readLine();
            while((line = nReader.readLine())!= null) {
                String[] match = line.split(csvSplit);
                int[] toCompare1 = cmp.getSignature(MinHash.shingleTextPairs(match[2]));
                int[] toCompare2 = cmp.getSignature(MinHash.shingleTextPairs(match[3]));
                if(cmp.calculateSimilarity(clubSign, toCompare1) > 0.7){
                    clubMatches.add(new Match(match[2],Integer.parseInt(match[4]),match[3],Integer.parseInt(match[5]),0, match[0]));
                }
                else if(cmp.calculateSimilarity(clubSign, toCompare2) > 0.7){
                    clubMatches.add(new Match(match[2],Integer.parseInt(match[4]),match[3],Integer.parseInt(match[5]),0, match[0]));
                }
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return clubMatches;
    }

    public static ArrayList<String> readNames() {
        BufferedReader nReader;
        String line;
        ArrayList<String> nomes = new ArrayList<>();

        try {
            nReader = new BufferedReader(new FileReader(namesPath));
            nReader.readLine();
            while ((line = nReader.readLine()) != null) {
                String[] s = line.split(csvSplit);
                nomes.add(s[1]);

            }

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return nomes;
    }



}

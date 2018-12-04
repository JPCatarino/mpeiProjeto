package testes;

// Adaptado do guião 6 (Bloom Filters).

import componentes.CountingBloomFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class testeCBF {
    public static void main(String[] args){
        System.out.println("The Count of Monte Cristo Word Searcher");
        Scanner sc = new Scanner(System.in);
        File book = new File("src\\testes\\1184-0.txt");
        int nmWords = countWordFile(book);
        System.out.printf("Fact: The book has %d words!\n", nmWords);
        CountingBloomFilter<String> cbf = new CountingBloomFilter<>(nmWords*15,32,CountingBloomFilter.calculateOptimalK(nmWords*15,nmWords));
        System.out.println("Inserting words in Counting Bloom Filter...");
        insertBookInCBF(book,cbf);
        while(true) {
            System.out.println("Insert the word you wish to search");
            System.out.print("> ");
            String procura = sc.nextLine();
            System.out.println(procura);
            System.out.printf("The words appears %s at least %d times\n", procura, cbf.count(procura));
        }
    }

    public static int countWordFile(File file){
        BufferedReader read;
        int counterWords = 0;
        try{
            read = new BufferedReader(new FileReader(file));
            String line;
            while((line = read.readLine()) != null){
                String[] aux = line.split("\\s");
                counterWords += aux.length;
            }
            read.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return counterWords;
    }

    public static void insertBookInCBF(File file, CountingBloomFilter cbf){
        BufferedReader read;
        try{
            read = new BufferedReader(new FileReader(file));
            String line;
            while((line = read.readLine()) != null){
                String[] aux = line.split("\\s");       //TODO: ignore commas and other punctuation!
                for(String word : aux){
                    cbf.insertElem(word);
                }
            }
            read.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}

package testes;

import componentes.MinHash;
import MovieLensExample.MlUser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;

/*
 * Teste baseado no exercicio 3 do Guião de MinHash
 */

public class testeMinHash {
    public static void main(String[] args){
        String path = "src\\MovieLensExample\\dataset\\u.data";
        HashMap<String, MlUser> listaDeUsers = new HashMap<>();
        int rows = 100000;                                  // TODO: Find a way not to hardcode file size

        // A ler ficheiro
        System.out.println("Reading File...");
        String[][] udata = readData(path,rows);
        HashMap<String, LinkedHashSet<Integer>> ShingledDocs = new HashMap<>();


        /*for(int i = 0; i < udata.length; i++) {
            for(int x = 0; x < 2; x++){
                System.out.print(udata[i][x] + " ");
            }
            System.out.println("\n");
        }*/

        // Vai buscar os usuários a data
        System.out.println("Getting unique users...");
        String[] users = getColumn(udata, 0);
        users = uniqueElems(users);


        // Associa dados a cada utilizador
        System.out.println("Adding data to each user...");
        for(int i = 0; i < users.length; i++){
            listaDeUsers.put(users[i], new MlUser(users[i]));
        }

        for(int i = 0; i < udata.length; i++) {
            listaDeUsers.get(udata[i][0]).addData(udata[i][1]);
        }
        udata = null;                               // cleaning memory

        //Cria o shingles para cada documento
        System.out.println("Shingling each doc");
        for(int i = 0; i < users.length; i++){
            String docToShingle = listaDeUsers.get(users[i]).getFilmes().toString();
            ShingledDocs.put(users[i], MinHash.shingleTextPairs(docToShingle));
        }

        // Calcula as assinaturas para cada documento
        System.out.println("Getting Signatures");

        MinHash nMH = new MinHash(100);
        HashMap<String, int[]> signatures = new HashMap<String,int[]>();
        for(int i = 0; i < users.length; i++){
            LinkedHashSet ShingleToSign = ShingledDocs.get(users[i]);
            signatures.put(users[i], nMH.getSignature(ShingleToSign));
        }

        //A calcular similiaridades
        System.out.println("Calculating similarities");
        double similarities[][] = new double[users.length][users.length];

        for(int row = 0; row < users.length; row++){
            for(int column = 0; column < users.length; column++){
                similarities[row][column] = row > column ? 0 : nMH.calculateSimilarity(signatures.get(users[row]), signatures.get(users[column]));
            }
        }

        double threshold = 0.5;

        for(int row = 0; row < users.length; row++){
            for(int column = 0; column < users.length; column++){
                if (row < column){
                    if((similarities[row][column] > threshold)) {

                        System.out.println("O user " + users[row] + " é semelhante ao " + users[column]);
                        System.out.println("Valor MinHash: " + Math.round(similarities[row][column]*10)/10.0);
                        System.out.println("Valor Teórico: " + Math.round(MinHash.indiceJaccard(ShingledDocs.get(users[row]), ShingledDocs.get(users[column]))*10)/10.0);
                    }
                }
            }
        }

        System.out.println("Erro: +-" + nMH.erro());
    }

    public static String[][] readData(String path,int size){
        BufferedReader uReader;
        String line;
        String[][] ret = new String[size][2];
        int linecount = 0;
        try {
            uReader = new BufferedReader(new FileReader(path));
            while((line = uReader.readLine()) != null && linecount < size){
                String[] split = line.split("\t");
                for (int i = 0; i < 2; i++){
                    ret[linecount][i] = split[i];
                }
                linecount++;
            }
            uReader.close();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return ret;
    }

    public static String[] getColumn(String[][] array, int index){
        String[] column = new String[array.length];
        for(int i = 0; i < column.length; i++){
            column[i] = array[i][index];
        }
        return column;
    }

    public static String[] uniqueElems(String[] array){
        return Arrays.stream(array).distinct().toArray(String[]::new);
    }

    public static void printArray(Object[] array){
        for(int i = 0; i < array.length; i++){
            System.out.println(array[i]);
        }
    }
}

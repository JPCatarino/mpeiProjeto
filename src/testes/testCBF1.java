package testes;

import componentes.CountingBloomFilter;

public class testCBF1 {
    public static void main(String[] args){
        int numPositions = 10000;
        int numHashes = 3;
        int counterWidth = 8;
        CountingBloomFilter<String> cbfTesting = new CountingBloomFilter<String>(numPositions,counterWidth,numHashes);

        String[] toInsert = {"ola", "mundo", "dos", "bloom", "filter", "bloom", "bloom", "bloom"};

        for(int i = 0; i < toInsert.length; i++){
            cbfTesting.insertElem(toInsert[i]);
        }

        String[] toCheck = {"mundo", "russia", "bloom"};

        for(int i = 0; i < toCheck.length; i++){
            if(cbfTesting.isMember(toCheck[i])){
                System.out.println("Provavelmente faz parte do conjunto");
                System.out.println("A palavra " + toCheck[i] + " acontece, no minimo " + cbfTesting.count(toCheck[i]) + " vezes\n");
            }
            else{
                System.out.println("Não faz parte do conjunto\n");
            }
        }
        System.out.println("");
        System.out.println(">Deleting instance of word bloom");
        cbfTesting.deleteElem("bloom");
        System.out.println("A palavra bloom acontece, no minimo " + cbfTesting.count("bloom") + " vezes");

    }
}

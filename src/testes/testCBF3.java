package testes;


import casasDeApostas.componentes.Match;
import componentes.CountingBloomFilter;
import componentes.DatasetReader;

public class testCBF3 {

    public static void main(String[] args) {
        Match[] matches = DatasetReader.readMatches();
        CountingBloomFilter<Match> cbf = new CountingBloomFilter<Match>(500000 * 15, 32, 500000 * 15 / 500000);
        System.out.println("Inserting Elems");
        for(int i = 0; i < matches.length; i++){
            cbf.insertElem(matches[i]);
        }
        System.out.println("done");

        if(cbf.isMember(matches[1]))
            System.out.println(cbf.count(matches[1]));
            System.out.println("cool");
    }


}

package testes;


import casasDeApostas.Match;
import componentes.CountingBloomFilter;
import componentes.DatasetReader;
import componentes.MinHash;

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

        MinHash nmn = new MinHash(32);
        long[][] matrix = nmn.getCoeficientes();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }


}

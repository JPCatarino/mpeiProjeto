package componentes;

import java.util.HashSet;
import java.util.Set;

/**
 * MinHash é um esquema de hashing que produz assinaturas semelhastes para
 * sets semelhantes.
 * @author Jorge Catarino
 * @author Oscar Pimentel
 */

public class MinHash {

    private long SxDMatrix[][];             // matriz shingles por documentos


    public static double indiceJaccard(Set A, Set B){
        Set intersecao = new HashSet(A);
        intersecao.retainAll(B);

        Set uniao = new HashSet(A);

        if(!uniao.isEmpty())
            uniao.addAll(B);
        else
            return 0;

        return (double) intersecao.size()/uniao.size();
    }

    public static double distanciaJaccard(Set A, Set B){
        return 1 - indiceJaccard(A,B);
    }
}

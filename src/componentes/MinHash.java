package componentes;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * MinHash é um esquema de hashing que produz assinaturas semelhastes para
 * sets semelhantes.
 * @author Jorge Catarino
 * @author Oscar Pimentel
 */

public class MinHash {

    private static final int maxShingleID = 2147483647;
    private long coeficientes[][];             // Random perms
    private int signatureSize;

    public MinHash(int signatureSize){
        this.signatureSize = signatureSize;
        pickCoeficientes(new Random());
    }

    public long[][] getCoeficientes() {
        return coeficientes;
    }

    private void pickCoeficientes(Random ran){
        coeficientes = new long[this.signatureSize][2];
        for (int i = 0; i < this.signatureSize; i++) {
            coeficientes[i][0] = ran.nextInt(maxShingleID - 1) + 1; // a
            coeficientes[i][1] = ran.nextInt(maxShingleID - 1) + 1; // b
        }
    }


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

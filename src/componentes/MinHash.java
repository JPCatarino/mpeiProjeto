package componentes;

import java.util.*;

/**
 * MinHash é um esquema de hashing que produz assinaturas semelhastes para
 * sets semelhantes.
 * @author Jorge Catarino
 * @author Oscar Pimentel
 */

public class MinHash {

    private static final int maxShingleID = 2147483647;
    private static final long nextPrime = 4294967311L;
    private long coeficientes[][];             // Random perms
    private int signatureSize;

    public MinHash(int signatureSize){
        this.signatureSize = signatureSize;
        pickCoeficientes(new Random());
    }

    public double calculateSimilarity(int[] signature1, int[] signature2){
        if(signature1.length != signature2.length){
            throw new IllegalArgumentException("Signatures should have same length");
        }

        double count = 0;
        for (int i = 0; i < signature1.length; i++) {
            if (signature1[i] == signature2[i]) {
                count += 1;
            }
        }

        return count / signature1.length;

    }

    public long[][] getCoeficientes() {
        return coeficientes;
    }

    public int[] getSignature(Set<Integer> toSign){
        int[] sig = new int[signatureSize];

        for (int i = 0; i < signatureSize; i++)
            sig[i] = Integer.MAX_VALUE;

        List<Integer> list = new ArrayList<Integer>(toSign);
        Collections.sort(list);

        for (final int r : list) {

            for (int i = 0; i < signatureSize; i++) {
                sig[i] = Math.min(
                        sig[i],
                        hash(i, r));
            }
        }

        return sig;
    }

    private void pickCoeficientes(Random ran){
        coeficientes = new long[this.signatureSize][2];
        for (int i = 0; i < this.signatureSize; i++) {
            coeficientes[i][0] = ran.nextInt(maxShingleID - 1) + 1; // a
            coeficientes[i][1] = ran.nextInt(maxShingleID - 1) + 1; // b
        }
    }

    public static LinkedHashSet<Integer> shingleTextPairs(String toShingle){
        Integer result[] = new Integer [toShingle.length() - 1];
        LinkedHashSet<Integer> ret = new LinkedHashSet<Integer>();
        for(int i = 0; i < toShingle.length() - 2; i++){
            result[i] = toShingle.substring(i, i+2).hashCode();
        }
        result = Arrays.copyOfRange(result,0,result.length-1);
        for(int i = 0; i < result.length; i++){
            ret.add(result[i]);
        }
        return ret;
    }

    // h = (a * x + b) % LARGE_PRIME
    private int hash(final int i, final int x) {
        return (int) ((coeficientes[i][0] * (long) x + coeficientes[i][1]) % nextPrime);
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

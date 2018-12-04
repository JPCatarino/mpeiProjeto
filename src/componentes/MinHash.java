package componentes;

import java.util.*;

/**
 * MinHash é um esquema de hashing que produz assinaturas semelhantes para
 * sets semelhantes.
 * Implementação baseada no artigo MinHash Tutorial with Python Code de Chris McCormick
 * Encontrado em http://mccormickml.com/2015/06/12/minhash-tutorial-with-python-code/
 * @author Jorge Catarino
 */

public class MinHash {

    private static final int maxShingleID = 2147483647;
    private static final long nextPrime = 4294967311L;
    private long coeficientes[][];             // Random perms
    private int signatureSize;

    /**
     * Class Constructor for Counting Bloom Filter.
     * @param signatureSize Size of signature/ number of hashes
     */

    public MinHash(int signatureSize){
        this.signatureSize = signatureSize;
        pickCoeficientes(new Random());
    }

    /**
     * Method to calculate similarity between 2 Minhash signatures
     * @param signature1 Signature of first set.
     * @param signature2 Signature of second set.
     * @return Similarity between the 2 signatures.
     */

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

    /**
     * Getter method for hash coefs
     * @return coeficientes
     */

    public long[][] getCoeficientes() {
        return coeficientes;
    }

    /**
     * Method to calculate MinHash Signature of a given set.
     * Converts set to ArrayList and hashes each int, getting the minimum value
     * @param toSign Set to sign
     * @return Hash Signature of given set
     */

    public int[] getSignature(Set<Integer> toSign){
        int[] sig = new int[signatureSize];

        for (int i = 0; i < signatureSize; i++)
            sig[i] = Integer.MAX_VALUE;

        List<Integer> list = new ArrayList<Integer>(toSign);
        Collections.sort(list);

        for (int hs : list) {
            for (int i = 0; i < signatureSize; i++) {
                sig[i] = Math.min(sig[i], hash(i, hs));
            }
        }
        return sig;
    }

    /**
     * Generates random hash coeffs with a given random
     * @param ran Random object
     */

    private void pickCoeficientes(Random ran){
        coeficientes = new long[this.signatureSize][2];
        for (int i = 0; i < this.signatureSize; i++) {
            coeficientes[i][0] = ran.nextInt(maxShingleID - 1) + 1; // a
            coeficientes[i][1] = ran.nextInt(maxShingleID - 1) + 1; // b
        }
    }

    /**
     * Method to shingle a String in pairs of 2 characters.
     * @param toShingle String to shingle.
     * @return Set with shingles
     */

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

    /**
     * Hash function bases on  h = (a * x + b) % c
     * @param i Index
     * @param x x
     * @return hashed value.
     */
    // h = (a * x + b) % LARGE_PRIME
    private int hash(int i, int x) {
        return (int) ((coeficientes[i][0] * (long) x + coeficientes[i][1]) % nextPrime);
    }

    /**
     * Method to calculate MinHash error, with the formula 1/sqrt(signatureSize)
     * For a smaller error, the Signature Size/Number of Hashes has to be bigger
     * For 10% it's 100 hashes, for 1% is no more than 10000 hash functions!
     * @return Error value.
     */

    public double erro(){
        return 1.0 / Math.sqrt(signatureSize);
    }


    /**
     * Auxiliary method to calculate Jaccard Index, can be used to check MinHash results
     * @param A Set A
     * @param B Set B
     * @return Jaccard's index
     */

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

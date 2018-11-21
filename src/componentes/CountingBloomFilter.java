package componentes;

import java.util.BitSet;

/**
 * <h1>Counting Bloom Filter</h1>
 * An implementation of a data structure that allow us to check
 * if a elements belongs to a set of E. Also makes it possible to count the instances of a certain element.
 * @author Jorge Catarino
 */

public class CountingBloomFilter<E> {
    private BitSet[] cbf;
    private int numHashes;
    private int numPositions;
    private int counterWidth;

    /**
     * Class Constructor for Counting Bloom Filter.
     * @param numPositions Number of positions the bit counter array shall have.
     * @param counterWidth Width of each bit counter.
     * @param numHashes Number of hash transforms to be used.
     */
    public CountingBloomFilter(int numPositions, int counterWidth, int numHashes){
        this.cbf = new BitSet[numPositions];
        this.numPositions = numPositions;
        this.counterWidth = counterWidth;
        this.numHashes = numHashes;
        fillBitSetArray(counterWidth);
    }

    /**
     * Method to insert element on the CBF.
     * @param elem Element to be inserted.
     */
    public void insertElem(E elem){
        for(int i = 0; i != numHashes; i++){
            String key = elem + String.valueOf(i);
            int position = Math.abs(key.hashCode()%numPositions);
            incrementBitSet(position);
        }
    }

    /**
     * Method to delete element of the CBF.
     * @param elem Element to be deleted.
     */
    public void deleteElem(E elem) {
        assert isMember(elem);
        for (int i = 0; i != numHashes; i++) {
            String key = elem + String.valueOf(i);
            int position = Math.abs(key.hashCode() % numPositions);
            if(bitSet2Long(cbf[position]) > 0) {
                decrementingBitSet(position);
            }
        }
    }

    /**
     * Method to check if a certain element is part of the CBF
     * @param elem Element to check
     * @return True if the element is member of CBF, false otherwise.
     */
    public boolean isMember(E elem){
        BitSet toCompare = new BitSet(counterWidth);

        for(int i = 0; i != numHashes; i++){
            String key = elem + String.valueOf(i);
            int position = Math.abs(key.hashCode()%numPositions);
            if(cbf[position].equals(toCompare)){
                return false;
            }
        }
        return true;
    }

    /**
     * Method to count instances of a certain element in the cbf
     * @param elem Elem to be counted.
     * @return Number of instances of the element on the CBF.
     */
    public long count(E elem){
        assert isMember(elem);
        long[] counterArray = new long[numHashes];
        for(int i = 0; i != numHashes; i++){
            String key = elem + String.valueOf(i);
            int position = Math.abs(key.hashCode()%numPositions);
            counterArray[i] = bitSet2Long(cbf[position]);
        }
        return minValue(counterArray);
    }

    /**
     * Static method to calculate the optimal k number of hashes transformations for a certain set.
     * @param n Number of CBF positions.
     * @param m Number of set members.
     * @return Optimal value of k hash transformations to use for a certain set.
     */
    public static int calculateOptimalK(int n,int m){
        return (int) Math.floor(((double)(n/m))*Math.log(2));     // k = (n/m)ln(2)
    }


    /**
     * Private method to increment a counter stored in a position of the CBF.
     * @param position Index of the counter we want to increment.
     */
    private void incrementBitSet(int position){
        boolean carry = true;
        BitSet toIncrement = cbf[position];

        for(int i = 0; i < toIncrement.size(); i++){
            if(carry){
                if(!toIncrement.get(i)){
                    toIncrement.set(i,true);
                    carry = false;
                }
                else{
                    toIncrement.set(i,false);
                    carry = true;
                }
            }
        }
        cbf[position] = toIncrement;
    }

    /**
     * Private method to decrement a counter, stored in a position of the CBF.
     * @param position Index of the counter we want to decrement.
     */
    private void decrementingBitSet(int position) {
        boolean borrow = false;
        BitSet toDecrement = cbf[position];
        if (!toDecrement.get(0)) {
            toDecrement.set(0, true);
            borrow = true;
        }
        else{
            toDecrement.set(0,false);
        }

        for (int i = 1; i < toDecrement.size(); i++) {
            if(borrow){
                if(toDecrement.get(i)){
                    toDecrement.set(i,false);
                    borrow = false;
                }
                else{
                    toDecrement.set(i,true);
                    borrow = true;
                }
            }
        }
    }

    /**
     * Private method to fill the CBF with bit counters with n-width
     * @param counterWidth Width of the bit counters
     */
    private void fillBitSetArray(int counterWidth){
        for(int i = 0; i < cbf.length; i++){
            cbf[i] = new BitSet(counterWidth);
        }
    }

    /**
     * Private method to convert the values stored in BitSet to long.
     * @param toConvert BitSet that needs to be converted.
     * @return Value of the BitSet converted to long.
     */
    private long bitSet2Long(BitSet toConvert){
        long result = 0L;
        for(int i = 0; i < toConvert.length(); ++i){
            result += toConvert.get(i) ? (1L << i) : 0L;
        }
        return result;
    }

    /**
     * Private method to get minimum value of a long array.
     * @return Minimum value of given array
     */
    private long minValue(long[] counterArray){
        long min = Long.MAX_VALUE;
        for (long aCounterArray : counterArray) {
            if (aCounterArray < min)
                min = aCounterArray;
        }
        return min;
    }
}

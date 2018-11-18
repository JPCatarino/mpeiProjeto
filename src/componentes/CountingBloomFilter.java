package componentes;

import java.util.BitSet;

public class CountingBloomFilter<E> {
    private BitSet[] cbf;
    private int numHashes;
    private int numPositions;
    private int counterWidth;

    public CountingBloomFilter(int numPositions, int counterWidth, int numHashes){
        this.cbf = new BitSet[numPositions];
        this.numPositions = numPositions;
        this.counterWidth = counterWidth;
        this.numHashes = numHashes;
        fillBitSetArray(counterWidth);
    }

    public void insertElem(E elem){
        for(int i = 0; i != numHashes; i++){
            String key = elem + String.valueOf(i);
            int position = Math.abs(key.hashCode()%numPositions);
            incrementBitSet(position);
        }
    }

    public void deleteElem(E elem) {
        assert isMember(elem);
        for (int i = 0; i != numHashes; i++) {
            String key = elem + String.valueOf(i);
            int position = Math.abs(key.hashCode() % numPositions);
            decrementingBitSet(position);
        }
    }

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

    private void fillBitSetArray(int counterWidth){
        for(int i = 0; i < cbf.length; i++){
            cbf[i] = new BitSet(counterWidth);
        }
    }

    private long bitSet2Long(BitSet toConvert){
        long result = 0L;
        for(int i = 0; i < toConvert.length(); ++i){
            result += toConvert.get(i) ? (1L << i) : 0L;
        }
        return result;
    }

    private long minValue(long[] counterArray){
        long min = Long.MAX_VALUE;
        for(int i = 0; i < counterArray.length; i++){
            if(counterArray[i] < min)
                min = counterArray[i];
        }
        return min;
    }
}

package com.joework.datastructures.hashtable;



import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.LinkedList;

/***
 * Implemented Methods:
 * [x] three constructors
 * [x] containsKey(K key)
 * [x] isEmpty(), size()
 * [x] bucketInsertEntry(bucketIdx, entry)
 *
 * UnKnown/not understood methods:
 * normalized Index
 * @param <K>
 * @param <V>
 */

class Entry <K,V>{
    // this is used to store the hash instead of computing it each time
    int hash;
    K key;
    V val;

    public Entry(K key, V val){
        this.key = key;
        this.val = val;
        this.hash = key.hashCode(); // compute and store the hash code to avoid re-computations in the future
    }

    // here we are not overriding the Object's class equals method
    public boolean equal(Entry<K,V> other){
        if(this.hash != other.hash) return false;
        return key.equals(other.key);
    }

    @Override
    public String toString() {
        return key + " : " + val;
    }
}

public class SeparateChainingHashTable <K,V> implements Iterable<K> {

    private static final int DEFAULT_CAPACITY = 3;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;
    private double maxLoadFactor;
    private int capacity, size = 0, threshold;

    private LinkedList<Entry<K,V>>[] table ;

    //Default constructor
    public SeparateChainingHashTable(){
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public SeparateChainingHashTable(int capacity){
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    // constructor with capacity

    // [main] constructor with capacity and load factor
    public SeparateChainingHashTable(int capacity, double loadFactor){
        if(capacity < 0) throw new IllegalArgumentException("capacity should be greater than 0");

        if(loadFactor < 0  || Double.isNaN(loadFactor) || Double.isInfinite(loadFactor))
            throw new IllegalArgumentException("Illegal load factor");

        this.maxLoadFactor = loadFactor;
        this.capacity = Math.max(DEFAULT_CAPACITY, capacity);
        this.threshold = (int)(this.capacity * maxLoadFactor);
        table = new LinkedList[capacity];

    }

    // isEmpty()
    public boolean isEmpty(){
        return size() == 0;
    }

    // return the size of the table
    public int size() {
        return size;
    }

    // clear()
    public void clear(){
        Arrays.fill(table, null);
        size = 0;
    }
    //containsKey(K key)
    public boolean containsKey(K key){
        int bucketIndex = normalizeIndex(key.hashCode());
        return bucketSeekEntry(bucketIndex, key) != null;
    }

    /**
     the value of the hex decimal 0x7FFFFFFF is 2147483647 in decimal (1111111111111111111111111111111 in bin)
     and this value is the MAX value in the integer 32 bit, so we AND this value with the hash value to flip
     the first bit (the leftmost bit) to make sure that if the value is negative we will change it
    */
    private int normalizeIndex(int keyHash) {
        return (keyHash & 0x7FFFFFFF) % capacity;
    }


    // insert(K key, V value)
    public V insert(K key, V value){
        if(Objects.isNull(key)) throw new IllegalArgumentException("Key can't be null");
        Entry<K,V> newEntry = new Entry<>(key,value);

        int index = normalizeIndex(newEntry.hash);

        if(table[index] == null) {
            table[index] =  new LinkedList<>();
            table[index].add(newEntry);
            return newEntry.val;
        }

        return bucketInsertEntry(index, newEntry);
    }

    // put(K key, V value)

    // add(K key, V value)

    // get(K key)

    public V remove(K key){
        int index = normalizeIndex(key.hashCode());
        return bucketRemoveEntry(index, key);
    }

    // Returns the list of keys found within the hash table
    //    public List<K> keys() {

    // Returns the list of values found within the hash table
//    public List<V> values()


    // Return an iterator to iterate over all the keys in this map



    // public String toString()
    /*

    private/helper methods

    */


    // Removes an entry from a given bucket if it exists 
    private V bucketRemoveEntry(int bucketIndex, K key){
        Entry<K, V> entry = bucketSeekEntry(bucketIndex, key);

        if(entry == null){
            return null;
        }


        var list = table[bucketIndex];
        list.remove(entry);    

        size--;
        return entry.val;
    }

      
    
    /**
      *  Inserts an entry in a given bucket only if the entry does not already
      *     exist in the given bucket, but if it does then update the entry value
      * 
      * @param bucketIndex
      * @param entry
      * @return the new value if the entry not exist in the bucket or the old value if it exist
      */
     private V bucketInsertEntry(int bucketIndex, Entry<K, V> entry){
         LinkedList<Entry<K,V>> bucket = table[bucketIndex];
         if(bucket == null) {
             bucket = new LinkedList<>();
             
             bucket.add(entry);
             size++;
             return entry.val;
         }

         Entry<K,V> existEntry = bucketSeekEntry( bucketIndex, entry.key);
         
         if (existEntry == null){
             table[bucketIndex].add(entry);
             size++;
             return entry.val;
         }else {
             V oldValue = existEntry.val;
             existEntry.val = entry.val;
             
             return oldValue;
         }

     }

    // Finds and returns a particular entry in a given bucket if it exists, returns null otherwise
    //    Entry<K, V> bucketSeekEntry(int bucketIndex, K key)
        private Entry<K, V> bucketSeekEntry(int bucketIndex, K key){
            LinkedList<Entry<K,V>> bucket = table[bucketIndex];

            for (var entry : bucket) {
                if(entry.key.equals(key)) return entry;
            }

            return null;
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeparateChainingHashTable<?, ?> that = (SeparateChainingHashTable<?, ?>) o;
        return Double.compare(that.maxLoadFactor, maxLoadFactor) == 0 && capacity == that.capacity && size == that.size && threshold == that.threshold;
    }

    @Override
    public int hashCode() {
        int hash = Objects.hash(maxLoadFactor, capacity, size, threshold);
        return hash;
    }


    // Resizes the internal table holding buckets of entries
    //    private void resizeTable()

    public static void main(String[] args) {
        	 String s = "sResponseBody1: <?xml version=\"1.0\" encoding=\"utf-8\"?><error xmlns=\"http://schemas.microsoft.com/ado/2007/08/dataservices/metadata\"><code>ERROR</code><message xml:lang=\"en\">(S4DCLNT120) Please enter sold-to party or ship-to party::(S4DCLNT120) Sales document  was not changed::(S4DCLNT120) Pricing data could not be read::Price procedure code is missing::Please enter a party with role Account.::Please use not more than 1 party(ies) of role Account.::Please use not more than 1 party(ies) of role Account.::Please use not more than 1 party(ies) of role Account.::Please use not more than 1 party(ies) of role Account.::Please use not more than 1 party(ies) of role Account.::Please use not more than 1 party(ies) of role Account.::Please use not more than 1 party(ies) of role Account.::Please use not more than 1 party(ies) of role Bill-To.::Please use not more than 1 party(ies) of role Account.::Please use not more than 1 party(ies) of role Account.::Please use not more than 1 party(ies) of role Bill-To.::Please use not more than 1 party(ies) of role Account.::Please use not more than 1 party(ies) of role Ship-To.::Please use not more than 1 party(ies) of role Account.::Please use not more than 1 party(ies) of role Bill-To.::Please use not more than 1 party(ies) of role Account.::Please use not more than 1 party(ies) of role Ship-To.::Please use not more than 1 party(ies) of role Payer.::Please use not more than 1 party(ies) of role Account.::Please use not more than 1 party(ies) of role Account.::Please use not more than 1 party(ies) of role Bill-To.::Please use not more than 1 party(ies) of role Ship-To.::Please use not more than 1 party(ies) of role Payer.::Please use not more than 1 party(ies) of role Bill-To.::Please use not more than 1 party(ies) of role Ship-To.::Please use not more than 1 party(ies) of role Payer.::Please use not more than 1 party(ies) of role Bill-To.::Please use not more than 1 party(ies) of role Ship-To.::Please use not more than 1 party(ies) of role Bill-To.::Please use not more than 1 party(ies) of role Ship-To.::Please use not more than 1 party(ies) of role Bill-To.::Please use not more than 1 party(ies) of role Bill-To.::{{15170,ZI}}::Gas Refilling Ticket Created within 2 or 7 day contact service agent : This customer has recent tank refill ticket 15170</message></error>";

 String[] split = s.split("\\{\\{");
 String[] split2 = split[1].split("\\}\\}");
//  System.out.println(split[1]);
 System.out.println(split2[0]);

 String[] split3 = split2[0].split(",");
 System.out.println("ticket id "+ split3[0]);
 System.out.println("ticket status "+ split3[1]);

}

    @Override
    public Iterator<K> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

}

class Solution {
    private int[] nums;
    private Random rand = new Random();
    public Solution(int[] nums) {
        this.nums = nums;
    }
    
    public int[] reset() {
        return nums;
    }
    
    public int[] shuffle() {
        int len = nums.length;
        int[] arr = new int[len];
        Set<Integer> set = new HashSet<>();
        
        for(int i = 0; i < len; i++){
                int nextIdx = rand.nextInt(len);;
                while(set.contains(nums[nextIdx])) {
                    nextIdx = rand.nextInt(len);
                }
                arr[i] = nums[nextIdx];
                set.add(arr[i]);
        }
        
       return arr; 
    }
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7,8};
        Solution solution = new Solution(arr);
        Arrays.stream(solution.shuffle()).forEach(System.out::print);
    }
}

/******************************************************************************

                            Online Java Compiler.
                Code, Compile, Run and Debug java program online.
Write your code in this editor and press "Run" button to execute it.

*******************************************************************************/


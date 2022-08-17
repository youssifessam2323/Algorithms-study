package com.joework.datastructures.tree;

import java.util.Optional;

public interface BinaryTreeOperations<K extends Comparable<K>, V extends Comparable<V>> {
    void insert(Node<K,V> node);

    boolean delete(K k);

    boolean delete(Node<K,V> k);

    Optional<Node<K,V>> search(K k);

    Optional<Node<K,V>> search(Node<K,V> k);

    Node<K,V> max();

    Node<K,V> min();

    Node<K,V> successor(K k);
    
    Node<K,V> predecessor(K k);

}

package com.joework.datastructures.tree;

public class Node<K extends Comparable<K>, V extends Comparable<V>> {
    protected K key;
    protected V value;
    protected Node<K, V> parent;
    protected Node<K, V> left, right;
    protected NodePosition position;
    protected int hashCode;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
        this.position = NodePosition.ROOT;
    }

    public Node(K key, V value, boolean isLeft) {
        this.key = key;
        this.value = value;
        this.position = isLeft == true ? NodePosition.LEFT : NodePosition.RIGHT;
    }

    public Node<K, V> setKey(K key) {
        this.key = key;
        return this;
    }

    public K getKey() {
        return key;
    }

    public Node<K, V> setLeft(Node<K, V> left) {
        if (left.position == NodePosition.ROOT)
            throw new IllegalArgumentException("node don't need to be root");
        this.left = left;
        return this;
    }

    public Node<K, V> getLeft() {
        return left;
    }

    public Node<K, V> setParent(Node<K, V> parent) {
        this.parent = parent;
        return this;
    }

    public Node<K, V> getParent() {
        return parent;
    }

    public Node<K, V> setPosition(NodePosition position) {
        this.position = position;
        return this;
    }

    public NodePosition getPosition() {
        return position;
    }

    public Node<K, V> setRight(Node<K, V> right) {
        if (right.position == NodePosition.ROOT)
            throw new IllegalArgumentException("node don't need to be root");
        this.right = right;
        return this;
    }

    public Node<K, V> getRight() {
        return right;
    }

    public Node<K, V> setValue(V value) {
        this.value = value;
        return this;
    }

    public V getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "{\nkey: " + key + ",\n value: " + value + ",\n position: " + this.position.name() + ", \nleft: " + left
                + ", \n right: " + right + " }";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof Node<?, ?>))
            return false;

        if(obj == this) return true;

        var other = (Node<K, V>) obj;
        System.out.println("Node's equal is invoked");
        return this.key.compareTo(other.key) == 0 && this.value.compareTo(other.value) == 0;
    }

    @Override
    public int hashCode() {
        int result = hashCode;

        if (result == 0) {
            result = 31 * result + key.hashCode();
            result = 31 * result + value.hashCode();
            hashCode = result;
        }

        return result;
    }

}

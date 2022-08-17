package com.joework.datastructures.tree;

import static com.joework.datastructures.tree.NodePosition.LEFT;
import static com.joework.datastructures.tree.NodePosition.RIGHT;
import static com.joework.datastructures.tree.NodePosition.ROOT;
import static com.joework.datastructures.tree.TreePrinter.printBinaryTree;

import java.util.Optional;

public class BST<K extends Comparable<K>, V extends Comparable<V>> implements BinaryTreeOperations<K, V> {
    private Node<K, V> root;
    private int size;

    public BST(Node<K, V> root) {
        this.root = root;
    }

    public Node<K, V> getRoot() {
        return root;
    }

    @Override
    public void insert(Node<K, V> node) {
        var curr = root;
        if (root == null)
            this.root = node;
        root.position = ROOT;
        insert(curr, node);
    }

    private void insert(Node<K, V> curr, Node<K, V> node) {

        if (curr.key.compareTo(node.key) > 0) {
            if (curr.left != null)
                insert(curr.left, node);
            else {
                curr.left = node;
                node.parent = curr;
                node.position = NodePosition.LEFT;
            }
        } else {
            if (curr.right != null)
                insert(curr.right, node);
            else {
                curr.right = node;
                node.parent = curr;
                node.position = NodePosition.RIGHT;
            }
        }
        size++;
    }

    @Override
    public boolean delete(K k) {
        return this.root == null ? false : delete(root, k);
    }

    private boolean fixChange(Node<K, V> curr) {
        if (hasRightOnly(curr)) {
            if (curr.position.equals(NodePosition.RIGHT)) {
                curr.parent.right = curr.right;
                size--;
            }

            if (curr.position.equals(NodePosition.LEFT)) {
                curr.parent.left = curr.right;
                curr.right.position = LEFT;
                curr.right.parent = curr.parent;
                size--;
            }

            if (curr.position.equals(NodePosition.ROOT)) {
                this.root = curr.right;
                curr.right.position = ROOT;
                curr.right.parent = null;
                size--;
            }
        } else if (hasLeftOnly(curr)) {
            if (curr.position.equals(NodePosition.RIGHT)) {
                curr.parent.right = curr.left;
                curr.left.position = RIGHT;
                curr.left.parent = curr.parent;
                size--;
            }

            if (curr.position.equals(NodePosition.LEFT)) {
                curr.parent.left = curr.left;
                curr.left.position = LEFT;
                curr.left.parent = curr.parent;
                size--;
            }

        } else if (hasNoChild(curr)) {
            switch (curr.position) {
                case ROOT: {
                    this.root = null;
                    curr.parent = null;
                }
                    break;
                case LEFT: {
                    curr.parent.left = null;
                    curr.parent = null;
                }
                    break;
                case RIGHT: {
                    curr.parent.right = curr.right;
                    curr.parent = null;
                }
                    break;
            }
        } else {

            // the node has left and right child
            var p = curr.parent;
            var r = curr.right;
            var rightLeft = curr.right == null ? null : curr.right.left;
            if (p != null && curr.position == NodePosition.LEFT) {
                p.left = curr.right;
            } else if (p != null && curr.position == NodePosition.RIGHT) {
                p.right = curr.right;
            } else {
                this.root = curr.right;
                this.root.position = NodePosition.ROOT;
                curr.right.parent = null;
            }
            r.left = curr.left;
            if (rightLeft != null)
                insert(curr, rightLeft);
            size--;
        }

        curr.left = null;
        curr.right = null;
        curr = null;
        return true;
    }

    private boolean hasRightOnly(Node<K, V> curr) {
        return curr.right != null && curr.left == null;
    }

    private boolean delete(Node<K, V> curr, K k) {
        if (curr == null)
            return false;

        if (curr.key.compareTo(k) == 0) {
            System.out.println(curr);
            return fixChange(curr);
        } else {
            return delete(curr.key.compareTo(k) > 0 ? curr.left : curr.right, k);
        }

    }

    @Override
    public boolean delete(Node<K, V> k) {

        var curr = this.root;
        return delete(curr, k);
    }

    private boolean delete(Node<K, V> curr, Node<K, V> target) {
        if (curr == null)
            return false;

        if (curr == target) {
            return fixChange(curr);
        }

        return delete(curr.key.compareTo(target.key) > 0 ? curr.left : curr.right, target);
    }

    private boolean hasNoChild(Node<K, V> curr) {
        return curr.right == null && curr.left == null;
    }

    private boolean hasLeftOnly(Node<K, V> curr) {
        return curr.right == null && curr.left != null;
    }

    /***
     * if the item we trying to search for is exist we will return an optional with
     * the node
     * otherwise an empty optional
     */
    @Override
    public Optional<Node<K, V>> search(K k) {
        var curr = root;

        while (curr != null) {
            if (curr.key.compareTo(k) == 0)
                return Optional.of(curr);

            if (curr.key.compareTo(k) > 0)
                curr = curr.left;
            else {
                curr = curr.right;
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Node<K, V>> search(Node<K, V> node) {
        return search(node.getKey());
    }

    @Override
    public Node<K, V> max() {
        var curr = this.root;

        while (curr.right != null)
            curr = curr.right;
        return curr;
    }

    @Override
    public Node<K, V> min() {
        var curr = this.root;

        while (curr.left != null)
            curr = curr.left;
        return curr;
    }

    private Node<K, V> min(Node<K, V> node) {
        var curr = node;

        while (curr.left != null)
            curr = curr.left;
        return curr;
    }

    private Optional<Node<K, V>> isElementExists(K k) {
        return search(k);
    }

    /**
     * there are two cases:
     * case 1: if we have a right child so we will go right and after that if the
     * right child has a left sub tree then we will go
     * to the leftmost node and return it
     * 
     * case 2: if we have not a right child so we will go up and as we are not the
     * left child of the parent or the parent are equal null
     * we will go up again and again until we hit one of the previous conditions
     */
    @Override
    public Node<K, V> successor(K k) {
        var element = isElementExists(k);
        if (!element.isPresent())
            throw new IllegalArgumentException("the key must be in the tree");

        var node = element.get();

        if (node.right != null) {
            return min(node.right);
        }

        var parent = node.parent;

        while (parent.right == node) {
            node = parent;
            parent = parent.parent;
        }

        return parent;
    }

    /**
     * there are two cases:
     * case 1: if we have a left child so we will go left and after that if the
     * left child has a right sub tree then we will go
     * to the rightmost node and return it
     * 
     * case 2: if we have not a left child so we will go up and as we are not the
     * right child of the parent or the parent are equal null
     * we will go up again and again until we hit one of the previous conditions
     */
    @Override
    public Node<K, V> predecessor(K k) {
        var element = isElementExists(k);
        if (!element.isPresent())
            throw new IllegalArgumentException("the key must be in the tree");

        var node = element.get();

        if (node.left != null)
            return max(node.left);

        var parent = node.parent;

        if (parent.left == node)
            return null;

        else
            return parent;

    }

    private Node<K, V> max(Node<K, V> node) {
        var curr = node;

        while (curr.right != null)
            curr = curr.right;
        return curr;
    }

    @Override
    public String toString() {
        if (root == null)
            return "{}";

        return root.toString();
    }
}

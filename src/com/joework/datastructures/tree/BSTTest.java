package com.joework.datastructures.tree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BSTTest {

    private BST<Integer, String> bst;

    @BeforeEach
    public void BeforeEach(){
        System.out.println("I will run before each testcase");
        var root = new Node<Integer, String>(10, "Developer");
        bst = new BST<>(root);
        bst.insert(new Node<Integer, String>(5, "IT"));
        bst.insert(new Node<Integer, String>(13, "IT"));
        bst.insert(new Node<Integer, String>(2, "IT"));
        bst.insert(new Node<Integer, String>(7, "IT"));
        bst.insert(new Node<Integer, String>(12, "IT"));
        bst.insert(new Node<Integer, String>(14, "IT"));
        bst.insert(new Node<Integer, String>(1, "IT"));
        bst.insert(new Node<Integer, String>(3, "IT"));
        bst.insert(new Node<Integer, String>(6, "IT"));
        bst.insert(new Node<Integer, String>(8, "IT"));
        bst.insert(new Node<Integer, String>(11, "IT"));
        TreePrinter.printBinaryTree(bst.getRoot(), 0);
    }
    @Test
    void test_deletion_of_all_nodes() {
        IntStream.range(1, 15).forEach(v -> {
            System.out.println("after deleting " + v);
            bst.delete(v);
            TreePrinter.printBinaryTree(bst.getRoot(), 0);
            System.out.println("===================================");
            });
        
        assertNull(bst.getRoot());
    }

    @Test
    void test_deletion_of_root_node() {
        var rootRightChild = bst.getRoot().getRight();
        var rootLeftChild = bst.getRoot().getLeft();
        bst.delete(bst.getRoot());
        assertEquals(bst.getRoot(), rootRightChild);
        assertEquals(bst.getRoot().getLeft(), rootLeftChild);
        assertEquals(null, bst.getRoot().getParent());
    }

    @Test
    void test_search(){
        var optionalRes = bst.search(100);

        assertEquals(false, optionalRes.isPresent());
    }

    @Test
    void test_max_element_in_BST(){
        var res = bst.max();
        var max = new Node<Integer, String>(14, "IT");
        assertEquals(max, res);
    }

    
    @Test
    void test_get_successor_in_BST(){
       assertEquals(new Node<Integer,String>(5, "IT"), bst.successor(3));
       assertEquals(new Node<Integer,String>(14, "IT"), bst.successor(13));
       assertEquals(new Node<Integer,String>(6,"IT"), bst.successor(5));
       assertEquals(new Node<Integer,String>(13,"IT"), bst.successor(12));

       assertThrows(IllegalArgumentException.class, () -> bst.successor(100));
    }

    @Test
    void test_get_predeseccor_in_BST(){
       assertEquals(new Node<Integer,String>(2, "IT"), bst.predecessor(3));
       assertEquals(new Node<Integer,String>(12, "IT"), bst.predecessor(13));
       assertEquals(new Node<Integer,String>(3,"IT"), bst.predecessor(5));
       assertEquals(new Node<Integer,String>(11,"IT"), bst.predecessor(12));

       assertThrows(IllegalArgumentException.class, () -> bst.predecessor(100));
    }
}

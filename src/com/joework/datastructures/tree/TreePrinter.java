package com.joework.datastructures.tree;

public class TreePrinter<K,V> {
    
    public static <K extends Comparable<K>,V extends Comparable<V>> void printBinaryTree(Node<K,V> root, int level){
        if(root==null)
             return;
        printBinaryTree(root.right, level+1);
        if(level!=0){
            for(int i=0;i<level-1;i++)
                System.out.print("|\t");
            System.out.println("|-------"+root.getKey());
        }
        else
            System.out.println(root.getKey());
        printBinaryTree(root.left, level+1);
    }  

}

package com.company;

//TODO Document
public class Node {
    public final Float value;
    public final int index;
    Node left;
    Node right;
    public Node(Float value, int index){
        this.value = value;
        this.index = index;
        left = null;
        right = null;
    }
}
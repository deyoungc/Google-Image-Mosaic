package com.company;

public class Node {
    Float value;
    public int index;
    Node left;
    Node right;
    public Node(Float value, int index){
        this.value = value;
        this.index = index;
        left = null;
        right = null;
    }
}
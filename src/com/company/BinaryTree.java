package com.company;

public class BinaryTree {
    private Node root;

    public BinaryTree(){
        this.root = null;
    }

    public boolean find(int value){
        Node current = root;
        while(current!=null){
            if(current.value == value){
                return true;
            }else if(current.value > value){
                current = current.left;
            }else{
                current = current.right;
            }
        }
        return false;
    }

    public boolean Insert(int value) {
        Node newNode = new Node(value);

        if (root == null) {
            root = newNode;
            return true;
        }

        Node current = root;
        Node parent;
        while(true){
            parent = current;
            //Don't want a duplicate value.
            if (value == current.value){
                return false;
            }
            else if(value < current.value){
                current = current.left;
                if(current == null){
                    parent.left = newNode;
                    return true;
                }
            }else{
                current = current.right;
                if(current == null){
                    parent.right = newNode;
                    return true;
                }
            }
        }
    }
}

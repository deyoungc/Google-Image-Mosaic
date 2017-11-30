package com.company;

public class BinaryTree {
    private Node root;

    public BinaryTree(){
        this.root = null;
    }

    public boolean find(Float value){
        final float error = 0.001f;

        Node current = root;
        while(current!=null){
            if(Math.abs(current.value - value) < error){
                return true;
            }else if(current.value > value){
                current = current.left;
            }else{
                current = current.right;
            }
        }
        return false;
    }

    public boolean Insert(Float value) {
        final float error = 0.001f;

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
            if(Math.abs(current.value - value) < error){
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

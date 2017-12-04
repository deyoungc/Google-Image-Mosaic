package com.company;

public class BinaryTree {
    private Node root;
    public int size;

    public BinaryTree(){
        this.root = null;
        this.size = 0;
    }

    //TODO Change this to find closest value
    public Node findClosest(Float value){
        final float error = 0.001f;
        //use 256 since that's the max brightness value
        float min = 256f;

        Node current = root;
        Node result = root;
        while(current!=null){
            float diff = Math.abs(value - current.value);

            if(Math.abs(current.value - value) < error){
                return current;

            }else if(current.value > value){
                if (diff < min) {
                    min = diff;
                    result = current;
                }
                current = current.left;
            }else{
                if (diff < min) {
                    min = diff;
                    result = current;
                }
                current = current.right;
            }
        }
        return result;
    }

    public void insert(Float value, int index) {
        size++;
        final float error = 0.001f;

        Node newNode = new Node(value, index);

        if (root == null) {
            root = newNode;
            return;
        }

        Node current = root;
        Node parent;
        while(true){
            parent = current;
            //Don't want a duplicate value.
            if(Math.abs(current.value - value) < error){
                return;
            }
            else if(value < current.value){
                current = current.left;
                if(current == null){
                    parent.left = newNode;
                    return;
                }
            }else{
                current = current.right;
                if(current == null){
                    parent.right = newNode;
                    return;
                }
            }
        }
    }
    public void printTree(){

    }
}

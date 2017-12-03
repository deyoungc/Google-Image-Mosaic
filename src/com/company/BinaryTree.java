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
        while(current!=null){
            if(Math.abs(current.value - value) < error){
                return current;
            }else if(current.value > value){
                float diff = Math.abs(value - current.value);
                if (diff < min) {
                    current = current.left;
                }
            }else{
                float diff = Math.abs(value - current.value);
                if ()
                current = current.right;
            }
        }
        return null;
    }

    public boolean insert(Float value, int index) {
        size++;
        final float error = 0.001f;

        Node newNode = new Node(value, index);

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

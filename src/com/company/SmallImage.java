package com.company;

import processing.core.PImage;

public class SmallImage extends PImage {

    private static int scl;
    public int brightness;

    public SmallImage() {
        super();
        scl = 1;
        this.loadPixels();
    }

    public int getScl() {
        return scl;
    }
    public void setScl(int newScl) {
        scl = newScl;
    }

}

package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MosaicCreator extends PApplet{

    public static void main(String[] args) {


        PApplet.main(MosaicCreator.class.getName());
    }



    //since this will kind of be pixelated we don't need every pixel. Smaller image has less pixels.
    private PImage smallTopResult;
    private PImage[] imgs;
    private BinaryTree brightVal;
    private final int scl = 8;

    private int w;
    private int h;

    public void settings() {
        size(800, 800);
    }

    public void setup() {
        PImage topResult;

        Scanner input = new Scanner(System.in);
        System.out.print("Search for an image: ");
        String search = input.nextLine();

        List<String> resultUrls = getGoogleUrls(search);

        String url = resultUrls.get(0);

        //Sometimes the image url includes .jpg at the end and sometimes it doesn't.
        //The loadImage method needs to know the file extension if the url doesn't include it.
        String fileExt = url.substring(url.length()-4, url.length());
        if(fileExt.equals(".jpg")){
            topResult = loadImage(url);
        }else{
            topResult = loadImage(url, "jpg");
        }

        //Some images returned are really small
        if (topResult.width > topResult.height){
            topResult.resize(800, 0);
        } else {
            topResult.resize(0, 800);
        }

        imgs = new PImage[100];
        brightVal = new BinaryTree();
        for (int i = 0; i < resultUrls.size(); i++) {
            PImage img;

            //load image
            url = resultUrls.get(i);
            fileExt = url.substring(url.length()-4, url.length());
            if(fileExt.equals(".jpg")){
                img = loadImage(resultUrls.get(i));
            }else{
                img = loadImage(resultUrls.get(i), "jpg");
            }

            //scale it down
            imgs[i] = createImage(scl, scl, RGB);
            imgs[i].copy(img, 0,0, img.width, img.height, 0, 0, scl, scl);
            imgs[i].loadPixels();

            //get avg brightness for later
            float avg = 0;
            for (int x = 0; x < imgs[i].pixels.length; x++){
                float b = brightness(imgs[i].pixels[x]);
                avg += b;
            }
            avg /= imgs[i].pixels.length;
            brightVal.insert(avg, i);
        }


        w = topResult.width/scl;
        h = topResult.height/scl;

        smallTopResult = createImage(w, h, RGB);
        smallTopResult.copy(topResult, 0, 0, topResult.width, topResult.height, 0, 0, w, h);

    }

    public void draw() {
        background(0);
        smallTopResult.loadPixels();

        for (int x = 0; x < w; x++){
            for (int y = 0; y < h; y++){
                int index = x + y * w;

                float b = brightness(smallTopResult.pixels[index]);
                int imgIndex = brightVal.findClosest(b).index;
                image(imgs[imgIndex], x*scl, y*scl, scl, scl);
            }
        }
        //image(topResult, 0, 0);

        noLoop();
    }

    private List<String> getGoogleUrls(String search){
        //List of the Images' url
        List<String> resultUrls = new ArrayList<>();

        String userAgent = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116";
        String searchURL = "https://www.google.com/search?site=imghp&tbm=isch&source=hp&q=" + search + "&gws_rd=cr";

        try {
            Document doc = Jsoup.connect(searchURL).userAgent(userAgent).referrer("https://www.google.com/").get();
            Elements elements = doc.select("div.rg_meta");

            //Go through the JSON we got back and get the URLs
            JSONObject jsonObject;
            for (Element element : elements) {
                if (element.childNodeSize() > 0) {
                    jsonObject = JSONObject.parse(element.childNode(0).toString());
                    resultUrls.add(jsonObject.getString("tu"));
                }
            }

            if (resultUrls.size() == 0) {
                System.out.println("No Google image results, please try another search.");
                System.exit(0);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return resultUrls;
    }
}
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

    private PImage img;
    private PImage[] imgs;

    public void settings() {
        //Have to find all the images first to make the Mosaic

//        Scanner input = new Scanner(System.in);
//        System.out.println("Search for an image: ");
        String search = "kittens";

        String userAgent = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36";
        //TODO Change the image to whatever they searched
        String searchURL = "https://www.google.com/search?site=imghp&tbm=isch&source=hp&q=" + search + "&gws_rd=cr";

        try {
            Document doc = Jsoup.connect(searchURL).userAgent(userAgent).referrer("https://www.google.com/").get();

            Elements elements = doc.select("div.rg_meta");

            //List of the Images' url
            List<String> resultUrls = new ArrayList<>();

            //Go through the JSON we got back and get the URLs
            JSONObject jsonObject;
            for (Element element : elements) {
                if (element.childNodeSize() > 0) {
                    jsonObject = JSONObject.parse(element.childNode(0).toString());
                    resultUrls.add(jsonObject.getString("ou"));
                }
            }

            System.out.println("number of results: " + resultUrls.size());

            int j = 1;
            for (String imageUrl : resultUrls) {
                System.out.println(j + ": " + imageUrl);
                j++;
            }

            String url = resultUrls.get(0);

            //Sometimes the image url includes .jpg at the end and sometimes it doesn't.
            //The loadImage method needs to know the file extension and if the url it doesn't include it,
            // it needs to know to add it.
            if(url.substring(url.length()-4, url.length()).equals(".jpg")){
                img = loadImage(url);
            }else{
                img = loadImage(url, "jpg");
            }

            imgs = new PImage[100];
            for (int i = 0; i < 100; i++) {

                if(url.substring(url.length()-4, url.length()).equals(".jpg")){
                    imgs[i] = loadImage(resultUrls.get(i));
                }else{
                    imgs[i] = loadImage(resultUrls.get(i), "jpg");
                }
                System.out.println(resultUrls.get(i));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        size(img.width, img.height);
    }

    public void setup() {

    }

    public void draw() {
        image(img, 0, 0);


        noLoop();
    }
}

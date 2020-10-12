package ua.nure.danylenko.epam.db.entity;

import java.util.ArrayList;
import java.util.List;

public class Product extends Entity{
    private static final long serialVersionUID = -6889036256149495388L;

    private String name;

    private int available;

    private BodySize bodySize;

    private Colour colour;


    private List<String> images;


    public Product(){
        colour=Colour.WHITE;
        bodySize=BodySize.S;
        images = new ArrayList<>();
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public BodySize getBodySize() {
        return bodySize;
    }

    public void setBodySize(BodySize bodySize) {
        this.bodySize = bodySize;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void extractSizeValue(String value){
        if(BodySize.valueOf(value.toUpperCase())!=null){
            BodySize bs = BodySize.valueOf(value.toUpperCase());
            setBodySize(bs);
        }
    }
    public void extractColourValue(String colour){
        if(Colour.valueOf(colour.toUpperCase())!=null){
            Colour cl= Colour.valueOf(colour.toUpperCase());
            setColour(cl);
        }
    }

}

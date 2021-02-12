package ua.nure.danylenko.epam.db.entity;

public class Product extends Entity{
    private static final long serialVersionUID = -6889036256149495388L;

    private String name;

    private int available;

    private BodySize bodySize;

    private String image;


    public Product(){
        bodySize=BodySize.S;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void extractSizeValue(String value){
        if(BodySize.valueOf(value.toUpperCase())!=null){
            BodySize bs = BodySize.valueOf(value.toUpperCase());
            this.setBodySize(bs);
        }
    }

}

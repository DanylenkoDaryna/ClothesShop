package ua.nure.danylenko.epam.db.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Item extends Entity{

    private String itemName;

    private double price;

    private LocalDate releaseDate;

    private String formattedReleaseDate;

    private String brand;

    private Colour colour;

    private List<Material> materials;

    private int categoryId;

    private List<Product> container;

    public Item(){
        itemName = "empty";
        colour=Colour.WHITE;
        materials=new ArrayList<>();
        container = new ArrayList<>();
    }

    @Override
    public String toString() {
        return itemName;
    }

    private void addItem(Product product){
        getContainer().add(product);
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getFormattedReleaseDate() {
        return formattedReleaseDate;
    }

    public void setFormattedReleaseDate(String formattedReleaseDate) {
        this.formattedReleaseDate = formattedReleaseDate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public List<Product>  getContainer() {
        return container;
    }

    public void setContainer(List<Product> container) {
        this.container = container;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public String getRuReleaseDate(){
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        setFormattedReleaseDate(df.format(releaseDate));
        return getFormattedReleaseDate();
    }

    public String getEnReleaseDate(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        setFormattedReleaseDate(df.format(releaseDate));
        return getFormattedReleaseDate();
    }

    public void extractColourValue(String colour){
        if(Colour.valueOf(colour.toUpperCase())!=null){
            Colour cl= Colour.valueOf(colour.toUpperCase());
            this.setColour(cl);
        }
    }
}

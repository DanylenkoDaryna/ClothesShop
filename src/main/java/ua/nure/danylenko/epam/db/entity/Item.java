package ua.nure.danylenko.epam.db.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Item extends Entity{

    private String productName;

    private double price;

    private LocalDate releaseDate;

    private String formattedReleaseDate;

    private String brand;

    private int categoryId;

    private List<Product> container;

    public Item(){
        productName = "empty";
        container = new ArrayList<>();
    }

    @Override
    public String toString() {
        return productName;
    }

    private void addItem(Product product){
        getContainer().add(product);
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String name) {
        this.productName = name;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public List getContainer() {
        return container;
    }

    public void setContainer(List<Product> container) {
        this.container = container;
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


}

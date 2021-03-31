package ua.nure.danylenko.epam.db.entity;

/**
 * The Category class provides fields and methods for describing category entity
 * @version 1.0 30/03/2021
 * @author Daryna Danylenko (delibertato)
 */
public class Category extends Entity {

    private String name;
    private int catalogueId;

    private int categoryId;//

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

    public int getCatalogueId() {
        return catalogueId;
    }

    public void setCatalogueId(int catalogueId) {
        this.catalogueId = catalogueId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }


}

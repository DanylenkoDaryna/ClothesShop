package ua.nure.danylenko.epam.db.entity;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Catalogue{

    private Map<String,List<Category>> container;

    public Catalogue(){
        container = new TreeMap<>();
    }

    public void addInCatalogue(String type, List<Category> category){
        container.put(type,category);
    }

    public Map<String, List<Category>> getContainer() {
        return container;
    }

    public void setContainer(Map<String, List<Category>> container) {
        this.container = container;
    }

}

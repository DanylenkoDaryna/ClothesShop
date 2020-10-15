package ua.nure.danylenko.epam.db.entity;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Material extends Entity{
    private static final long serialVersionUID = -6889036256149495388L;
    private static final Logger DB_LOG = Logger.getLogger("jdbc");

    private String name;

    private int percent;
    private long itemId;

   public Material(String material, int p, long id){
       name=material;
       percent=p;
       itemId = id;
   }

    public Material(){ }

    @Override
    public String toString() {
        return name + " - " + percent + "%" + System.lineSeparator();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        if(percent>0&&percent<=100) {
            this.percent = percent;
        }else{
            DB_LOG.error("value percent is out of range");
        }

    }

    public static List<Material> extractItems(String[] materials, String[] percents, long itemId){
        List<Material> materialsList = new ArrayList<>();
        for(int i=0; i<materials.length; i++){
            Material material= new Material();
            material.setName(materials[i]);
            material.setPercent(Integer.parseInt(percents[i]));
            material.setItemId(itemId);
            materialsList.add(material);
        }
        return materialsList;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
}

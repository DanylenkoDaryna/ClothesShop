package ua.nure.danylenko.epam.db.entity;

import org.apache.log4j.Logger;

/**
 * The Material class provides fields and methods for manipulating with materials for purchases
 * @version 1.0 30/03/2021
 * @author Daryna Danylenko (delibertato)
 */
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

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
}

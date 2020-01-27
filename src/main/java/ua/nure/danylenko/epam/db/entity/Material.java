package ua.nure.danylenko.epam.db.entity;

import org.apache.log4j.Logger;

public class Material extends Entity{
    private static final long serialVersionUID = -6889036256149495388L;
    private static final Logger LOG = Logger.getLogger(Material.class);

    private String name;

    private int percent;

   public Material(String material ,int perc){
       name=material;
       percent=perc;
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
            LOG.error("value percent is out of range");
        }

    }
}

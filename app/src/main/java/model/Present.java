package model;

import java.io.Serializable;

/**
 * Created by Sparta on 1/12/15.
 */
public class Present implements Serializable {
    private static final long serialVersionUID = 10L;
    private String presentName;
    private double presentPrize;
    private int presentId;
    private String recordDate;

    public Present(String nombre, int precio, int id, String date){
        presentName = nombre;
        presentPrize = precio;
        presentId = id;
        recordDate = date;

    }

    public Present(){

    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPresentName() {
        return presentName;
    }

    public void setPresentName(String presentName) {
        this.presentName = presentName;
    }

    public double getPresentPrice() {
        return presentPrize;
    }

    public void setPresentPrice(double price) {
        this.presentPrize = price;
    }

    public int getPresentId() {
        return presentId;
    }

    public void setPresentId(int presentId) {
        this.presentId = presentId;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }
}



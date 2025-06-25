package com.document.allreader.allofficefilereader.models;


public class ModelAcMain {
     int itemImageId;
     String itemName;

    public ModelAcMain(String str, int i) {
        this.itemName = str;
        this.itemImageId = i;
    }

    public String getItemName() {
        return this.itemName;
    }

    public int getItemImageId() {
        return this.itemImageId;
    }
}

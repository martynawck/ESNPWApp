package com.wiacek.martyna.esnpwapp.Domain;

public class DrawerItem {

    String ItemName;
    int imgResID;
    String title;
    boolean isTitle;

    public DrawerItem(String itemName, int imgResID) {
        super();
        ItemName = itemName;
        this.imgResID = imgResID;
    }

    public DrawerItem(String title) {
        this(null,0);
        this.title = title;
        this.isTitle = true;
    }

    public String getItemName() {
        return ItemName;
    }
    public void setItemName(String itemName) {
        ItemName = itemName;
    }
    public int getImgResID() {
        return imgResID;
    }
    public void setImgResID(int imgResID) {
        this.imgResID = imgResID;
    }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public boolean isTitle() { return  isTitle; }

}
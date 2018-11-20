package com.example.a17980.herolist;

import android.graphics.Bitmap;

public class EquipItem {
    private String name;
    private String price;
    private Bitmap image;
    private String base_attr;
    private String equip_skill;

    public void setBase_attr(String base_attr) {
        this.base_attr = base_attr;
    }

    public String getBase_attr() {
        return base_attr;
    }

    public void setEquip_skill(String equip_skill) {
        this.equip_skill = equip_skill;
    }

    public String getEquip_skill() {
        return equip_skill;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}

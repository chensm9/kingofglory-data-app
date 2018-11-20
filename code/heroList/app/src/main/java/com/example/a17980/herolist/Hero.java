package com.example.a17980.herolist;
import android.graphics.Bitmap;

import java.io.Serializable;

public class Hero implements Serializable {
    private Bitmap heroIcon;
    private String heroName;

    Hero(Bitmap heroIcon, String heroName){
        this.heroIcon = heroIcon;
        this.heroName = heroName;
    }

    public Bitmap getHeroIcon(){
        return heroIcon;
    }
    public String getHeroName(){
        return heroName;
    }
    public void setHeroIcon(Bitmap heroIcon){
        this.heroIcon = heroIcon;
    }
    public void setHeroName(String heroName){
        this.heroName = heroName;
    }
}

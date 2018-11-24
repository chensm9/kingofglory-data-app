package com.example.a17980.herolist;
import android.graphics.Bitmap;

import java.io.Serializable;

public class Hero implements Serializable {
    private Bitmap heroIcon;
    private String heroName;
    private String heroRole;
    private Bitmap smallIcon;

    Hero(Bitmap heroIcon, String heroName){
        this.heroIcon = heroIcon;
        this.heroName = heroName;
    }

    public Bitmap getHeroIcon(){
        return heroIcon;
    }
    public Bitmap getSmallIcon() { return smallIcon; }
    public String getHeroName(){
        return heroName;
    }
    public void setHeroIcon(Bitmap heroIcon){
        this.heroIcon = heroIcon;
    }
    public void setHeroName(String heroName){
        this.heroName = heroName;
    }
    public void setSmallIcon(Bitmap icon) { smallIcon = icon; }

    public String getHeroRole() {
        return heroRole;
    }

    public void setHeroRole(String heroRole) {
        this.heroRole = heroRole;
    }
}

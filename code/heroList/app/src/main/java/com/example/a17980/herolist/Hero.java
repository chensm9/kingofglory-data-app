package com.example.a17980.herolist;
import java.io.Serializable;

public class Hero implements Serializable {
    private int heroIcon;
    private String heroName;

    Hero(int heroIcon, String heroName){
        this.heroIcon = heroIcon;
        this.heroName = heroName;
    }

    public int getHeroIcon(){
        return heroIcon;
    }
    public String getHeroName(){
        return heroName;
    }
    public void setHeroIcon(int heroIcon){
        this.heroIcon = heroIcon;
    }
    public void setHeroName(String heroName){
        this.heroName = heroName;
    }
}

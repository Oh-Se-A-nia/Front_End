package com.example.myapplication.model.classification;

import java.io.Serializable;

public class Trash implements Serializable {
    private String name;
    private int image;

    public Trash(){
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}

package com.example.boxmanagementsystem.objects;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Component implements Serializable {
    String name;
    String description;
    Container parent;
    int imageId;


    public abstract String getName();

    abstract String setName();

    public abstract Container getParent();

    public abstract ArrayList<Component> getChildren();

    public abstract int getImageId();

}

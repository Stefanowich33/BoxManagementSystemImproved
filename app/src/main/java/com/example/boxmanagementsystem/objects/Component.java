package com.example.boxmanagementsystem.objects;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Component implements Serializable {
    String name;
    String description;
    Container parent;
    int imageId;


    public abstract String getName();

    public abstract Container getParent();

    public abstract void removeFromChildren(Component toBeRemoved);

    public abstract ArrayList<Component> getChildren();

    public abstract int getImageId();

    public abstract ArrayList<Component> getAllBeneth();

    public abstract Object getLocation();

    public abstract void setParent(Container parent);
}

package com.example.boxmanagementsystem.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Container extends Component implements Serializable {
    private ArrayList<Component> children;
    private Container parent;

    public Container(Container container, String name,int imageId) {
        children = new ArrayList<>();
        parent = container;
        this.name = name;
        this.imageId = imageId;
        parent.addToChildren(this);
    }

    public Container(String name) {
        children = new ArrayList<>();
        this.name = name;
    }

    public ArrayList<Component> getChildren() {
        return children;
    }

    @Override
    public int getImageId() {
        return imageId;
    }


    public void removeChild(Component toBeRemoved) {
        for (int i=0; children.size()>i ;i++){
            if(toBeRemoved.equals(children.get(i))){
                children.remove(i);
            }
        }
    }

    public void addToChildren(Component child){
        children.add(child);
    }

    public Container getParent() {
        return parent;
    }
    public void moveToNewContainer(Container container){
        this.parent = container;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    String setName() {
        return name;
    }
}

package com.example.boxmanagementsystem.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    public Container(Container container, String name,int imageId, ArrayList<Component> children) {
        this.children = children;
        parent = container;
        this.name = name;
        this.imageId = imageId;
        parent.addToChildren(this);
        for (Component child: children) {
            child.setParent(this);
        }
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

    @Override
    public void removeFromChildren(Component toBeRemoved) {
        parent.getChildren().remove(toBeRemoved);
    }

    @Override
    public String getName() {
        return name;
    }
    public void setChildren(ArrayList<Component> children) {
        this.children = children;
    }


    //integrate methods like this instead of micro manageing
    public ArrayList<Component> getAllBeneth(){
        ArrayList<Component> result = new ArrayList<>();
        for (Component children1: children) {
            result = children1.getAllBeneth();
            result.addAll(children);
        }
        return result;
    }

    @Override
    public Object getLocation() {
        return null;
    }

    @Override
    public void setParent(Container parent) {
        this.parent = parent;
    }
}

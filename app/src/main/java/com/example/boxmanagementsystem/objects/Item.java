package com.example.boxmanagementsystem.objects;
import java.io.Serializable;
import java.util.ArrayList;

public class Item extends Component implements Serializable {

    public Item(Container parent,String name, int imageId) {
        this.parent = parent;
        this.name = name;
        this.imageId = imageId;
        parent.addToChildren(this);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    String setName() {
        return name;
    }

    @Override
    public Container getParent() {
        return parent;
    }

    @Override
    public void move(Container destination) {
        parent.removeFromChildren(this);
        destination.addToChildren(this);
    }

    @Override
    public void removeFromChildren(Component toBeRemoved) {
        parent.getChildren().remove(toBeRemoved);
    }

    @Override
    public ArrayList<Component> getChildren() {
        return new ArrayList<>();
    }

    @Override
    public int getImageId() {
        return imageId;
    }
}

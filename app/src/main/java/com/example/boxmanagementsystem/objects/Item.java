package com.example.boxmanagementsystem.objects;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

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
    public Container getParent() {
        return parent;
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

    @Override
    public ArrayList<Component> getAllBeneth() {
        return null;
    }

    public String getLocation(){
        StringBuilder builder = new StringBuilder();
        builder.append("Location: ");
        Stack<String> locationStack = new Stack<>();
        Component parent1 = this;
        while(!parent1.getName().equals("house")){
            parent1 = parent1.getParent();
            String parentName = parent1.getName();
            locationStack.push(parentName);
        }
        while (!locationStack.isEmpty()) {
            builder.append(locationStack.pop() + "/");
        }
        return builder.toString();
    }

    @Override
    public void setParent(Container parent) {
        this.parent = parent;
    }

}

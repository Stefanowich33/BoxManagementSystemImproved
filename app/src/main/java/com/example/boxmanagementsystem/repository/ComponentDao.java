package com.example.boxmanagementsystem.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.boxmanagementsystem.R;
import com.example.boxmanagementsystem.objects.Component;
import com.example.boxmanagementsystem.objects.Container;
import com.example.boxmanagementsystem.objects.Item;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class ComponentDao {
    private ArrayList<Component> dataSet;
    private final MutableLiveData<List<Component>> allComponents = new MutableLiveData<>();
    private static ComponentDao instance;
    private final Container house = new Container("house");;
    private Container currentParent;
    private final MutableLiveData<List<Component>> currentChildren = new MutableLiveData<>();
    private final MutableLiveData<List<Component>> searchedItems = new MutableLiveData<>();
    private final MutableLiveData<List<Component>> otherContainers = new MutableLiveData<>();
    private Container movingContainer;

    private ComponentDao() {
        dataSet = new ArrayList<>();
        makeData();
        setCurrentParent(house.getName());
        allComponents.setValue(dataSet);
        sortToItems();
    }



    public static ComponentDao getInstance(){
        if(instance == null) {
            instance = new ComponentDao();
        }
        return instance;
    }

    public LiveData<List<Component>> getCurrentChildren() {
        return currentChildren;
    }
    public Container getCurrentParent() {
        return currentParent;
    }
    public MutableLiveData<List<Component>> getAllComponents() {
        return allComponents;
    }
    public MutableLiveData<List<Component>> getSearchedItems() {
        return searchedItems;
    }


    public void insert(Component component) {
        dataSet.add(component);
        allComponents.setValue(dataSet);
        setCurrentParent(currentParent.getName());
        sortToItems();
    }

    public void setCurrentParent(String stringQuery) {
        for(Component component: dataSet){
            if(stringQuery.equals(component.getName())){
                currentParent = (Container) component;
                currentChildren.setValue(component.getChildren());
            }
        }
    }

    public void makeData(){
        dataSet.add(house);
        Container livingRoom = new Container(house, "Living Room", R.drawable.parcel);
        dataSet.add(livingRoom);
        Container garage = new Container(house, "Garage", R.drawable.parcel);
        dataSet.add(garage);
        Container box1 = new Container(garage, "Garage Box 1", R.drawable.parcel);
        dataSet.add(box1);
        Container box2 = new Container(garage, "Garage Box 2", R.drawable.parcel);
        dataSet.add(box2);
        dataSet.add(new Item(garage, "Race Bike",R.drawable.bicycle));
        Container box3 = new Container(livingRoom, "Living Room Box 3", R.drawable.parcel);
        dataSet.add(box3);
        Container box4 = new Container(livingRoom, "Living Room Box 4", R.drawable.parcel);
        dataSet.add(box4);
        dataSet.add(new Item(box1, "Iphone",R.drawable.scanner));
        dataSet.add(new Item(box2, "Spayduck",R.drawable.sprayduck));
        dataSet.add(new Item(box3, "Tea",R.drawable.tea));
        dataSet.add(new Item(box4, "Antidote",R.drawable.antidote));
        dataSet.add(new Item(box1, "Mountain Bike",R.drawable.bicycle));
        dataSet.add(new Item(box2, "Calcuim",R.drawable.calcium));
        dataSet.add(new Item(box3, "Ball",R.drawable.snowball));
        dataSet.add(new Item(box4, "Honey",R.drawable.honey));
    }

    public Container getHouse(){
        return house;
    }

    public void search(String newText) {
        List<Component> results = new ArrayList<>();
        for(Component component:dataSet){
            if(component.getName().toLowerCase().contains(newText.toLowerCase()) && component instanceof Item){
                results.add(component);
            }
        }
        searchedItems.setValue(results);
    }
    public void sortToItems() {
        List<Component> results = new ArrayList<>();
        for(Component component:dataSet){
            if(component instanceof Item){
                results.add(component);
            }
        }
        searchedItems.setValue(results);
    }

    public void back() {
        if(!currentParent.equals(house)){
            setCurrentParent(currentParent.getParent().getName());
        }

    }

    public void removeComponent(int adapterPosition) {
        Component toBeRemoved =  currentChildren.getValue().get(adapterPosition);
        for(int i = 0; dataSet.size()>i; i++){
            if(dataSet.get(i).equals(toBeRemoved)){
                currentParent.removeChild(toBeRemoved);
                dataSet.remove(i);
                break;
            }
        }
        allComponents.setValue(dataSet);
        setCurrentParent(currentParent.getName());
        sortToItems();
    }

    public MutableLiveData<List<Component>> getOtherContainers() {
        return otherContainers;
    }
    public void setMovingComponent(Component component){
        Log.d("setMove", "setMovingContainer: ");
        List<Component> result = new ArrayList<>();
        for (Component comp: dataSet){
            if(comp instanceof Container && !(component.getName().equals(comp.getName()))){
                result.add(comp);
            }
        }
        otherContainers.setValue(result);
    }


    public void moveComponent(Component toBeMoved,Container destination, ArrayList<Component> children){
        currentParent.removeChild(toBeMoved);
        dataSet.remove(toBeMoved);
        if(toBeMoved instanceof Container){
            dataSet.add(new Container(destination,toBeMoved.getName(),toBeMoved.getImageId(),children));
            //When moving a container the children are imported as a quick fix, but the children st
        }
        if (toBeMoved instanceof Item){
            dataSet.add(new Item(destination,toBeMoved.getName(),toBeMoved.getImageId()));
        }
        setCurrentParent(currentParent.getName());
        sortToItems();
    }


    public void insertBox(Container newBox) {
        dataSet.add(newBox);
        allComponents.setValue(dataSet);
        setCurrentParent(currentParent.getName());
    }
}

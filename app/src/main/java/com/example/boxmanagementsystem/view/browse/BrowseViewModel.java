package com.example.boxmanagementsystem.view.browse;

import android.text.Editable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.boxmanagementsystem.objects.Component;
import com.example.boxmanagementsystem.objects.Item;
import com.example.boxmanagementsystem.repository.ComponentDao;

import java.util.List;

public class BrowseViewModel extends ViewModel {


    private ComponentDao model = ComponentDao.getInstance();


    public LiveData<List<Component>> getComponents() {
        return model.getAllComponents();
    }

    public void insert(String name,int imageId) {
        model.insert(new Item(model.getCurrentParent(), name, imageId));
    }

    public LiveData<List<Component>> getCurrentChildren() {
        return model.getCurrentChildren();
    }

    public void setCurrentParent(String toString) {
        model.setCurrentParent(toString);
    }

    public void back() {
        model.back();
    }

    public Component getCurrentParent() {
        return model.getCurrentParent();
    }

    public void removeComponent(int adapterPosition) {
        model.removeComponent(adapterPosition);
    }
}
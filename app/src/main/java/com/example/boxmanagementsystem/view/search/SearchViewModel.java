package com.example.boxmanagementsystem.view.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.boxmanagementsystem.objects.Component;
import com.example.boxmanagementsystem.repository.ComponentDao;

import java.util.List;

public class SearchViewModel extends ViewModel {


    private ComponentDao model = ComponentDao.getInstance();


    public LiveData<List<Component>> getSearchedItems() {
        return model.getSearchedItems();
    }

    public void search(String newText) {
        model.search(newText);
    }
}
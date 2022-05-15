package com.example.boxmanagementsystem.view.search;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boxmanagementsystem.R;
import com.example.boxmanagementsystem.adapters.ComponentAdapter;
import com.example.boxmanagementsystem.databinding.FragmentBrowseBinding;
import com.example.boxmanagementsystem.objects.Component;
import com.example.boxmanagementsystem.objects.Item;

import java.util.Stack;

public class SearchFragment extends Fragment {

    private FragmentBrowseBinding binding;
    private ComponentAdapter componentAdapter;
    private RecyclerView recyclerView;
    private View root;

    //For popup information
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private TextView location;
    private TextView itemName;
    private Button exit;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        SearchViewModel searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);



    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_options_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                SearchViewModel searchViewModel = new ViewModelProvider(SearchFragment.this).get(SearchViewModel.class);
                searchViewModel.search(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBrowseBinding.inflate(getLayoutInflater());
        binding = FragmentBrowseBinding.inflate(inflater, container, false);
        inflater.inflate(R.layout.fragment_browse, container, false);
        root = binding.getRoot();

        SearchViewModel searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        componentAdapter = new ComponentAdapter();
        searchViewModel.getSearchedItems().observe(getViewLifecycleOwner(), components -> componentAdapter.update(components));

        componentAdapter.setOnClickListener(component -> {
            createNewInformationDialog(component);
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = root.findViewById(R.id.browseRecycler);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(componentAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void createNewInformationDialog(Component item){
        dialogBuilder = new AlertDialog.Builder(getContext());
        final View informationPopupView = getLayoutInflater().inflate(R.layout.information_popup, null);
        itemName = informationPopupView.findViewById(R.id.itemName);
        location = informationPopupView.findViewById(R.id.item_loaction);
        itemName.setText("Name: " + item.getName());


        location.setText(""+item.getLocation());
        dialogBuilder.setView(informationPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        exit = (Button) informationPopupView.findViewById(R.id.exit_btn);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}
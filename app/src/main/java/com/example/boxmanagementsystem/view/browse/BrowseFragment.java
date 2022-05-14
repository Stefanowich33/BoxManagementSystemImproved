package com.example.boxmanagementsystem.view.browse;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boxmanagementsystem.MainActivity;
import com.example.boxmanagementsystem.R;
import com.example.boxmanagementsystem.adapters.ComponentAdapter;
import com.example.boxmanagementsystem.databinding.ActivityMainBinding;
import com.example.boxmanagementsystem.databinding.FragmentBrowseBinding;
import com.example.boxmanagementsystem.objects.Component;
import com.example.boxmanagementsystem.objects.Container;
import com.example.boxmanagementsystem.objects.Item;

import java.util.Objects;
import java.util.Stack;

public class BrowseFragment extends Fragment {

    private FragmentBrowseBinding binding;
    private ComponentAdapter componentAdapter;
    private RecyclerView recyclerView;
    private View root;

    //For popup Add item
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText editText;
    private Button cancel_btn;
    private Button cancel_btn1;
    private Button add_btn;

    private AlertDialog moveDialog;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBrowseBinding.inflate(getLayoutInflater());
        binding = FragmentBrowseBinding.inflate(inflater, container, false);
        componentAdapter = new ComponentAdapter();
        BrowseViewModel browseViewModel = new ViewModelProvider(this).get(BrowseViewModel.class);
        browseViewModel.getCurrentChildren().observe(getViewLifecycleOwner(), components -> componentAdapter.update(components));
        inflater.inflate(R.layout.fragment_browse, container, false);

        componentAdapter.setOnClickListener(component -> {
            if(component instanceof Container){
                browseViewModel.setCurrentParent(component.getName());
            } else if(component instanceof Item){
            }
        });

        componentAdapter.setLongClickListener(component -> {
            createNewMoveDialog(component);
        });


        root = binding.getRoot();
        setHasOptionsMenu(true);


        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        recyclerView = root.findViewById(R.id.browseRecycler);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        BrowseViewModel browseViewModel = new ViewModelProvider(this).get(BrowseViewModel.class);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if(componentAdapter.getComponents().get(viewHolder.getAdapterPosition())instanceof Item){
                    browseViewModel.removeComponent(viewHolder.getAdapterPosition());
                    componentAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getContext(), "You can't destroy containers", Toast.LENGTH_SHORT).show();
                    componentAdapter.notifyDataSetChanged();
                }

            }
        };
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(componentAdapter);



    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.browse_options_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        BrowseViewModel browseViewModel = new ViewModelProvider(this).get(BrowseViewModel.class);
        int id = item.getItemId();
        if(id == R.id.add_item){
            createNewAddDialog();
        }
        if(id == R.id.back){
            browseViewModel.back();
        }


        return super.onContextItemSelected(item);
    }
    public void createNewAddDialog(){
        dialogBuilder = new AlertDialog.Builder(getContext());
        final View addItemPopupView = getLayoutInflater().inflate(R.layout.make_item_fragment, null);
        add_btn = (Button) addItemPopupView.findViewById(R.id.add_item_btn);
        cancel_btn = (Button) addItemPopupView.findViewById(R.id.cancel_button);
        editText = addItemPopupView.findViewById(R.id.editTextTextName);

        BrowseViewModel browseViewModel = new ViewModelProvider(this).get(BrowseViewModel.class);

        dialogBuilder.setView(addItemPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int imageId = 0;//id for pic goes here
                browseViewModel.insert(editText.getText().toString(), imageId);
                dialog.dismiss();
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void createNewMoveDialog(Component component){

        dialogBuilder = new AlertDialog.Builder(getContext());
        final View moveItemPopupView = getLayoutInflater().inflate(R.layout.move_to_popup, null);
        RecyclerView moveRecyclerView = moveItemPopupView.findViewById(R.id.moveRecycler);
        cancel_btn1 = (Button) moveItemPopupView.findViewById(R.id.cancel_move);
        moveRecyclerView.hasFixedSize();
        moveRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ComponentAdapter componentAdapter1 = new ComponentAdapter();

        BrowseViewModel browseViewModel = new ViewModelProvider(this).get(BrowseViewModel.class);
        browseViewModel.setMovingConatiner(component);
        browseViewModel.getOtherContainers().observe(getViewLifecycleOwner(), components -> componentAdapter1.update(components));

        componentAdapter1.setOnClickListener(container -> {
            component.move((Container) container);
            browseViewModel.moveComponent();
            dialog.dismiss();
        });

        cancel_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        moveRecyclerView.setAdapter(componentAdapter1);


        dialogBuilder.setView(moveItemPopupView);
        dialog = dialogBuilder.create();
        dialog.show();


    }
}
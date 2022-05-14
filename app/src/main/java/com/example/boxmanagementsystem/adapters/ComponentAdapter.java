package com.example.boxmanagementsystem.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boxmanagementsystem.R;
import com.example.boxmanagementsystem.objects.Component;
import com.example.boxmanagementsystem.objects.Container;

import java.util.ArrayList;
import java.util.List;

public class ComponentAdapter extends RecyclerView.Adapter<ComponentAdapter.ViewHolder> {

    private List<Component> components = new ArrayList<>();
    private OnClickListener listener;


    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }
    
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(components.get(position) instanceof Container){
            holder.itemView.setBackgroundColor(Color.parseColor("#add8e6"));
        }
        holder.name.setText(components.get(position).getName());
        holder.image.setImageResource(components.get(position).getImageId());
    }

    @Override
    public int getItemCount() {
        if(!components.isEmpty()){
            return components.size();
        }
        else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(v -> {
                listener.onClick(components.get(getAdapterPosition()));
            });
        }
    }
    public void update(List<Component> components){
        this.components = components;
        notifyDataSetChanged();
    }


    public interface OnClickListener {
        void onClick(Component component);
    }
}
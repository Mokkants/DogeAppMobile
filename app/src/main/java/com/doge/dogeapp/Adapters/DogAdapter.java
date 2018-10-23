package com.doge.dogeapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doge.dogeapp.Activities.R;
import com.doge.dogeapp.Models.Dog;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.DogViewHolder> {
    private LayoutInflater layoutInflater;
    private ArrayList<Dog> listDogs;

    public DogAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.dog_row_item, parent, false);
        DogViewHolder viewHolder = new DogViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
        Dog dog = listDogs.get(position);
        holder.dogName.setText(dog.getName());
        holder.dogBreed.setText(dog.getBreed());
        holder.dogInfo.setText(dog.getShortInfo());
    }

    @Override
    public int getItemCount() {
        return listDogs.size();
    }

    public class DogViewHolder extends RecyclerView.ViewHolder {
        private TextView dogName;
        private TextView dogBreed;
        private TextView dogInfo;

        public DogViewHolder(@NonNull View itemView) {
            super(itemView);
            dogName = itemView.findViewById(R.id.dog_name);
            dogBreed = itemView.findViewById(R.id.dog_breed);
            dogInfo = itemView.findViewById(R.id.dog_info);
        }
    }

    public void setDogList(ArrayList<Dog> dogs) {
        listDogs.addAll(dogs);
        notifyDataSetChanged();
    }
}

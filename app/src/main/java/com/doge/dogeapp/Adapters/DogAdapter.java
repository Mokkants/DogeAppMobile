package com.doge.dogeapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doge.dogeapp.Activities.R;
import com.doge.dogeapp.Models.Dog;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.DogViewHolder> {
    private Context mContext;
    private Dog[] mData;

    public DogAdapter(Context context, Dog[] data)
    {
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.dog_row_item, parent, false);
        return new DogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
        Dog dog = mData[position];
        holder.nameDog.setText(dog.getName());
        holder.breedDog.setText(dog.getBreed());
        holder.socialDog.setText(dog.getShortInfo());
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    public class DogViewHolder extends RecyclerView.ViewHolder {
        TextView nameDog,breedDog,socialDog,infoDog;
        public DogViewHolder(@NonNull View itemView) {
            super(itemView);
            nameDog = itemView.findViewById(R.id.dog_name);
            breedDog = itemView.findViewById(R.id.dog_breed);
            socialDog = itemView.findViewById(R.id.dog_social);
            infoDog = itemView.findViewById(R.id.dog_info);
        }
    }
}

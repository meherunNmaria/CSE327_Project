package com.example.ambulanceapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements Filterable {

    ArrayList<Model> arrayList;
    ArrayList<Model> getModelListFilter;
    Context context;
    UserClickListener userClickListener;


    public Adapter(ArrayList<Model> arrayList, Context context, UserClickListener userClickListener) {
        this.arrayList = arrayList;
        this.getModelListFilter = arrayList;
        this.context = context;
        this.userClickListener = userClickListener;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if(charSequence == null || charSequence.length() == 0){
                    filterResults.values = getModelListFilter;
                    filterResults.count = getModelListFilter.size();
                }else{
                    String searchStr = charSequence.toString().toLowerCase();
                    ArrayList<Model> arrayList = new ArrayList<>();
                    for(Model model: getModelListFilter){
                        if(model.getName().toLowerCase().contains(searchStr)){
                            arrayList.add(model);
                        }
                    }
                    filterResults.values = arrayList;
                    filterResults.count = arrayList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                arrayList = (ArrayList<Model>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public interface UserClickListener{
        void selectedUser(Model model);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hospital_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model model = arrayList.get(position);
        holder.name.setText(model.getName());
        holder.distance.setText(model.getDistance());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userClickListener.selectedUser(model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,distance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            distance = itemView.findViewById(R.id.distance);
        }
    }
}

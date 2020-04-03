package com.example.weather;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import data.CityDbHelper;
import data.CityModel;

public class CitiesAdded extends RecyclerView.Adapter<CitiesAdded.ViewHolder> {
    CityDbHelper dbHelper;
    Context context;
    private List<String> MyCities=new ArrayList<>();

    public CitiesAdded(List<String> MyCity, Context context){
        MyCities.addAll(MyCity);
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_cities_layouy,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        dbHelper=new CityDbHelper(context);
        String CityName=dbHelper.IdToName(Long.parseLong(MyCities.get(position)));
        holder.city.setText(CityName);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper=new CityDbHelper(context);
                dbHelper.UpdateCitySelected(Long.valueOf(MyCities.get(position)),false);
                MyCities.remove(MyCities.get(position));
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return MyCities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btn;
        TextView city;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            btn=itemView.findViewById(R.id.btn);
            city=itemView.findViewById(R.id.city_name);
        }
    }
}

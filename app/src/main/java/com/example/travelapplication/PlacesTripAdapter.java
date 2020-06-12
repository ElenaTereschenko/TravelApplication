package com.example.travelapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PlacesTripAdapter extends RecyclerView.Adapter<PlacesTripAdapter.NumberlistPlacesHolder> {
    private List<PlaceTrip> places;


    public PlacesTripAdapter (List<PlaceTrip> places){

        this.places = places;

    }

    @NonNull
    @Override
    public NumberlistPlacesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListPlace = R.layout.number_list_place;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListPlace, parent, false);

        NumberlistPlacesHolder viewHolder = new NumberlistPlacesHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NumberlistPlacesHolder holder, int position) {
        holder.bind(position);


    }



    @Override
    public int getItemCount() {
        return places.size();
    }



    class NumberlistPlacesHolder extends RecyclerView.ViewHolder {

            TextView namePlace;
            TextView periodPlace;
            TextView adressPlace;

            public NumberlistPlacesHolder(View itemView){
                super(itemView);

                namePlace = itemView.findViewById(R.id.textview_listPlace_name);
                periodPlace = itemView.findViewById(R.id.textView_listPlace_period);
                adressPlace = itemView.findViewById(R.id.textview_listPlace_adress);
            }

            void bind(int position){
                namePlace.setText(places.get(position).getName());
                periodPlace.setText(places.get(position).getPeriod());
                adressPlace.setText(places.get(position).getAdress());
            }


        }
}

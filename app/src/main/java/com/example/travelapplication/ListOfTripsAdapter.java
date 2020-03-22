package com.example.travelapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListOfTripsAdapter extends RecyclerView.Adapter<ListOfTripsAdapter.NumberlistTripsHolder> {


    private List<Trip> trips;


    public ListOfTripsAdapter (List<Trip> trips){
        this.trips = trips;
    }

    @NonNull
    @Override
    public NumberlistTripsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListTrip = R.layout.number_list_trip;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListTrip, parent, false);

        NumberlistTripsHolder viewHolder = new NumberlistTripsHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NumberlistTripsHolder holder, int position) {
        holder.bind(position);


    }



    @Override
    public int getItemCount() {
        return trips.size();
    }




    class NumberlistTripsHolder extends RecyclerView.ViewHolder {

        TextView nameTrip;
        TextView periodTrip;

        public NumberlistTripsHolder(View itemView){
            super(itemView);

            nameTrip = itemView.findViewById(R.id.textview_listTrip_name);
            periodTrip = itemView.findViewById(R.id.textView_listTrip_period);


        }






        void bind(int position){
            nameTrip.setText(trips.get(position).getName());
            periodTrip.setText(trips.get(position).getPeriod());
        }


    }
}

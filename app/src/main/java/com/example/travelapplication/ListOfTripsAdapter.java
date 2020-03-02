package com.example.travelapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListOfTripsAdapter extends RecyclerView.Adapter<ListOfTripsAdapter.NumberlistTripsHolder> {

    private static int viewHolderCount;
    private int numberItems;

    public ListOfTripsAdapter (int numberOfItems){
        numberOfItems = numberOfItems;
        viewHolderCount = 0;
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
        return numberItems;
    }

    class NumberlistTripsHolder extends RecyclerView.ViewHolder{

        TextView nameTrip;

        public NumberlistTripsHolder(View itemView){
            super(itemView);

            nameTrip = itemView.findViewById(R.id.textview_listTrip_name);
        }

        void bind(int nameOfTrip){
            nameTrip.setText(nameOfTrip);
        }
    }
}

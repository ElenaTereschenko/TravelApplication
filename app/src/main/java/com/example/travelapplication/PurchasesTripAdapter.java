package com.example.travelapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PurchasesTripAdapter extends RecyclerView.Adapter<PurchasesTripAdapter.NumberlistPurchasesHolder>  {
    private List<Purchase> purchases;
    private boolean isVisible;

    public PurchasesTripAdapter (List<Purchase> purchases, boolean isVisible){
        this.purchases = purchases;
        this.isVisible = isVisible;
    }


    @NonNull
    @Override
    public PurchasesTripAdapter.NumberlistPurchasesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListGood = R.layout.number_list_purchase;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListGood, parent, false);

        PurchasesTripAdapter.NumberlistPurchasesHolder viewHolder = new PurchasesTripAdapter.NumberlistPurchasesHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PurchasesTripAdapter.NumberlistPurchasesHolder holder, int position) {
        holder.bind(position);
    }

    public void updateDate(List<Purchase> purchases){
        this.purchases = purchases;
    }

    public void changeVisibility(boolean isVisible){
        this.isVisible = isVisible;
    }

    @Override
    public int getItemCount() {
        return purchases.size();
    }



    class NumberlistPurchasesHolder extends RecyclerView.ViewHolder {

        CheckedTextView purchase;
        EditText purchaseEdit;
        TextView cost;
        TextView category;


        public NumberlistPurchasesHolder(View itemView){
            super(itemView);

            purchase = itemView.findViewById(R.id.checkedTextView_numberListPurchase_purchase);
            purchaseEdit = itemView.findViewById(R.id.editText_numberListPurchase_purchase);
            cost = itemView.findViewById(R.id.textView_numberListPurchase_cost);
            category = itemView.findViewById(R.id.textView_numberListPurchase_category);


        }

        void bind(int position){
            purchase.setText(purchases.get(position).getName());
            purchase.setChecked(false);
            purchaseEdit.setText(purchases.get(position).getName());
            cost.setText("" + purchases.get(position).getCost());
            category.setText(purchases.get(position).getCategory());
            if (isVisible) {
                purchase.setVisibility(View.VISIBLE);
                purchaseEdit.setVisibility(View.INVISIBLE);}
            else{
                purchase.setVisibility(View.INVISIBLE);
                purchaseEdit.setVisibility(View.VISIBLE);
            }



            purchase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    purchase.setChecked(true);
                }
            });
        }


    }
}

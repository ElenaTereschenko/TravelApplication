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

import java.util.List;

public class GoodsTripAdapter extends RecyclerView.Adapter<GoodsTripAdapter.NumberlistGoodsHolder> {
    private List<Good> goods;
    private boolean isVisible;

    public GoodsTripAdapter (List<Good> goods, boolean isVisible){
        this.goods = goods;
        this.isVisible = isVisible;
    }

    @NonNull
    @Override
    public GoodsTripAdapter.NumberlistGoodsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListGood = R.layout.number_list_good;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListGood, parent, false);

        GoodsTripAdapter.NumberlistGoodsHolder viewHolder = new GoodsTripAdapter.NumberlistGoodsHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsTripAdapter.NumberlistGoodsHolder holder, int position) {
        holder.bind(position);
    }

    public void updateDate(List<Good> goods){
        this.goods = goods;
    }

    public void changeVisibility(boolean isVisible){
        this.isVisible = isVisible;
    }

    @Override
    public int getItemCount() {
        return goods.size();
    }


    class NumberlistGoodsHolder extends RecyclerView.ViewHolder {

        CheckedTextView good;
        EditText goodEdit;
        TextView count;
        Button addButton;
        Button removeButton;

        public NumberlistGoodsHolder(View itemView){
            super(itemView);

            good = itemView.findViewById(R.id.checkedTextView_numberListGood_good);
            goodEdit = itemView.findViewById(R.id.editText_numberListGood_good);
            count = itemView.findViewById(R.id.textView_numberListGood_count);
            addButton = itemView.findViewById(R.id.button_numberListGood_addCount);
            removeButton = itemView.findViewById(R.id.button_numberListGood_removeCount);

        }

        void bind(int position){
            good.setText(goods.get(position).getName());
            good.setChecked(goods.get(position).isTook());
            goodEdit.setText(goods.get(position).getName());
            count.setText("" + goods.get(position).getCount());
            if (isVisible) {
                good.setVisibility(View.VISIBLE);
                goodEdit.setVisibility(View.INVISIBLE);}
            else{
                good.setVisibility(View.INVISIBLE);
                goodEdit.setVisibility(View.VISIBLE);
            }

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count.setText(""+(goods.get(position).getCount() + 1));
                    goods.get(position).setCount(goods.get(position).getCount() + 1);
                }
            });

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count.setText("" +(goods.get(position).getCount() - 1));
                    goods.get(position).setCount(goods.get(position).getCount() - 1);
                }
            });

            good.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    good.setChecked(true);
                }
            });
        }


    }
}

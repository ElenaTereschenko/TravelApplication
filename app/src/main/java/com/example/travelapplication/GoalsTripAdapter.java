package com.example.travelapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GoalsTripAdapter  extends RecyclerView.Adapter<GoalsTripAdapter.NumberlistGoalsHolder>{
    private List<Goal> goals;

    public GoalsTripAdapter (List<Goal> goals){
        this.goals = goals;
    }

    @NonNull
    @Override
    public GoalsTripAdapter.NumberlistGoalsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListGoal = R.layout.number_list_goal;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListGoal, parent, false);

        GoalsTripAdapter.NumberlistGoalsHolder viewHolder = new GoalsTripAdapter.NumberlistGoalsHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GoalsTripAdapter.NumberlistGoalsHolder holder, int position) {
        holder.bind(position);
    }

    public void updateDate(List<Goal> goals){
        this.goals = goals;
    }

    @Override
    public int getItemCount() {
        return goals.size();
    }


    class NumberlistGoalsHolder extends RecyclerView.ViewHolder {

        CheckedTextView goal;

        public NumberlistGoalsHolder(View itemView){
            super(itemView);

            goal = itemView.findViewById(R.id.checkedTextView_numberListGoal_goal);
        }

        void bind(int position){
            goal.setText(goals.get(position).getName());
            goal.setChecked(goals.get(position).isDone());
        }


    }
}

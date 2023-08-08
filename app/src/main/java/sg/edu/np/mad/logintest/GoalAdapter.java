package sg.edu.np.mad.logintest;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalViewHolder> {

    private List<Goal> goalList;
    private GoalDatabaseHelper databaseHelper;

    public GoalAdapter(List<Goal> goalList, GoalDatabaseHelper databaseHelper) {
        this.goalList = goalList;
        this.databaseHelper = databaseHelper;
    }
    @NonNull
    @Override
    public GoalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View goalView = inflater.inflate(R.layout.item_goal, parent, false);

        return new GoalViewHolder(goalView);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalViewHolder holder, int position) {
        Goal goal = goalList.get(position);
        holder.bind(goal);
    }

    @Override
    public int getItemCount() {
        return goalList.size();
    }

    public void setFilteredList(List<Goal> filteredList) {
        goalList = filteredList;
        notifyDataSetChanged();
    }

    public class GoalViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewGoalName;
        private TextView textViewGoalDescription;
        private TextView textViewGoalDueDate;
        private Button buttonEditGoal;
        private Button buttonDeleteGoal;

        public GoalViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewGoalName = itemView.findViewById(R.id.textViewGoalName);
            textViewGoalDescription = itemView.findViewById(R.id.textViewGoalDescription);
            textViewGoalDueDate = itemView.findViewById(R.id.textViewGoalDueDate);
            buttonEditGoal = itemView.findViewById(R.id.buttonEditGoal);
            buttonDeleteGoal = itemView.findViewById(R.id.buttonDeleteGoal);
            buttonDeleteGoal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        long goalId = goalList.get(position).getId();
                        deleteGoal(goalId);
                    }
                }
            });
        }

        private void deleteGoal(long goalId) {
            Log.d("Debug", "Deleting goal with ID: " + goalId);
            databaseHelper.deleteGoal(goalId);

            // Update goalList and notify adapter
            int position = findGoalPosition(goalId);
            if (position != -1) {
                goalList.remove(position);
                notifyItemRemoved(position);
            }
        }
        private int findGoalPosition(long goalId) {
            for (int i = 0; i < goalList.size(); i++) {
                if (goalList.get(i).getId() == goalId) {
                    return i;
                }
            }
            return -1;
        }

        public void bind(Goal goal) {
            textViewGoalName.setText(goal.getName());
            textViewGoalDescription.setText(goal.getDescription());

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String dueDateFormatted = sdf.format(new Date(goal.getDueDateMillis()));
            textViewGoalDueDate.setText(dueDateFormatted);

            buttonEditGoal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        long selectedGoalId = goalList.get(position).getId();  // Assuming getId() returns the goal ID
                        Intent intent = new Intent(itemView.getContext(), EditGoalActivity.class);
                        intent.putExtra("goal_id", selectedGoalId);
                        itemView.getContext().startActivity(intent);
                    }
                }
            });


            buttonDeleteGoal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        long goalId = goalList.get(position).getId();
                        databaseHelper.deleteGoal(goalId);
                        goalList.remove(position);
                        notifyItemRemoved(position);
                    }
                }
            });
        }
    }
}
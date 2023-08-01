package sg.edu.np.mad.logintest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalViewHolder> {

    private List<Goal> goalList;

    public GoalAdapter(List<Goal> goalList) {
        this.goalList = goalList;
    }

    @NonNull
    @Override
    public GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goal, parent, false);
        return new GoalViewHolder(view);
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

    public class GoalViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewGoalName;
        private TextView textViewGoalDescription;
        private TextView textViewGoalDueDate;

        public GoalViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewGoalName = itemView.findViewById(R.id.textViewGoalName);
            textViewGoalDescription = itemView.findViewById(R.id.textViewGoalDescription);
            textViewGoalDueDate = itemView.findViewById(R.id.textViewGoalDueDate);
        }

        public void bind(Goal goal) {
            textViewGoalName.setText(goal.getName());
            textViewGoalDescription.setText(goal.getDescription());

            // Format the due date in your desired format
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String dueDateFormatted = sdf.format(new Date(goal.getDueDateMillis()));
            textViewGoalDueDate.setText(dueDateFormatted);
        }
    }
}
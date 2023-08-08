package sg.edu.np.mad.logintest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class EditGoalActivity extends AppCompatActivity {

    private long goalId;
    private GoalDatabaseHelper databaseHelper;

    private EditText editTextGoalName;
    private EditText editTextGoalDescription;
    private DatePicker datePickerGoalDueDate;
    private Button buttonSaveEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_goal);

        goalId = getIntent().getLongExtra("goal_id", -1); // Get goal ID from intent
        databaseHelper = new GoalDatabaseHelper(this);

        editTextGoalName = findViewById(R.id.editTextGoalName);
        editTextGoalDescription = findViewById(R.id.editTextGoalDescription);
        datePickerGoalDueDate = findViewById(R.id.datePickerGoalDueDate);
        buttonSaveEdit = findViewById(R.id.buttonSaveEdit);

        Goal goal = databaseHelper.getGoalById(goalId);
        if (goal != null) {
            editTextGoalName.setText(goal.getName());
            editTextGoalDescription.setText(goal.getDescription());

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(goal.getDueDateMillis());
            datePickerGoalDueDate.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        }
        buttonSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGoal();
            }
        });
    }

    private void updateGoal() {
        String updatedName = editTextGoalName.getText().toString();
        String updatedDescription = editTextGoalDescription.getText().toString();

        int day = datePickerGoalDueDate.getDayOfMonth();
        int month = datePickerGoalDueDate.getMonth();
        int year = datePickerGoalDueDate.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        long updatedDueDateMillis = calendar.getTimeInMillis();


        Goal updatedGoal = new Goal(goalId, updatedName, updatedDescription, updatedDueDateMillis, 0);
        databaseHelper.updateGoal(updatedGoal);

        Toast.makeText(this, "Goal updated!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
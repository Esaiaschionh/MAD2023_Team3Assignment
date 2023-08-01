package sg.edu.np.mad.logintest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class GoalTrackerActivity extends AppCompatActivity {

    private EditText editTextGoalName;
    private EditText editTextGoalDescription;
    private DatePicker datePickerGoalDueDate;
    private GoalDatabaseHelper databaseHelper;
    ImageButton menu2Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_tracker);

        menu2Btn = findViewById(R.id.menu2_btn);
        menu2Btn.setOnClickListener((v)->showMenu() );

        editTextGoalName = findViewById(R.id.editTextGoalName);
        editTextGoalDescription = findViewById(R.id.editTextGoalDescription);
        datePickerGoalDueDate = findViewById(R.id.datePickerGoalDueDate);

        databaseHelper = new GoalDatabaseHelper(this);

        Button buttonSaveGoal = findViewById(R.id.buttonSaveGoal);
        buttonSaveGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGoal();
            }
        });

        Button buttonDisplayGoals = findViewById(R.id.buttonDisplayGoals);
        buttonDisplayGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayGoals();
            }
        });

    }


    private void saveGoal() {
        String goalName = editTextGoalName.getText().toString();
        String goalDescription = editTextGoalDescription.getText().toString();

        // Get the selected due date from the DatePicker
        int day = datePickerGoalDueDate.getDayOfMonth();
        int month = datePickerGoalDueDate.getMonth();
        int year = datePickerGoalDueDate.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        long dueDateMillis = calendar.getTimeInMillis();

        // Save the goal to the database or local storage
        Goal goal = new Goal(goalName, goalDescription, dueDateMillis, 0);
        long id = databaseHelper.insertGoal(goal);

        if (id > 0) {
            goal.setId(id);
            Toast.makeText(this, "Goal saved!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to save goal!", Toast.LENGTH_SHORT).show();
        }

    }

    private void displayGoals() {
        // Start the DisplayGoalsActivity to display the list of goals
        Intent intent = new Intent(this, DisplayGoalsActivity.class);
        startActivity(intent);
    }



    void showMenu(){
        PopupMenu popupMenu = new PopupMenu(GoalTrackerActivity.this, menu2Btn);
        popupMenu.getMenu().add("Home");
        popupMenu.getMenu().add("Logout");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getTitle()=="Logout"){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(GoalTrackerActivity.this,LoginActivity.class));
                    finish();
                    Utility.showToast(GoalTrackerActivity.this,"Logout successful");
                    return true;
                }
                if(menuItem.getTitle()=="Home"){
                    startActivity(new Intent(GoalTrackerActivity.this,MainActivity.class));
                    finish();
                    Utility.showToast(GoalTrackerActivity.this,"Welcome back to the homepage");
                    return true;
                }
                return false;
            }
        });
    }
}
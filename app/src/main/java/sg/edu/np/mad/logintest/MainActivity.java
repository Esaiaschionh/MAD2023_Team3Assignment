package sg.edu.np.mad.logintest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button task_planner_button;
    private Button study_SAA_button;
    private Button task_Time_Tracking_button;
    private Button new_Notes_button;
    private Button goal_Tracker_button;

    private Button tracking_button;

    private Button stats_button;
    ImageButton menu2Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menu2Btn = findViewById(R.id.menu2_btn);

        task_planner_button = (Button) findViewById(R.id.task_planner_btn);
        task_planner_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTaskPlannerActivity();
            }
        });
        goal_Tracker_button= (Button) findViewById(R.id.goal_Tracker_btn);
        goal_Tracker_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGoalTrackerActivity();
            }
        });

        study_SAA_button = (Button) findViewById(R.id.study_SAA_btn);
        study_SAA_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStudySAActivity();
            }
        });

        task_Time_Tracking_button = (Button) findViewById(R.id.task_Time_Tracking_btn);
        task_Time_Tracking_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTaskTimeTrackingActivity();
            }
        });

        new_Notes_button = (Button) findViewById(R.id.new_Notes_btn);
        new_Notes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNotesActivity();
            }
        });

        tracking_button = (Button) findViewById(R.id.tracking_btn);
        tracking_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTrackingActivity();
            }
        });

        stats_button = (Button) findViewById(R.id.stats_btn);
        stats_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStatsActivity();
            }
        });

        menu2Btn.setOnClickListener((v)->showMenu() );
    }
    public void openTaskPlannerActivity(){
        Intent intent = new Intent(this, TasksPlannerActivity.class);
        startActivity(intent);
    }

    public void openStudySAActivity(){
        Intent intent = new Intent(this, StudyStatisticsAnalyticsActivity.class);
        startActivity(intent);
    }

    public void openTaskTimeTrackingActivity(){
        Intent intent = new Intent(this, TaskTimeTrackingActivity.class);
        startActivity(intent);
    }

    public void openNotesActivity(){
        Intent intent = new Intent(this, NotesActivity.class);
        startActivity(intent);
    }
    public void openGoalTrackerActivity(){
        Intent intent = new Intent(this, GoalTrackerActivity.class);
        startActivity(intent);
    }

    public void openTrackingActivity(){
        Intent intent = new Intent(this, Tracking.class);
        startActivity(intent);
    }

    public void openStatsActivity(){
        Intent intent = new Intent(this, Stats.class);
        startActivity(intent);
    }

    void showMenu(){
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, menu2Btn);
        popupMenu.getMenu().add("Logout");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getTitle()=="Logout"){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    finish();
                    Utility.showToast(MainActivity.this,"Logout successful");
                    return true;
                }
                return false;

            }
        });
    }

}
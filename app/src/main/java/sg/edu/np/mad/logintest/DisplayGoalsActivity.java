package sg.edu.np.mad.logintest;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class DisplayGoalsActivity extends AppCompatActivity {

    private List<Goal> goalList;
    private GoalAdapter goalAdapter;

    private GoalDatabaseHelper databaseHelper;

    ImageButton menu3Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_goals);

        menu3Btn = findViewById(R.id.menu2_btn);
        menu3Btn.setOnClickListener((v)->showMenu() );

        RecyclerView recyclerViewGoals = findViewById(R.id.recyclerViewGoals);
        recyclerViewGoals.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new GoalDatabaseHelper(this);
        goalList = databaseHelper.getAllGoals();
        goalAdapter = new GoalAdapter(goalList);
        recyclerViewGoals.setAdapter(goalAdapter);
    }

    void showMenu(){
        PopupMenu popupMenu = new PopupMenu(DisplayGoalsActivity.this, menu3Btn);
        popupMenu.getMenu().add("Home");
        popupMenu.getMenu().add("Logout");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getTitle()=="Logout"){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(DisplayGoalsActivity.this,LoginActivity.class));
                    finish();
                    Utility.showToast(DisplayGoalsActivity.this,"Logout successful");
                    return true;
                }
                if(menuItem.getTitle()=="Home"){
                    startActivity(new Intent(DisplayGoalsActivity.this,MainActivity.class));
                    finish();
                    Utility.showToast(DisplayGoalsActivity.this,"Welcome back to the homepage");
                    return true;
                }
                return false;
            }
        });
    }

    private List<Goal> retrieveGoals() {
        return databaseHelper.getAllGoals();
    }
}

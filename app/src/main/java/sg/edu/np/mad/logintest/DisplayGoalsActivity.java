package sg.edu.np.mad.logintest;


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class DisplayGoalsActivity extends AppCompatActivity {

    private List<Goal> goalList;
    private GoalAdapter goalAdapter;

    ImageButton menu3Btn;

    EditText editTextSearch;

    private GoalDatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_goals);

        menu3Btn = findViewById(R.id.menu2_btn);
        menu3Btn.setOnClickListener((v)->showMenu() );

        RecyclerView recyclerViewGoals = findViewById(R.id.recyclerViewGoals);
        recyclerViewGoals.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new GoalDatabaseHelper(this);
        goalList = retrieveGoals();
        goalAdapter = new GoalAdapter(goalList, databaseHelper);

        recyclerViewGoals.setAdapter(goalAdapter);

        editTextSearch = findViewById(R.id.editTextSearch);
        editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        (event != null && event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                                    performSearch(v.getText().toString());
                                    return true;
                                }
                                return false;
            }
        });
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

    private void performSearch(String query) {
        List<Goal> filteredGoals = new ArrayList<>();

        for (Goal goal : goalList) {
            if (goal.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredGoals.add(goal);
            }
        }

        goalAdapter.setFilteredList(filteredGoals);
    }
}

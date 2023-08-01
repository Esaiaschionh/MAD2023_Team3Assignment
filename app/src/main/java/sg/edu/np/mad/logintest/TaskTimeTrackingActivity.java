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

public class TaskTimeTrackingActivity extends AppCompatActivity {
    ImageButton menu2Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_time_tracking);

        menu2Btn = findViewById(R.id.menu2_btn);
        menu2Btn.setOnClickListener((v)->showMenu() );
    }

    void showMenu(){
        PopupMenu popupMenu = new PopupMenu(TaskTimeTrackingActivity.this, menu2Btn);
        popupMenu.getMenu().add("Home");
        popupMenu.getMenu().add("Logout");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getTitle()=="Logout"){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(TaskTimeTrackingActivity.this,LoginActivity.class));
                    finish();
                    Utility.showToast(TaskTimeTrackingActivity.this,"Logout successful");
                    return true;
                }
                if(menuItem.getTitle()=="Home"){
                    startActivity(new Intent(TaskTimeTrackingActivity.this,MainActivity.class));
                    finish();
                    Utility.showToast(TaskTimeTrackingActivity.this,"Welcome back to the homepage");
                    return true;
                }
                return false;

            }
        });
    }
}
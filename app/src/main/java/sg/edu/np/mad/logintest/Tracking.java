package sg.edu.np.mad.logintest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.google.firebase.auth.FirebaseAuth;

public class Tracking extends AppCompatActivity {

    ImageButton menu2Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_planner);

        menu2Btn = findViewById(R.id.menu2_btn);
        menu2Btn.setOnClickListener((v)->showMenu() );
    }

    void showMenu(){
        PopupMenu popupMenu = new PopupMenu(Tracking.this, menu2Btn);
        popupMenu.getMenu().add("Home");
        popupMenu.getMenu().add("Logout");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getTitle()=="Logout"){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(Tracking.this,LoginActivity.class));
                    finish();
                    Utility.showToast(Tracking.this,"Logout successful");
                    return true;
                }
                if(menuItem.getTitle()=="Home"){
                    startActivity(new Intent(Tracking.this,MainActivity.class));
                    finish();
                    Utility.showToast(Tracking.this,"Welcome back to the homepage");
                    return true;
                }
                return false;
            }
        });
    }
}
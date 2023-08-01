package sg.edu.np.mad.logintest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Query;

public class NotesActivity extends AppCompatActivity {
    Button addNoteBtn;
    RecyclerView recyclerView;
    ImageButton menu2Btn;
    NoteAdapter noteAdapter;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);


        addNoteBtn = findViewById(R.id.add_Notes_btn);
        recyclerView = findViewById(R.id.recycler_View);
        menu2Btn = findViewById(R.id.menu2_btn);

        addNoteBtn.setOnClickListener((v -> startActivity(new Intent(NotesActivity.this,NoteDetailsActivity.class))));
        menu2Btn.setOnClickListener((v)->showMenu() );
        setupRecyclerView();
    }


    void showMenu(){
        PopupMenu popupMenu = new PopupMenu(NotesActivity.this, menu2Btn);
        popupMenu.getMenu().add("Home");
        popupMenu.getMenu().add("Logout");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getTitle()=="Logout"){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(NotesActivity.this,LoginActivity.class));
                    finish();
                    Utility.showToast(NotesActivity.this,"Logout successful");
                    return true;
                }
                if(menuItem.getTitle()=="Home"){
                    startActivity(new Intent(NotesActivity.this,MainActivity.class));
                    finish();
                    Utility.showToast(NotesActivity.this,"Welcome back to the homepage");
                    return true;
                }
                return false;

            }
        });
    }

    void setupRecyclerView(){
        Query query = Utility.getCollectionReferenceForNotes();
        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>().setQuery(query, Note.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(options, this);
        recyclerView.setAdapter(noteAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        noteAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        noteAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        noteAdapter.notifyDataSetChanged();
    }
}
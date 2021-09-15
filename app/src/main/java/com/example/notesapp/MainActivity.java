package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private NoteViewModel viewModel;
    public static final int NEW_NOTE_REQUEST_CODE = 101;
    private static final String INTENT_TITLE = "title";
    private static final String INTENT_DESC = "desc";
    private TextView tvNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        tvNotes = findViewById(R.id.tv_notes);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
            startActivityForResult(intent, NEW_NOTE_REQUEST_CODE);
        });

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(NoteViewModel.class);

        viewModel.getAllNotes().observe(this, (List<Note> notes) -> {
            //update RecyclerView
            //Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
            tvNotes.setText("");
            for(Note note : notes) {
                Log.d(TAG, "onChanged: "+note.getTitle());
                tvNotes.append(note.getTitle()+"\n");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
            if(data != null) {
                Log.d(TAG, "onActivityResult: "+data.getStringExtra("title"));
                Note note = new Note(data.getStringExtra(INTENT_TITLE), data.getStringExtra(INTENT_DESC));
                viewModel.insert(note);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
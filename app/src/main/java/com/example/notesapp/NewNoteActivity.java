package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NewNoteActivity extends AppCompatActivity {
    private static final String INTENT_TITLE = "title";
    private static final String INTENT_DESC = "desc";
    public static final int NEW_NOTE_RESULT_CODE = 101;

    private EditText etTitle, etDesc;
    private Button btnAddNote;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newnote);

        init();
        btnAddNote.setOnClickListener(v -> {
            String title = etTitle.getText().toString();
            String desc = etDesc.getText().toString();
            Intent intent = new Intent();
            intent.putExtra(INTENT_TITLE, title);
            intent.putExtra(INTENT_DESC, desc);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    private void init() {
        etTitle = findViewById(R.id.et_title);
        etDesc = findViewById(R.id.et_desc);
        btnAddNote = findViewById(R.id.btn_addnote);
    }
}

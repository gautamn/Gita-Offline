package com.gita.holybooks.bhagwatgita.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.gita.holybooks.bhagwatgita.R;
import com.gita.holybooks.bhagwatgita.adapter.NoteAdapter;
import com.gita.holybooks.bhagwatgita.dto.Note;
import com.gita.holybooks.bhagwatgita.service.DatabaseService;

import java.util.ArrayList;
import java.util.List;

public class NoteActivity extends AppCompatActivity {

    DatabaseService databaseService;
    private RecyclerView recyclerView;
    private NoteAdapter mAdapter;
    private List<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setToolbar(toolbar, "List Of Notes");

        databaseService = new DatabaseService(this);
        notes = databaseService.getNotes();

        recyclerView = (RecyclerView) findViewById(R.id.note_recycler_view);
        mAdapter = new NoteAdapter(notes, getApplicationContext(), databaseService);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

    private void setToolbar(Toolbar toolbar, String title) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);

    }
}

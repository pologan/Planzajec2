package com.example.planzajec.planzajec.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.planzajec.planzajec.Data.DatabaseHandler;
import com.example.planzajec.planzajec.Model.Classes;
import com.example.planzajec.planzajec.R;
import com.example.planzajec.planzajec.UI.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText nameItem;
    private EditText placeItem;
    private EditText teacherItem;
    private EditText startTime;
    private EditText endTime;
    private Spinner weekdaySpinner;
    private Spinner typeclassSpinner;
    private Spinner activeDaySpinner;
    private Button saveButton;
    private List<Classes> classesList;
    private List<Classes> listItems;
    private String selectedDay;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        activeDaySpinner = (Spinner) findViewById(R.id.active_day_spinner);

        selectedDay = activeDaySpinner.getSelectedItem().toString();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupDialog();
            }
        });

        activeDaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDay = activeDaySpinner.getSelectedItem().toString();
                update();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Nothing
            }
        });
        update();


    }
    private void saveClassToDB(View v) {

        Classes classes = new Classes();

        String newName = nameItem.getText().toString();
        String newPlace = placeItem.getText().toString();
        String newTeacher = teacherItem.getText().toString();
        String newStart = startTime.getText().toString();
        String newEnd = endTime.getText().toString();
        String newWeekday = weekdaySpinner.getSelectedItem().toString();
        String newClasstype = typeclassSpinner.getSelectedItem().toString();

        classes.setName(newName);
        classes.setPlace(newPlace);
        classes.setTeacher(newTeacher);
        classes.setStartTime(newStart);
        classes.setEndTime(newEnd);
        classes.setWeekDay(newWeekday);
        classes.setClassType(newClasstype);

        //Saving to DB
        db.addClass(classes);

        Snackbar.make(v, "Zapisano!", Snackbar.LENGTH_LONG).show();

        Log.d("Item added ID:", String.valueOf(db.getClassesCount()));

        dialog.dismiss();

        update();
    }

    private void createPopupDialog() {

        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);
        nameItem = (EditText) view.findViewById(R.id.nameItem);
        placeItem = (EditText) view.findViewById(R.id.placeItem);
        teacherItem = (EditText) view.findViewById(R.id.teacherItem);
        startTime = (EditText) view.findViewById(R.id.start_time);
        endTime = (EditText) view.findViewById(R.id.end_time);
        weekdaySpinner = (Spinner) view.findViewById(R.id.day_spinner);
        typeclassSpinner = (Spinner) view.findViewById(R.id.class_type_spinner);
        saveButton = (Button) view.findViewById(R.id.saveButton);

        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nameItem.getText().toString().isEmpty()
                        && !placeItem.getText().toString().isEmpty()
                        && !teacherItem.getText().toString().isEmpty()
                        && !startTime.getText().toString().isEmpty()
                        && !endTime.getText().toString().isEmpty()) {
                    saveClassToDB(v);
                }
            }
        });
    }

    private void update() {
        db = new DatabaseHandler(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        classesList = new ArrayList<>();
        listItems = new ArrayList<>();

        //Get items from database
        classesList = db.getAllClasses(activeDaySpinner.getSelectedItem().toString());

        for (Classes c : classesList) {
            Classes classes = new Classes();

            classes.setId(c.getId());
            classes.setName(c.getName());
            classes.setPlace(c.getPlace());
            classes.setTeacher(c.getTeacher());
            classes.setStartTime(c.getStartTime());
            classes.setEndTime(c.getEndTime());
            classes.setWeekDay(c.getWeekDay());
            classes.setClassType(c.getClassType());


            listItems.add(classes);
        }

        recyclerViewAdapter = new RecyclerViewAdapter(this,listItems);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    public void onClick(View view) {
        startActivity(new Intent(ListActivity.this,AboutActivity.class));
    }
}

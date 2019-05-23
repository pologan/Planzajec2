package com.example.planzajec.planzajec.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.planzajec.planzajec.Data.DatabaseHandler;
import com.example.planzajec.planzajec.Model.Classes;
import com.example.planzajec.planzajec.R;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText nameItem;
    private EditText placeItem;
    private EditText teacherItem;
    private EditText startTime;
    private EditText endTime;
    private Spinner weekdaySpinner;
    private Spinner typeclassSpinner;
    private Button saveButton;
    private DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);

        byPassActivity();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                createPopupDialog();
            }
        });
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

    private void createPopupDialog() {

        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup,null);
        nameItem = (EditText) view.findViewById(R.id.nameItem);
        placeItem = (EditText) view.findViewById(R.id.placeItem);
        teacherItem = (EditText) view.findViewById(R.id.teacherItem);
        startTime= (EditText) view.findViewById(R.id.start_time);
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
                if(!nameItem.getText().toString().isEmpty()
                        && !placeItem.getText().toString().isEmpty()
                        && !teacherItem.getText().toString().isEmpty()
                        && !startTime.getText().toString().isEmpty()
                        && !endTime.getText().toString().isEmpty()) {
                    saveClassToDB(v);
                }
            }
        });


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

        Snackbar.make(v,"Zapisano!", Snackbar.LENGTH_LONG).show();

        Log.d("Item added ID:", String.valueOf(db.getClassesCount()));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                //start new activity
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        },1200);
    }

    public void byPassActivity() {

        if(db.getClassesCount()>0) {
            startActivity(new Intent(MainActivity.this,ListActivity.class));
            finish();
        }
    }

}

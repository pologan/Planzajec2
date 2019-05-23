package com.example.planzajec.planzajec.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.planzajec.planzajec.Data.DatabaseHandler;
import com.example.planzajec.planzajec.Model.Classes;
import com.example.planzajec.planzajec.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Classes> classesItems;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    public RecyclerViewAdapter(Context context, List<Classes> classesItems) {
        this.context = context;
        this.classesItems = classesItems;
    }


    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row,parent,false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder viewHolder, int position) {

        Classes classes = classesItems.get(position);

        viewHolder.classesItemName.setText(classes.getName());
        viewHolder.place.setText(classes.getPlace());
        viewHolder.teacher.setText(classes.getTeacher());
        viewHolder.time.setText(classes.getStartTime() + "-" + classes.getEndTime());
        viewHolder.classType.setText(classes.getClassType());



    }

    @Override
    public int getItemCount() {
        return classesItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView classesItemName;
        public TextView place;
        public TextView teacher;
        public TextView time;
        public TextView classType;
        public Button editButton;
        public Button deleteButton;
        public int id;

        public ViewHolder(View view, Context ctx) {
            super(view);

            context = ctx;

            classesItemName = (TextView) view.findViewById(R.id.name);
            place = (TextView) view.findViewById(R.id.place);
            teacher = (TextView) view.findViewById(R.id.teacher);
            time = (TextView) view.findViewById(R.id.time);
            classType = (TextView) view.findViewById(R.id.classtype);

            editButton = (Button) view.findViewById(R.id.editButton);
            deleteButton = (Button) view.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //go to next screen
                }
            });

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.editButton:
                    int position = getAdapterPosition();
                    Classes classes = classesItems.get(position);

                    editItem(classes);
                    break;
                case R.id.deleteButton:
                    position = getAdapterPosition();
                    classes = classesItems.get(position);
                    deleteItem(classes.getId());
                    break;
            }

        }

        public void deleteItem(final int id) {

            alertDialogBuilder = new AlertDialog.Builder(context);

            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirmation_dialog,null);

            Button noButton = (Button) view.findViewById(R.id.noButton);
            Button yesButton = (Button) view.findViewById(R.id.yesButton);

            alertDialogBuilder.setView(view);
            dialog = alertDialogBuilder.create();
            dialog.show();

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler db = new DatabaseHandler(context);
                    db.deleteClasses(id);
                    classesItems.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    dialog.dismiss();
                }
            });
        }

        public void editItem(final Classes classes) {
            alertDialogBuilder = new AlertDialog.Builder(context);

            inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.popup,null);

            final EditText nameItem = (EditText) view.findViewById(R.id.nameItem);
            final EditText placeItem = (EditText) view.findViewById(R.id.placeItem);
            final EditText teacherItem = (EditText) view.findViewById(R.id.teacherItem);
            final EditText startTimeItem = (EditText) view.findViewById(R.id.start_time);
            final EditText endTimeItem = (EditText) view.findViewById(R.id.end_time);
            final Spinner weekdayItem = (Spinner) view.findViewById(R.id.day_spinner);
            final Spinner classTypeItem = (Spinner) view.findViewById(R.id.class_type_spinner);
            final TextView title = (TextView) view.findViewById(R.id.tile);

            nameItem.setText(classes.getName());
            placeItem.setText(classes.getPlace());
            teacherItem.setText(classes.getTeacher());
            startTimeItem.setText(classes.getStartTime());
            endTimeItem.setText(classes.getEndTime());
            weekdayItem.set
            title.setText("Edytuj moduł");
            Button saveButton = (Button) view.findViewById(R.id.saveButton);

            alertDialogBuilder.setView(view);
            dialog = alertDialogBuilder.create();
            dialog.show();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatabaseHandler db = new DatabaseHandler(context);

                    //Update item
                    classes.setName(nameItem.getText().toString());
                    classes.setPlace(placeItem.getText().toString());
                    classes.setTeacher(teacherItem.getText().toString());
                    classes.setStartTime(startTimeItem.getText().toString());
                    classes.setEndTime(endTimeItem.getText().toString());
                    classes.setWeekDay(weekdayItem.getSelectedItem().toString());
                    classes.setClassType(classTypeItem.getSelectedItem().toString());

                    if(!nameItem.getText().toString().isEmpty()
                            && !placeItem.getText().toString().isEmpty()
                            && !teacherItem.getText().toString().isEmpty()
                            && !startTimeItem.getText().toString().isEmpty()
                            && !endTimeItem.getText().toString().isEmpty()
                            && !weekdayItem.getSelectedItem().toString().isEmpty()
                            && !classTypeItem.getSelectedItem().toString().isEmpty()) {
                        db.updateClasses(classes);
                        notifyItemChanged(getAdapterPosition(),classes);
                    }else
                    {
                        Snackbar.make(view, "Za mało danych", Snackbar.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                }
            });
        }
    }
}

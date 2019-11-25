package sk.kabum.kabumcare;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;
import sk.kabum.kabumcare.Enums.TaskType;
import sk.kabum.kabumcare.Enums.WorkoutType;
import sk.kabum.kabumcare.Objects.DatePickerFragment;
import sk.kabum.kabumcare.Objects.Task;
import sk.kabum.kabumcare.Objects.TimePickerFragment;
import sk.kabum.kabumcare.dbs.DatabaseHelper;

public class WorkoutCreator extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private WorkoutType type;
    private TimePickerFragment timePickerFragment = null;
    private DatePickerFragment datePickerFragment = null;
    private Spinner spinner;
    private String[] paths;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private DatePickerFragment dateFragment;
    private TimePickerFragment timeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_creator);
        ButterKnife.bind(this);
        this.paths = new String[WorkoutType.values().length];
        for (int i = 0; i < WorkoutType.values().length; i++) {
            this.paths[i] = WorkoutType.values()[i].toString();
        }



        spinner = (Spinner) findViewById(R.id.pick_workout_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.type = WorkoutType.values()[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        this.type = WorkoutType.values()[0];

    }
    @OnClick(R.id.pick_date)
    public void onClickDate() {
        this.dateFragment = new DatePickerFragment();
        this.dateFragment.show(getSupportFragmentManager(), "datePicker");
        this.year = this.dateFragment.getYear();
        this.month = this.dateFragment.getMonth();
        this.day = this.dateFragment.getDay();

    }
    @OnClick(R.id.pick_time)
    public void onClickTime(){
        this.timeFragment = new TimePickerFragment();
        timeFragment.show(getSupportFragmentManager(), "timePicker");
        this.minute = this.timeFragment.getMinute();
        this.hour = this.timeFragment.getHour();


    }
    @OnClick(R.id.save_workout)
    public void onClickSave(){
        DatabaseHelper helper = new DatabaseHelper(this);
        RuntimeExceptionDao<Task,Integer> dbsDao = helper.getTaskRuntimeDao();
        // connect user
        Calendar calendar = Calendar.getInstance();
        calendar.set(this.year, this.month, this.day, this.hour, this.minute);
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date newDate = calendar.getTime();
        String dateForDbs = date.format(newDate);

        EditText inputedName=  findViewById(R.id.name);
        String workoutName = inputedName.getText().toString();

        Task workout = new Task(5,dateForDbs,TaskType.WORKOUT,workoutName,this.type);

        dbsDao.create(workout);

        Log.i("WorkoutCreator","Posted");
        Log.i("WorkoutCreator",workout.toString());
        Intent goToMain = new Intent();
        goToMain.setClass(this, MainActivity.class);
        startActivity(goToMain);
    }


    public  static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {


        private int hour=0;
        private int minute=0;


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            this.hour = hourOfDay;
            this.minute = minute;
            Toast picked = Toast.makeText(getContext(),"Time selected",Toast.LENGTH_SHORT);
            picked.show();
        }

        public int getHour() {
            return hour;
        }

        public int getMinute() {
            return minute;
        }


    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        private int year;
        private int month;
        private int day;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            this.year = year;
            this.month = month;
            this.day = day;
            Toast picked = Toast.makeText(getContext(), "Date selected", Toast.LENGTH_SHORT);
            picked.show();


        }

        public int getYear() {
            return year;
        }

        public int getMonth() {
            return month;
        }

        public int getDay() {
            return day;
        }
    }

}
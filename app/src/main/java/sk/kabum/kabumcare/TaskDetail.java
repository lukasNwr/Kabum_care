package sk.kabum.kabumcare;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;
import java.util.TreeSet;

import butterknife.ButterKnife;
import butterknife.OnClick;
import sk.kabum.kabumcare.Enums.WorkoutType;
import sk.kabum.kabumcare.Objects.DatePickerFragment;
import sk.kabum.kabumcare.Objects.Task;
import sk.kabum.kabumcare.Objects.TimePickerFragment;
import sk.kabum.kabumcare.Objects.User;
import sk.kabum.kabumcare.dbs.DatabaseHelper;

public class TaskDetail extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private WorkoutType type;
    private TimePickerFragment timePickerFragment= null;
    private DatePickerFragment datePickerFragment= null;
    private Spinner spinner;
    private int duration =0;
    private String[] paths;
    private User user;
    private RuntimeExceptionDao<User,Integer> usrDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        ButterKnife.bind(this);

        this.paths = new String[WorkoutType.values().length];
        for (int i = 0; i < WorkoutType.values().length; i++) {
            this.paths[i] = WorkoutType.values()[i].toString();
        }

        TextView header = findViewById(R.id.workout_name);
        Intent intent = getIntent();
        header.setText(intent.getStringExtra("name"));


        DatabaseHelper dbs = new DatabaseHelper(getBaseContext());
        RuntimeExceptionDao<Task,Integer> taskDAO = dbs.getTaskRuntimeDao();
        RuntimeExceptionDao<User,Integer> usrDao = dbs.getUserRuntimeDao();
        Task taskyTask = taskDAO.queryForId(intent.getIntExtra("id",0));



        TextView date = findViewById(R.id.due_date_value);
        date.setText(taskyTask.getDueDate());
        Log.i("xd",taskyTask.getDueDate());
        Log.i("xd",taskyTask.getDueDate());
        Log.i("xd",taskyTask.getDueDate());Log.i("xd",taskyTask.getDueDate());Log.i("xd",taskyTask.getDueDate());Log.i("xd",taskyTask.getDueDate());Log.i("xd",taskyTask.getDueDate());Log.i("xd",taskyTask.getDueDate());



        this.usrDao = usrDao;
        List<User> userList = usrDao.queryForAll();
        this.user = userList.get(0);


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.type = WorkoutType.values()[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        this.type = WorkoutType.values()[0];

    }


    @OnClick(R.id.finish_workout)
    public void onFinishClicked(View view) {
        EditText inputedDuration =  findViewById(R.id.duration_value);
        this.duration = Integer.parseInt(inputedDuration.getText().toString());
        if (this.duration <1){
            Toast toast = Toast.makeText(this, "Duration can't be 0", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Intent intent = getIntent();
            int workoutID = intent.getIntExtra("id",-1);
            if (workoutID > -1) {
                DatabaseHelper helper = new DatabaseHelper(this);
                RuntimeExceptionDao<Task,Integer> dbsDao = helper.getTaskRuntimeDao();
                dbsDao.deleteById(workoutID);
                Log.i("TaskDetail","Deleted");
                Intent goToMain = new Intent();
                intent.setClass(this, MainActivity.class);
                startActivity(intent);

                this.user.setGold(this.user.getGold()+this.calcGold());
                this.user.setXp(this.user.getXp()+this.calcXP());
                this.usrDao.update(this.user);
            } else {
                Log.i("TaskDetail","Workout ID missing");}
        }

    }


    private int calcXP() {
        return (int) (duration*0.2);
    }
    private int calcGold() {
        return (int) (duration*2);
    }

}

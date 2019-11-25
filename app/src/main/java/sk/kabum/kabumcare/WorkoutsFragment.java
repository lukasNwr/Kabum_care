package sk.kabum.kabumcare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import sk.kabum.kabumcare.Enums.TaskType;
import sk.kabum.kabumcare.Objects.Task;
import sk.kabum.kabumcare.dbs.DatabaseHelper;


public class WorkoutsFragment extends ListFragment {

    private List<Task> tasks;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DatabaseHelper helper = new DatabaseHelper(getContext());
        RuntimeExceptionDao<Task, Integer> simpleDaoT = helper.getTaskRuntimeDao();
        this.tasks = simpleDaoT.queryForAll();
        String names[] = new String[tasks.size()];
        Log.i(" xd","list size %d"+tasks.size());
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i) != null) {
                names[i] = tasks.get(i).getName();

            Log.i("xd","xd" + tasks.get(i).toString()); } else {
                Log.i("lulw","xd");
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, names);
        setListAdapter(adapter);

    }


    public void onListItemClick(ListView l, View v, int position, long id) {
        if (this.tasks != null) {
            long workoutID = this.tasks.get(position).getId();

            DatabaseHelper helper = new DatabaseHelper(getContext());
            RuntimeExceptionDao<Task, Integer> taskDao = helper.getTaskRuntimeDao();
            Task myTask = taskDao.queryForId((int) workoutID);
            Log.i("xd","task + " + myTask.toString());
            String myTaskDueDate = myTask.getDueDate();
            TaskType myTaskType = myTask.getType();
            String myTaskName = myTask.getName();


            String dateString = myTaskDueDate;


            Intent intent = new Intent();
            Bundle extras = intent.getExtras();
            intent.putExtra("name", myTaskName);
            intent.putExtra("date", dateString);
            intent.putExtra("id", (int) workoutID);
            intent.setClass(getContext(), TaskDetail.class);
            startActivity(intent);
        }
    }
}


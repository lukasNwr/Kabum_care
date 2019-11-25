package sk.kabum.kabumcare;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import sk.kabum.kabumcare.Objects.Task;

public class WorkoutAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] resources;

    public WorkoutAdapter(Context context, String[] resource){
        super(context,R.layout.workout_lauout,resource);
        this.context = context;
        this.resources = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.workout_lauout, parent ,false);

        String workoutId = getItem(position);

        TextView workoutText = customView.findViewById(R.id.workout_title);
        workoutText.setText(this.resources[position]);

        Button buttonDone = customView.findViewById(R.id.good_workout);
        buttonDone.setVisibility(View.VISIBLE);
        Button buttonFail = customView.findViewById(R.id.fail_workout);
        buttonFail.setVisibility(View.VISIBLE);

        return customView;
    }


}

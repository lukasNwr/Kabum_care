package sk.kabum.kabumcare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FoodActivity extends AppCompatActivity {

    private int calories = 0;
    private int maxCalories = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        maxCalories = sharedPreferences.getInt("cal",maxCalories);
        TextView calIntake = findViewById(R.id.maxCal);
        calIntake.setText(Integer.toString(maxCalories)+"ml");

        final ProgressBar bar = findViewById(R.id.progressCal);
        bar.setMax(maxCalories);
        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(FoodActivity.this);
                alert.setTitle("Set your calories goal in cal.");
                final EditText input = new EditText(FoodActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(input);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        maxCalories = Integer.parseInt(input.getText().toString());
                        TextView maxStepsTV = findViewById(R.id.maxCal);
                        maxStepsTV.setText(Integer.toString(maxCalories));
                        ProgressBar bar1 = findViewById(R.id.progressCal);
                        bar1.setMax(maxCalories);
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("cal",maxCalories);
                        editor.apply();
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                alert.show();
            }
        });

        Button button = findViewById(R.id.addCal);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(FoodActivity.this);
                alert.setTitle("Add calories in cal.");
                final EditText input = new EditText(FoodActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(input);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        calories =  calories + Integer.parseInt(input.getText().toString());
                        ProgressBar bar1 = findViewById(R.id.progressCal);
                        bar1.setProgress(calories);
                        TextView view1 = findViewById(R.id.calories);
                        view1.setText(Integer.toString(calories)+"cal");

                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                alert.show();
            }
        });
    }
}

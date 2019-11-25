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


public class WatterCounterActivity extends AppCompatActivity {

    private int cup = 100;
    private int watter = 0;
    private int maxWatterIntake = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_counter);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        cup = sharedPreferences.getInt("cup",cup);
        final TextView watterIntake = findViewById(R.id.cupSize);
        watterIntake.setText(Integer.toString(cup)+"ml");

        Button buttonPlus = findViewById(R.id.plus);
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cup = cup + 50;
                TextView watterIntake = findViewById(R.id.cupSize);
                watterIntake.setText(Integer.toString(cup)+"ml");
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("cup",cup);
                editor.apply();
            }
        });

        Button buttonMinus = findViewById(R.id.minus);
        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cup = cup - 50;
                TextView watterIntake = findViewById(R.id.cupSize);
                watterIntake.setText(Integer.toString(cup)+"ml");
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("cup",cup);
                editor.apply();
            }
        });

        Button drink = findViewById(R.id.add);
        drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                watter = watter + cup;
                if(watter <= 0){
                    watter = 0;
                }
                TextView intake = findViewById(R.id.intake);
                intake.setText(Integer.toString(watter)+"ml");
                ProgressBar bar = findViewById(R.id.progressWatter);
                bar.setProgress(watter);
            }
        });

        maxWatterIntake = sharedPreferences.getInt("watterIntake",maxWatterIntake);
        ProgressBar bar = findViewById(R.id.progressWatter);
        bar.setMax(maxWatterIntake);
        TextView maxWatter = findViewById(R.id.maxWatter);
        maxWatter.setText(Integer.toString(maxWatterIntake));

        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(WatterCounterActivity.this);
                alert.setTitle("Set your goal in ml.");
                final EditText input = new EditText(WatterCounterActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(input);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        maxWatterIntake = Integer.parseInt(input.getText().toString());
                        TextView maxStepsTV = findViewById(R.id.maxWatter);
                        maxStepsTV.setText(Integer.toString(maxWatterIntake));
                        ProgressBar bar1 = findViewById(R.id.progressWatter);
                        bar1.setMax(maxWatterIntake);
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("watterIntake",maxWatterIntake);
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
    }
}

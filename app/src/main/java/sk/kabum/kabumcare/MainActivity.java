package sk.kabum.kabumcare;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import org.w3c.dom.Text;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import sk.kabum.kabumcare.Enums.TaskType;
import sk.kabum.kabumcare.Enums.WorkoutType;
import sk.kabum.kabumcare.Objects.Armour;
import sk.kabum.kabumcare.Objects.Task;
import sk.kabum.kabumcare.Objects.User;
import sk.kabum.kabumcare.Objects.Weapon;
import sk.kabum.kabumcare.dbs.DatabaseHelper;

public class MainActivity extends FragmentActivity {

    AnimationDrawable animationDrawable;


    @Override
    protected void onResume() {
        super.onResume();

        DatabaseHelper dbs = new DatabaseHelper(this);
        RuntimeExceptionDao<User,Integer> usrDao = dbs.getUserRuntimeDao();
        List<User> usrList = usrDao.queryForAll();
        User user = usrList.get(0);

        TextView lvl = findViewById(R.id.levelVal);

        TextView gold = findViewById(R.id.goldVal);


        int lvlVal = user.getXp()/60;
        lvl.setText(Integer.toString(lvlVal));

        gold.setText(Integer.toString(user.getGold()));



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //doSampleDatabaseStuff();

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ButterKnife.bind(this);
        ImageView imageView = findViewById(R.id.character);
        imageView.setBackgroundResource(R.drawable.spriteanim1);
        animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new WorkoutsFragment()).commit();

        RuntimeExceptionDao<User,Integer> dao = databaseHelper.getUserRuntimeDao();
        List<User> list = dao.queryForAll();
        User user;
        if (list.size()<1) {
            User newUser = new User(10,"Tester");
            dao.create(newUser);
            list = dao.queryForAll();
            user = list.get(0);

        } else {
            user = list.get(0);
        }

        ProgressBar progressBarHp = findViewById(R.id.progressBarHp);
        progressBarHp.setProgress(user.getHp());

        int expVal = user.getXp()%60;


        ProgressBar progressBarExp = findViewById(R.id.progressBarExp);
        progressBarExp.setProgress(expVal);

        TextView lvl = findViewById(R.id.levelVal);
        lvl.setText(Integer.toString(user.getXp()/60));

        TextView gold = findViewById(R.id.goldVal);
        gold.setText(Integer.toString(user.getGold()));

        Button buttonWorkout = findViewById(R.id.workouts);
        buttonWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new WorkoutsFragment()).commit();
                ImageView imageView = findViewById(R.id.character);
                imageView.setBackgroundResource(R.drawable.spriteanim1);
                animationDrawable = (AnimationDrawable) imageView.getBackground();
                animationDrawable.start();
            }
        });



        Button buttonDiet = findViewById(R.id.diet);
        buttonDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new FoodFragment()).commit();
                ImageView imageView = findViewById(R.id.character);
                imageView.setBackgroundResource(R.drawable.spriteanim2);
                animationDrawable = (AnimationDrawable) imageView.getBackground();
                animationDrawable.start();
            }
        });

        Button buttonWater = findViewById(R.id.water);
        buttonWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new WaterFragment()).commit();
                ImageView imageView = findViewById(R.id.character);
                imageView.setBackgroundResource(R.drawable.spriteanim3);
                animationDrawable = (AnimationDrawable) imageView.getBackground();
                animationDrawable.start();
            }
        });

        Button buttonBoss = findViewById(R.id.boss);
        buttonBoss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), BossFight.class);
                startActivity(intent);
            }
        });

        //DatabaseHelper helper = new DatabaseHelper(this);
        //doSampleDatabaseStuff();

    }

    @OnClick(R.id.new_workout)
    public void onNewWorkoutClick(){
        Intent intent = new Intent(this, WorkoutCreator.class);
        startActivity(intent);
    }

    private void doSampleDatabaseStuff() {
        // get our dao
        DatabaseHelper helper = new DatabaseHelper(this);

        RuntimeExceptionDao<User, Integer> simpleDao = helper.getUserRuntimeDao();
        RuntimeExceptionDao<Task, Integer> simpleDaoT = helper.getTaskRuntimeDao();
        RuntimeExceptionDao<Weapon, Integer> simpleDaoW = helper.getWeaponRuntimeDao();
        RuntimeExceptionDao<Armour, Integer> simpleDaoA = helper.getArmourRuntimeDao();
        // query for all of the data objects in the database


        List<User> list = simpleDao.queryForAll();
        List<Task> listt = simpleDaoT.queryForAll();
        List<Weapon> listw = simpleDaoW.queryForAll();
        List<Armour>listA = simpleDaoA.queryForAll();


    }
}

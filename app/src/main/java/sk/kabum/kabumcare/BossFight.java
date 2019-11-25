package sk.kabum.kabumcare;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sk.kabum.kabumcare.Enums.TaskType;
import sk.kabum.kabumcare.Enums.WorkoutType;
import sk.kabum.kabumcare.Objects.Armour;
import sk.kabum.kabumcare.Objects.Boss;
import sk.kabum.kabumcare.Objects.Task;
import sk.kabum.kabumcare.Objects.User;
import sk.kabum.kabumcare.Objects.Weapon;
import sk.kabum.kabumcare.dbs.DatabaseHelper;

public class BossFight extends AppCompatActivity {

    AnimationDrawable bossAnimation;
    AnimationDrawable playerAnimation;
    ImageView bossImage;
    ImageView playerImage;

    private ProgressBar playerHpBar;
    private ProgressBar bossHpBar;
    private User player;
    private List<Boss> bossList = new ArrayList<Boss>();
    private int playerDamage;
    private int position;

    private int bossHealth;
    private int playerHealth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_fight);
        position = 0;

        Button nextButton = findViewById(R.id.next);
        Button previousButton = findViewById(R.id.previous);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position < bossList.size() - 1) {
                    position++;

                    if(position == 0) {
                        bossImage = findViewById(R.id.bossImage);
                        bossImage.setBackgroundResource(R.drawable.blob_animation);
                        bossAnimation = (AnimationDrawable) bossImage.getBackground();
                        bossAnimation.start();
                    } else if(position == 1) {
                        bossImage = findViewById(R.id.bossImage);
                        bossImage.setBackgroundResource(R.drawable.burger_animation);
                        bossAnimation = (AnimationDrawable) bossImage.getBackground();
                        bossAnimation.start();
                    }
                }

            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position > 0) {
                    position--;
                    if(position == 0) {
                        bossImage = findViewById(R.id.bossImage);
                        bossImage.setBackgroundResource(R.drawable.blob_animation);
                        bossAnimation = (AnimationDrawable) bossImage.getBackground();
                        bossAnimation.start();
                        if(bossList.get(position).getHealth() == 0) {
                            bossImage.setBackgroundResource(R.drawable.blob_7);
                        }
                    } else if(position == 1) {
                        bossImage = findViewById(R.id.bossImage);
                        bossImage.setBackgroundResource(R.drawable.burger_animation);
                        bossAnimation = (AnimationDrawable) bossImage.getBackground();
                        bossAnimation.start();
                        if(bossList.get(position).getHealth() == 0) {
                            bossImage.setBackgroundResource(R.drawable.blob_7);
                        }
                    }
                }
            }
        });

        final Random rand = new Random();
        final Handler handler = new Handler();

        DatabaseHelper helper = new DatabaseHelper(this);
        //doSampleDatabaseStuff();

        final RuntimeExceptionDao<User, Integer> simpleDao = helper.getUserRuntimeDao();
        List<User> list = simpleDao.queryForAll();
        player = list.get(0);

        bossList.add(new Boss(100, 5, 50));
        bossList.add(new Boss(200, 10, 150));

        if (position == 0) {
            bossImage = findViewById(R.id.bossImage);
            bossImage.setBackgroundResource(R.drawable.blob_animation);
            bossAnimation = (AnimationDrawable) bossImage.getBackground();
            bossAnimation.start();
        } else if (position == 1) {
            bossImage = findViewById(R.id.bossImage);
            bossImage.setBackgroundResource(R.drawable.burger_animation);
            bossAnimation = (AnimationDrawable) bossImage.getBackground();
            bossAnimation.start();
        }


        playerDamage = player.getXp() / 60 + 1;


        playerHpBar = (ProgressBar) findViewById(R.id.playerrHpBar);
        playerHpBar.setMax(100 + player.getXp());
        playerHpBar.setProgress(playerHpBar.getMax());
        playerHealth = playerHpBar.getProgress();

        bossHpBar = (ProgressBar) findViewById(R.id.bossHpBar);
        bossHealth = bossHpBar.getProgress();
        bossHpBar.setProgress(bossHpBar.getMax());


        playerImage = findViewById(R.id.playerImage);
        playerImage.setBackgroundResource(R.drawable.player_animation);
        playerAnimation = (AnimationDrawable) playerImage.getBackground();
        playerAnimation.start();

        bossImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bossList.get(position).getHealth() > 0) {
                    bossList.get(position).setHealth(bossList.get(position).getHealth() - playerDamage);
                    bossHpBar.setProgress(bossList.get(position).getHealth());
                    bossAnimation.stop();
                    Animation animShake = AnimationUtils.loadAnimation(BossFight.this, R.anim.shake_anim);
                    view.startAnimation(animShake);
                    bossAnimation.start();
                } else if (bossList.get(position).getHealth() <= 0) {
                    //bossList.get(position).setDamage(0);
                    if (position == 0) {
                        bossImage = findViewById(R.id.bossImage);
                        bossImage.setBackgroundResource(R.drawable.blob_6);
                    } else if (position == 1) {
                        bossImage = findViewById(R.id.bossImage);
                        bossImage.setBackgroundResource(R.drawable.burger_dead);
                    }

                    bossAnimation.stop();
                    bossImage.setBackgroundResource(R.drawable.blob_6);
                    player.setXp(player.getXp() + 50);
                    simpleDao.update(player);
                }
            }
        });


        Runnable r = new Runnable() {
            public void run() {
                if (playerHealth > 0) {
                    playerHealth -= bossList.get(position).getDamage();
                    playerHpBar.setProgress(playerHealth);
                } else if(playerHealth <= 0) {
                    player.setHp(player.getHp() - 10);
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }
                handler.postDelayed(this, rand.nextInt(2000) + 1000);
            }
        };
        handler.postDelayed(r, rand.nextInt(2000) + 1000);

        if(playerHealth <= 0) {
            handler.removeCallbacks(r);
        }
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
        List<Armour> listA = simpleDaoA.queryForAll();

//        for (int x =0; x <3;x++) {
//            Log.i("xd",(list.get(x).toString()) );
//            Log.i("xd",(listt.get(x).toString()) );
//            //Log.i("xd",(listw.get(x).toString()) );
//            //Log.i("xd",(listA.get(x).toString()) );
//        }

    }
}

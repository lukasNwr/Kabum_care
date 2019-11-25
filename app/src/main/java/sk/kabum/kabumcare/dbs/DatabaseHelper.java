package sk.kabum.kabumcare.dbs;

import java.net.ConnectException;
import java.sql.Date;
import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import sk.kabum.kabumcare.Enums.TaskType;
import sk.kabum.kabumcare.Objects.Armour;
import sk.kabum.kabumcare.Objects.Task;
import sk.kabum.kabumcare.Objects.User;
import sk.kabum.kabumcare.Objects.Weapon;
import sk.kabum.kabumcare.R;


/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "kabumcare.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    // the DAO object we use to access the SimpleData table
    private Dao<User, Integer> userDao = null;
    private Dao<Task, Integer> taskDao = null;
    private Dao<Weapon, Integer> weaponDao = null;
    private Dao<Armour, Integer> armourDao = null;

    private RuntimeExceptionDao<User, Integer> userRuntimeDao = null;
    private RuntimeExceptionDao<Task, Integer> taskRuntimeDao = null;
    private RuntimeExceptionDao<Weapon, Integer> weaponRuntimeDao = null;
    private RuntimeExceptionDao<Armour, Integer> armourRuntimeDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, Task.class);
            TableUtils.createTable(connectionSource, Weapon.class);
            TableUtils.createTable(connectionSource, Armour.class);
            //TableUtils.dropTable(connectionSource, Task.class,true);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }

        // here we try inserting data in the on-create as a test
        /*RuntimeExceptionDao<User, Integer> daoU = getUserRuntimeDao();
        RuntimeExceptionDao<Task, Integer> daoT = getTaskRuntimeDao();
        RuntimeExceptionDao<Weapon, Integer> daoW = getWeaponRuntimeDao();
        RuntimeExceptionDao<Armour, Integer> daoA = getArmourRuntimeDao();

        User test = new User(10);
        Task testt = new Task(5, new Date(System.currentTimeMillis()), TaskType.WORKOUT);
        Weapon testW = new Weapon();
        Armour testA = new Armour();
        daoU.create(test);
        daoT.create(testt);
        daoW.create(testW);
        daoA.create(testA);*/

    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, Task.class, true);
            TableUtils.dropTable(connectionSource, Weapon.class, true);
            TableUtils.dropTable(connectionSource, Armour.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }



    /**
     * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
     * value.
     */
    public Dao<User, Integer> getUserDao() throws SQLException {
        if (userDao == null) {
           userDao = getDao(User.class);
        }
        return userDao;
 }
    public Dao<Task, Integer> getTaskDao() throws SQLException {
        if (taskDao == null) {
            taskDao = getDao(Task.class);
        }
        return taskDao;
    }
    public Dao<Weapon, Integer> getWeaponDao() throws SQLException {
        if (weaponDao == null) {
            weaponDao = getDao(Weapon.class);
        }
        return weaponDao;
    }
    public Dao<Armour, Integer> getArmourDao() throws SQLException {
        if (armourDao == null) {
            armourDao = getDao(Armour.class);
        }
        return armourDao;
    }
    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our SimpleData class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
   public RuntimeExceptionDao<User, Integer> getUserRuntimeDao() {
        if (userRuntimeDao == null) {
            userRuntimeDao = getRuntimeExceptionDao(User.class);
        }
        return userRuntimeDao;
    }
    public RuntimeExceptionDao<Task, Integer> getTaskRuntimeDao() {
        if (taskRuntimeDao == null) {
            taskRuntimeDao = getRuntimeExceptionDao(Task.class);
        }
        return taskRuntimeDao;
    }
    public RuntimeExceptionDao<Weapon, Integer> getWeaponRuntimeDao() {
        if (weaponRuntimeDao == null) {
            weaponRuntimeDao = getRuntimeExceptionDao(Weapon.class);
        }
        return weaponRuntimeDao;
    }
    public RuntimeExceptionDao<Armour, Integer> getArmourRuntimeDao() {
        if (armourRuntimeDao == null) {
            armourRuntimeDao = getRuntimeExceptionDao(Armour.class);
        }
        return armourRuntimeDao;
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();

        userDao = null;
        taskDao = null;
        weaponDao = null;
        armourDao =null;

        userRuntimeDao = null;
        taskRuntimeDao = null;
        weaponRuntimeDao = null;
        armourRuntimeDao = null;

    }
}
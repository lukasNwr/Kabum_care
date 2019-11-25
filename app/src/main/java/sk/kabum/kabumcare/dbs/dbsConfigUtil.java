package sk.kabum.kabumcare.dbs;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import sk.kabum.kabumcare.Objects.Armour;
import sk.kabum.kabumcare.Objects.Task;
import sk.kabum.kabumcare.Objects.User;
import sk.kabum.kabumcare.Objects.Weapon;

public class dbsConfigUtil extends OrmLiteConfigUtil {

    private static final Class<?>[] classes = new Class[] {User.class,Weapon.class,Task.class,Armour.class};


    public static void main(String[] args) throws SQLException, IOException {
        String currDirectory = "user.dir";
        String configPath = "/app/src/main/res/raw/ormlite_config.txt";
        String projectRoot = System.getProperty(currDirectory);
        String fullConfigPath = projectRoot + configPath;
        writeConfigFile(new File(fullConfigPath), classes);
    }
}
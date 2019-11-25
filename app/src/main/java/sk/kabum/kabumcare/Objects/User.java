package sk.kabum.kabumcare.Objects;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
@DatabaseTable
public class User {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private long GoogleID;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private ArrayList<Integer> achievementsID;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private ArrayList<Equipment> inventory;
    @DatabaseField
    private int xp;
    @DatabaseField
    private int hp;
    @DatabaseField
    private int gold;
    @DatabaseField
    private String name;

    public User(long GoogleID, String name) {
        this.GoogleID = GoogleID;
        this.inventory = new ArrayList<>();
        this.achievementsID = new ArrayList<>();
        this.xp = 0;
        this.hp =100;
        this.gold = 0;
        this.name = name;
    }

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGoogleID() {
        return GoogleID;
    }

    public void setGoogleID(long googleID) {
        GoogleID = googleID;
    }

    public ArrayList<Integer> getAchievementsID() {
        return achievementsID;

    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void setAchievementsID(ArrayList<Integer> achievementsID) {
        this.achievementsID = achievementsID;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", GoogleID=" + GoogleID +
                ", achievementsID=" + achievementsID +
                ", inventory=" + inventory +
                ", xp=" + xp +
                ", hp=" + hp +
                ", gold=" + gold +
                '}';
    }



    public ArrayList<Equipment> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Equipment> inventory) {
        this.inventory = inventory;
    }
}

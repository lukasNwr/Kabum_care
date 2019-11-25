package sk.kabum.kabumcare.Objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Weapon extends Equipment {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private int damage;
    @DatabaseField
    private String name;

    public Weapon(String name, int damage) {
        super();
        this.name = name;
        this.damage = damage;
    }

    public Weapon() {

    }

    @Override
    public String toString() {
        return "Weapon{" +
                "id=" + id +
                ", damage=" + damage +
                ", name='" + name + '\'' +
                '}';
    }

    public int getDamage() {
        return damage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}

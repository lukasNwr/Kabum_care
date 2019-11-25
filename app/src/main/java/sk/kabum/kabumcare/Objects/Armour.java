package sk.kabum.kabumcare.Objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Armour extends Equipment {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private int armour;
    @DatabaseField
    private String name;

    public Armour(String name, int armour) {
        super();
        this.name = name;
        this.armour = armour;

    }
    public Armour() {

    }

    @Override
    public String toString() {
        return "Armour{" +
                "id=" + id +
                ", armour=" + armour +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArmour() {
        return armour;
    }

    public void setArmour(int armour) {
        this.armour = armour;
    }
}

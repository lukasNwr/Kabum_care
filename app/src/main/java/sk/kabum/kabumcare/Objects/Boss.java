package sk.kabum.kabumcare.Objects;

public class Boss {
    private int health;
    private int damage;
    private int xpReward;

    public Boss(int health, int damage, int xpReward) {
        this.health = health;
        this.damage = damage;
        this.xpReward = xpReward;
    }

    public int getDamage() {
        return damage;
    }

    public int getHealth() {
        return health;
    }

    public int getXpReward() {
        return xpReward;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setXpReward(int xpReward) {
        this.xpReward = xpReward;
    }
}

package fr.tarkod.kitpvp.level;

public class ProfileLevel {

    private int level;
    private double xp;

    private double xpForLevelUp;

    ProfileLevel(int level, double xp, double xpForLevelUp) {
        this.level = level;
        this.xp = xp;
        this.xpForLevelUp = xpForLevelUp;
    }

    public int getLevel() {
        return level;
    }

    public double getXp() {
        return xp;
    }

    public double howXpToLevelUp() {
        return xpForLevelUp;
    }
}

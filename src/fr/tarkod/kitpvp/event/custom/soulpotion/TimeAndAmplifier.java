package fr.tarkod.kitpvp.event.custom.soulpotion;

public class TimeAndAmplifier {

    private final int amplifier;
    private int tickRemaining;

    public TimeAndAmplifier(int amplifier, int tick) {
        this.amplifier = amplifier;
        this.tickRemaining = tick;
    }

    public int getAmplifier() {
        return amplifier;
    }

    public int getTimeInTick() {
        return tickRemaining;
    }

    public int getTimeInSecond() {
        return tickRemaining/20;
    }

    public void minusOneSecond() {
        tickRemaining -= 20;
    }
}

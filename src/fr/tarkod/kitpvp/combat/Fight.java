package fr.tarkod.kitpvp.combat;

public class Fight {

    private int timeLeft;

    public Fight(){
        this.timeLeft = 0;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public boolean isFighting(){
        if(timeLeft <= 0){
            return false;
        }
        return true;
    }
}

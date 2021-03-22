package ru.company.onetwo33.homework5;

import java.util.ArrayList;
import java.util.Arrays;

public class Race {
    private boolean winner = false;
    private ArrayList<Stage> stages;

    public ArrayList<Stage> getStages() { return stages; }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public Race(Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
    }
}

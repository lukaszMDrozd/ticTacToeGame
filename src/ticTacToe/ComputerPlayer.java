package ticTacToe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComputerPlayer implements Player{

    private String computerName;

    ComputerPlayer(String computerName) {
        this.computerName = computerName;
    }

    @Override
    public List<Integer> makeMove(List<List<Integer>> availableMoves, int spanNumber) {
        System.out.println("Lista na wejściu" + availableMoves);
        System.out.println("Ruch komputera: " + getComputerName());

        List<Integer> result = new ArrayList<>();

        do {
            int xCoordinate = new Random().nextInt(spanNumber + 1);
            int yCoordinate = new Random().nextInt(spanNumber + 1);

            result.add(xCoordinate);
            result.add(yCoordinate);
            System.out.println("wynik Komputera: " + result);
        }
        while(!availableMoves.contains(result) && result.size() == 2);

        return result;
    }

    private String getComputerName() {
        return computerName;
    }
}

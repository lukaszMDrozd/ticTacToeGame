package ticTacToe;

import java.util.List;

public interface Player {

    List<Integer> makeMove(List<List<Integer>> availableMoves, int spanNumber);
}

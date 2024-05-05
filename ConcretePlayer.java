package VikingsChess;

import java.util.*;

public class ConcretePlayer implements Player {

    //False= Defender True= Attacker
    private boolean attOrDef;
    private int wins;

    // this variable hold the alive\killed pieces.
    private ArrayList<ConcretePiece> pieces;


    public ConcretePlayer(boolean attOrDef) {
        this.attOrDef = attOrDef;
        this.wins = 0;
        pieces = new ArrayList<>();
    }

    @Override
    public boolean isPlayerOne() {
        return attOrDef;
    }

    @Override
    public int getWins() {
        return this.wins;
    }

    public void addWin() {
        this.wins += 1;
    }

    public void addPiece(ConcretePiece piece) {
        pieces.add(piece);
    }


    public ArrayList<ConcretePiece> getPieces() {
        return pieces;
    }


}
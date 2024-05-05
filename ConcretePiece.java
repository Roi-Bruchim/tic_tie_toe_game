package VikingsChess;

import java.util.ArrayList;

public class ConcretePiece implements Piece {

    private Player player_piece;


    public ConcretePiece(Player player_piece)
    {
        this.player_piece=player_piece;
    }



    @Override
    public Player getOwner() {
        return player_piece;
    }

    @Override
    public String getType() {
        {
            if (getOwner().isPlayerOne())
            {
                return "O";
            }
            else
            {
                return "X";
            }
        }

    }
}

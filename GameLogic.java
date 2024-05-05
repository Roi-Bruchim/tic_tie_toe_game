package VikingsChess;

import java.util.ArrayList;
import java.util.Stack;

public class GameLogic implements PlayableLogic {
    //board size is final in order for the board size to be unchangeable.
    private final int board_size = 3;

    //game board will be treated as a matrix of a 2-dimensional array
    private final Piece[][] GameBoard;

    //the matrix that represent the moves will be in stack in order to make an undo button
    private Stack<Piece[][]> gameMoves;
    private ConcretePlayer firstPlayer;
    private ConcretePlayer secondPlayer;
    //will help us decide which player got this turn
    private int numOfMoves;

    private int current_winner;




    //constructor
    public GameLogic() {
        this.firstPlayer = new ConcretePlayer(true);
        this.secondPlayer = new ConcretePlayer(false);
        this.GameBoard = new Piece[board_size][board_size];
        this.gameMoves = new Stack<Piece[][]>();
        this.numOfMoves = 0;

        init();
    }


    //helper method to initialize the board or reset it
    public void init() {
        current_winner=0;
        this.gameMoves = new Stack<Piece[][]>();
        this.numOfMoves = 0;
        for (int rows = 0; rows < board_size; rows++) {
            for (int columns = 0; columns < board_size; columns++) {
                GameBoard[rows][columns] = null;
            }
        }

    }

    public int getCurrent_winner() {
        return current_winner;
    }


    @Override
    public boolean add(Position a) {
        //check if the position is occupied
        if (GameBoard[a.getX()][a.getY()] != null) {
            return false;
        }
        if(isSecondPlayerTurn()) {
            GameBoard[a.getX()][a.getY()] = new ConcretePiece(secondPlayer);

            Piece[][] current= new Piece[board_size][board_size];

            for(int i=0;i<board_size;i++)
                for (int j=0;j<board_size;j++)
                    current[i][j]=GameBoard[i][j];

            gameMoves.add(current);
            numOfMoves++;
            return true;

        }
        else {
            GameBoard[a.getX()][a.getY()] = new ConcretePiece(firstPlayer);

            Piece[][] current= new Piece[board_size][board_size];

            for(int i=0;i<board_size;i++)
                for (int j=0;j<board_size;j++)
                    current[i][j]=GameBoard[i][j];

            gameMoves.add(current);
            numOfMoves++;
            return true;
        }


    }

    @Override
    public Piece getPieceAtPosition(Position position) {


        int x = position.getX();
        int y = position.getY();


        //check to see if the position is within the range of the board
        if (x >= 0 && x < board_size && y >= 0 && y < board_size) {
            return GameBoard[x][y];
        } else {
            return null;
        }
    }

    @Override
    public Player getFirstPlayer() {
        return this.firstPlayer;
    }


    @Override
    public Player getSecondPlayer() {
        return this.secondPlayer;
    }


    @Override
    public boolean isGameFinished() {
        int sum=0;
        for(int i=0;i<board_size;i++)
            for (int j=0;j<board_size;j++)
                if(GameBoard[i][j]!=null)
                    sum++;



        if((GameBoard[0][0]!=null && GameBoard[0][0].getOwner()==firstPlayer && GameBoard[0][1]!=null && GameBoard[0][1].getOwner()==firstPlayer && GameBoard[0][2]!=null && GameBoard[0][2].getOwner()==firstPlayer)
            ||(GameBoard[1][0]!=null && GameBoard[1][0].getOwner()==firstPlayer && GameBoard[1][1]!=null && GameBoard[1][1].getOwner()==firstPlayer && GameBoard[1][2]!=null && GameBoard[1][2].getOwner()==firstPlayer)
            ||(GameBoard[2][0]!=null && GameBoard[2][0].getOwner()==firstPlayer && GameBoard[2][1]!=null && GameBoard[2][1].getOwner()==firstPlayer && GameBoard[2][2]!=null && GameBoard[2][2].getOwner()==firstPlayer)
            ||(GameBoard[0][0]!=null && GameBoard[0][0].getOwner()==firstPlayer && GameBoard[1][0]!=null && GameBoard[1][0].getOwner()==firstPlayer && GameBoard[2][0]!=null && GameBoard[2][0].getOwner()==firstPlayer)
            ||(GameBoard[0][1]!=null && GameBoard[0][1].getOwner()==firstPlayer && GameBoard[1][1]!=null && GameBoard[1][1].getOwner()==firstPlayer && GameBoard[2][1]!=null && GameBoard[2][1].getOwner()==firstPlayer)
            ||(GameBoard[0][2]!=null && GameBoard[0][2].getOwner()==firstPlayer && GameBoard[1][2]!=null && GameBoard[1][2].getOwner()==firstPlayer && GameBoard[2][2]!=null && GameBoard[2][2].getOwner()==firstPlayer)
            ||(GameBoard[0][0]!=null && GameBoard[0][0].getOwner()==firstPlayer && GameBoard[1][1]!=null && GameBoard[1][1].getOwner()==firstPlayer && GameBoard[2][2]!=null && GameBoard[2][2].getOwner()==firstPlayer)
            ||(GameBoard[0][2]!=null && GameBoard[0][2].getOwner()==firstPlayer && GameBoard[1][1]!=null && GameBoard[1][1].getOwner()==firstPlayer && GameBoard[2][0]!=null && GameBoard[2][0].getOwner()==firstPlayer))
        {
            firstPlayer.addWin();
            current_winner=1;
            return true;
        }
        else if ((GameBoard[0][0]!=null && GameBoard[0][0].getOwner()==secondPlayer && GameBoard[0][1]!=null && GameBoard[0][1].getOwner()==secondPlayer && GameBoard[0][2]!=null && GameBoard[0][2].getOwner()==secondPlayer)
                ||(GameBoard[1][0]!=null && GameBoard[1][0].getOwner()==secondPlayer && GameBoard[1][1]!=null && GameBoard[1][1].getOwner()==secondPlayer && GameBoard[1][2]!=null && GameBoard[1][2].getOwner()==secondPlayer)
                ||(GameBoard[2][0]!=null && GameBoard[2][0].getOwner()==secondPlayer && GameBoard[2][1]!=null && GameBoard[2][1].getOwner()==secondPlayer && GameBoard[2][2]!=null && GameBoard[2][2].getOwner()==secondPlayer)
                ||(GameBoard[0][0]!=null && GameBoard[0][0].getOwner()==secondPlayer && GameBoard[1][0]!=null && GameBoard[1][0].getOwner()==secondPlayer && GameBoard[2][0]!=null && GameBoard[2][0].getOwner()==secondPlayer)
                ||(GameBoard[0][1]!=null && GameBoard[0][1].getOwner()==secondPlayer && GameBoard[1][1]!=null && GameBoard[1][1].getOwner()==secondPlayer && GameBoard[2][1]!=null && GameBoard[2][1].getOwner()==secondPlayer)
                ||(GameBoard[0][2]!=null && GameBoard[0][2].getOwner()==secondPlayer && GameBoard[1][2]!=null && GameBoard[1][2].getOwner()==secondPlayer && GameBoard[2][2]!=null && GameBoard[2][2].getOwner()==secondPlayer)
                ||(GameBoard[0][0]!=null && GameBoard[0][0].getOwner()==secondPlayer && GameBoard[1][1]!=null && GameBoard[1][1].getOwner()==secondPlayer && GameBoard[2][2]!=null && GameBoard[2][2].getOwner()==secondPlayer)
                ||(GameBoard[0][2]!=null && GameBoard[0][2].getOwner()==secondPlayer && GameBoard[1][1]!=null && GameBoard[1][1].getOwner()==secondPlayer && GameBoard[2][0]!=null && GameBoard[2][0].getOwner()==secondPlayer))
        {
            secondPlayer.addWin();
            current_winner=2;
            return true;
        }
        else if(sum==9)
            return true;

        return false;

    }

    //if number of moves is even it's player 2 turn
    @Override
    public boolean isSecondPlayerTurn() {
        return numOfMoves % 2 == 0;
    }

    @Override
    public void reset() {
        init();


    }

    @Override
    public void undoLastMove() {
        if (gameMoves.size() > 0) {

            Piece[][] update = new Piece[board_size][board_size];
            update = gameMoves.pop();

            for (int i = 0; i < board_size; i++)
                for (int j = 0; j < board_size; j++)
                    GameBoard[i][j] = update[i][j];
        }


        else
        {
            for (int i = 0; i < board_size; i++)
                for (int j = 0; j < board_size; j++)
                    GameBoard[i][j] = null;
        }
     numOfMoves++;
    }

    @Override
    public int getBoardSize() {
        return board_size;
    }
}



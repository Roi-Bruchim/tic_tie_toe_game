package VikingsChess;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI_for_tic_tac_toe extends JFrame {
    private static final int BUTTON_SIZE = 200;
    private static final int FONT_SIZE = 140;
    private final JButton[][] buttons;
    private final int BOARD_SIZE;
    private final PlayableLogic gameLogic;
    private final JLabel turnLabel = new JLabel("Player 2's Turn");
    private final JLabel playerTwoWinsLabel = new JLabel("X Player 2 Wins: 0");
    private final JLabel playerOneWinsLabel = new JLabel("O Player 1 Wins: 0");
    private final JLabel victoryMessageLabel = new JLabel();
    private static final int MESSAGE_DURATION = 3000;  // Duration in milliseconds
    private final JPanel mainPanel = new JPanel(new BorderLayout());




    public GUI_for_tic_tac_toe(PlayableLogic gameLogic, String title) {
        this.gameLogic = gameLogic;
        this.BOARD_SIZE = gameLogic.getBoardSize();
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton resetButton = new JButton("Reset");
        topPanel.add(resetButton, BorderLayout.EAST);
        resetButton.addActionListener(e -> resetGame());

        JButton undoButton = new JButton("Undo");
        topPanel.add(undoButton, BorderLayout.WEST);
        undoButton.addActionListener(e -> undoMove());

        JPanel leftLabelPanel = new JPanel();
        JPanel rightLabelPanel = new JPanel();
        leftLabelPanel.add(playerTwoWinsLabel);
        rightLabelPanel.add(playerOneWinsLabel);
        topPanel.add(leftLabelPanel, BorderLayout.WEST);
        topPanel.add(turnLabel, BorderLayout.CENTER);
        topPanel.add(rightLabelPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Add a JLabel for displaying victory messages
        victoryMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        victoryMessageLabel.setFont(new Font("DejaVu Sans", Font.BOLD, 20));
        add(victoryMessageLabel, BorderLayout.SOUTH);

        mainPanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        buttons = new JButton[BOARD_SIZE][BOARD_SIZE];

        for (int col = 0; col < BOARD_SIZE; col++) {
            for (int row = 0; row < BOARD_SIZE; row++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
                Font chessFont = new Font("DejaVu Sans", Font.PLAIN, FONT_SIZE);
                buttons[row][col].setFont(chessFont);

                if ((row + col) % 2 != 0) {
                    buttons[row][col].setBackground(Color.WHITE);
                } else {
                    buttons[row][col].setBackground(new Color(0, 200, 255));
                }
                if (row % (BOARD_SIZE - 1) == 0 && col % (BOARD_SIZE - 1) == 0) {
                    buttons[row][col].setBackground(new Color(0, 200, 255));
                }

                buttons[row][col].setBorderPainted(false);
                buttons[row][col].addActionListener((e) -> onButtonClick(e));
                mainPanel.add(buttons[row][col]);
            }
        }

        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void onButtonClick(java.awt.event.ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        int rowIndex = -1, colIndex = -1;

        // Find the clicked button's position
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (buttons[row][col] == clickedButton) {
                    rowIndex = row;
                    colIndex = col;
                    break;
                }
            }
        }

        if (rowIndex != -1 && colIndex != -1) {
            Position clickedPosition = new Position(rowIndex, colIndex);
            if (gameLogic.add(clickedPosition)) {
                updateBoard();

                // Check For victory
                if (gameLogic.isGameFinished()) {
                    displayVictoryMessage();
                }
            }
        }
    }

    private void displayVictoryMessage() {
        if (gameLogic.getCurrent_winner()==1) {
            victoryMessageLabel.setText("Player 1 won!");
        } else if (gameLogic.getCurrent_winner()==2) {
            victoryMessageLabel.setText("Player 2 won!");
        } else {
            victoryMessageLabel.setText("It's a draw!");
        }

        // Use Timer to make the message disappear after MESSAGE_DURATION milliseconds
        Timer timer = new Timer(MESSAGE_DURATION, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                victoryMessageLabel.setText("");
                resetGame();
            }
        });
        timer.setRepeats(false);  // Run the timer only once
        timer.start();
    }

    private void updateBoard() {
        if (gameLogic.isSecondPlayerTurn()) {
            turnLabel.setText("Player 2's Turn");
        } else {
            turnLabel.setText("Player 1's Turn");
        }

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Piece piece = gameLogic.getPieceAtPosition(new Position(row, col));
                if (piece != null) {
                    String type = piece.getType();
                    buttons[row][col].setText(type);
                    if ((piece.getOwner().isPlayerOne())) {
                        buttons[row][col].setForeground(new Color(255, 0, 0)); // Neon red
                    } else {
                        buttons[row][col].setForeground(Color.BLACK);
                    }
                } else {
                    buttons[row][col].setText("");
                }
            }
        }
        updateWinsLabels(gameLogic.getSecondPlayer().getWins(), gameLogic.getFirstPlayer().getWins());
    }

    private void updateWinsLabels(int attackerWins, int defenderWins) {
        playerTwoWinsLabel.setText("♟ Player 2 Wins: " + attackerWins);
        playerOneWinsLabel.setText("♙ Player 1 Wins: " + defenderWins);
    }

    private void resetGame() {
        gameLogic.reset();
        updateBoard();
        turnLabel.setText("Player 2's Turn");
    }

    private void undoMove() {
        gameLogic.undoLastMove();
        updateBoard();
        turnLabel.setText("Player 2's Turn");
    }

    public static void main(String[] args) {
        PlayableLogic gameLogic = new GameLogic();
        SwingUtilities.invokeLater(() -> {
            // Set the UIManager property to make the focus color transparent
            UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
            UIManager.put("Button.select", new ColorUIResource(new Color(0, 0, 0, 0)));

            new GUI_for_tic_tac_toe(gameLogic, "Vikings Chess Game");
        });
    }
}



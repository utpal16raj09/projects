import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe extends JFrame implements ActionListener {
    private static final int SIZE = 3; // Board size 3x3
    private JButton[][] buttons;
    private char currentPlayer;
    private int[] winningLine; // To store the winning line coordinates (row/col/diag)
    private boolean gameOver;

    public TicTacToe() {
        buttons = new JButton[SIZE][SIZE];
        currentPlayer = 'X';
        winningLine = new int[4]; // Store winning line information
        gameOver = false;

        setTitle("Tic-Tac-Toe");
        setSize(300, 300);
        setLayout(new GridLayout(SIZE, SIZE));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].addActionListener(this);
                add(buttons[row][col]);
            }
        }

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (button.getText().equals("") && !gameOver) {
            button.setText(String.valueOf(currentPlayer));
            if (checkWin()) {
                gameOver = true;
                JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
            } else if (isBoardFull()) {
                gameOver = true;
                JOptionPane.showMessageDialog(this, "It's a draw!");
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            }
        }
    }

    private boolean checkWin() {
        // Check rows and columns
        for (int i = 0; i < SIZE; i++) {
            if (buttons[i][0].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[i][1].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[i][2].getText().equals(String.valueOf(currentPlayer))) {
                winningLine = new int[]{i, 0, i, 2}; // Horizontal win
                return true;
            }
            if (buttons[0][i].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[1][i].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[2][i].getText().equals(String.valueOf(currentPlayer))) {
                winningLine = new int[]{0, i, 2, i}; // Vertical win
                return true;
            }
        }
        // Check diagonals
        if (buttons[0][0].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[2][2].getText().equals(String.valueOf(currentPlayer))) {
            winningLine = new int[]{0, 0, 2, 2}; // Diagonal win from top-left to bottom-right
            return true;
        }
        if (buttons[0][2].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[2][0].getText().equals(String.valueOf(currentPlayer))) {
            winningLine = new int[]{0, 2, 2, 0}; // Diagonal win from top-right to bottom-left
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (buttons[row][col].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        currentPlayer = 'X';
        winningLine = new int[]{-1, -1, -1, -1}; // Reset winning line
        gameOver = false;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                buttons[row][col].setText("");
            }
        }
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (gameOver) {
            drawWinningLine(g);
        }
    }

    private void drawWinningLine(Graphics g) {
        g.setColor(Color.RED);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(8)); // Set line thickness

        int width = getWidth() / SIZE;
        int height = getHeight() / SIZE;

        int x1 = winningLine[1] * width + width / 2;
        int y1 = winningLine[0] * height + height / 2;
        int x2 = winningLine[3] * width + width / 2;
        int y2 = winningLine[2] * height + height / 2;

        // Draw the line connecting the winning positions
        g2d.drawLine(x1, y1, x2, y2);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToe::new);
    }
}

package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private boolean playerXTurn = true;
    private int[][] board = new int[3][3];
    private GridLayout gridLayout;
    private TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.gridLayout);
        statusText = findViewById(R.id.statusText);
        Button resetButton = findViewById(R.id.resetButton);

        // Create game board buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button(this);
                button.setWidth(200);
                button.setHeight(200);
                button.setTextSize(30);
                button.setTag(new int[]{i, j});
                button.setOnClickListener(this::onCellClick);
                gridLayout.addView(button);
            }
        }

        resetButton.setOnClickListener(v -> resetGame());
    }

    private void onCellClick(View view) {
        Button button = (Button) view;
        int[] position = (int[]) button.getTag();
        int row = position[0];
        int col = position[1];

        if (board[row][col] != 0) return;

        if (playerXTurn) {
            button.setText("X");
            board[row][col] = 1;
        } else {
            button.setText("O");
            board[row][col] = 2;
        }

        if (checkWin()) {
            statusText.setText((playerXTurn ? "X" : "O") + " Wins!");
            disableAllButtons();
            return;
        }

        if (isBoardFull()) {
            statusText.setText("It's a Draw!");
            return;
        }

        playerXTurn = !playerXTurn;
        statusText.setText((playerXTurn ? "X" : "O") + "'s Turn");
    }

    private boolean checkWin() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][0] == board[i][2]) {
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (board[0][j] != 0 && board[0][j] == board[1][j] && board[0][j] == board[2][j]) {
                return true;
            }
        }

        // Check diagonals
        if (board[0][0] != 0 && board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
            return true;
        }
        return board[0][2] != 0 && board[0][2] == board[1][1] && board[0][2] == board[2][0];
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void disableAllButtons() {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            gridLayout.getChildAt(i).setEnabled(false);
        }
    }

    private void resetGame() {
        board = new int[3][3];
        playerXTurn = true;
        statusText.setText("Player X's Turn");

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button button = (Button) gridLayout.getChildAt(i);
            button.setText("");
            button.setEnabled(true);
        }
    }
}
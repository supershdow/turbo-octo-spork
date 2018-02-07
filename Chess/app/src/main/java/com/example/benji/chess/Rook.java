package com.example.benji.chess;

import android.widget.ImageView;

/**
 * Created by benji on 1/2/18.
 */

public class Rook extends Piece {
    public Rook(int row, int col){
        super(row, col);
    }

    public Rook(ImageView iv, int row, int col){
        super(iv, row, col);
    }

    public int getLight(){
        if (whitePiece)
            return R.drawable.lightrooklightsquare;
        else
            return R.drawable.darkrooklightsquare;
    }

    public int getDark(){
        if (whitePiece)
            return R.drawable.lightrookdarksquare;
        else
            return R.drawable.darkrookdarksquare;
    }

    public boolean validMove(Piece[][] board, int newRow, int newCol){
        if (newRow == row && newCol == col)
            return false;
        if (newRow == row) {
            for (int i = col + 1; i < newCol; i++)
                if (!board[row][i].empty)
                    return false;
            for (int i = col - 1; i > newCol; i--)
                if (!board[row][i].empty)
                    return false;
            if (board[row][newCol].empty || board[row][newCol].whitePiece != whitePiece)
                return true;
        } else if (newCol == col){
            for (int i = row + 1; i < newRow; i++)
                if (!board[i][col].empty)
                    return false;
            for (int i = row - 1; i > newRow; i--)
                if (!board[i][col].empty)
                    return false;
            if (board[newRow][newCol].empty || board[newRow][newCol].whitePiece != whitePiece)
                return true;
        }
        return false;
    }
}

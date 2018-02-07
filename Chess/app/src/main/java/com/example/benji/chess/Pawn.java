package com.example.benji.chess;

import android.widget.ImageView;

/**
 * Created by benji on 1/2/18.
 */

public class Pawn extends Piece {

    public Pawn(ImageView iv, int row, int col){
        super(iv, row, col);
        empty = false;
    }

    public int getLight(){
        if (whitePiece)
            return R.drawable.lightpawnlightsquare;
        else
            return R.drawable.darkpawnlightsquare;
    }

    public int getDark(){
        if (whitePiece)
            return R.drawable.lightpawndarksquare;
        else
            return R.drawable.darkpawndarksquare;
    }

    public boolean validMove(Piece[][] board, int newRow, int newCol){
        if (whitePiece){
            if (row == 6) {
                if (newRow == 5 && col == newCol && board[5][col].empty)
                    return true;
                else if (newRow == 4 && board[5][newCol].empty && col == newCol && board[4][col].empty)
                    return true;
            }
            if (newRow == row - 1 && col == newCol && board[newRow][col].empty)
                return true;
            else if (newRow == row - 1 && ((newCol == (col + 1)) || newCol == (col - 1)) && !board[newRow][newCol].whitePiece)
                return true;
        } else {
            if (row == 1) {
                if (newRow == 2 && col == newCol && board[2][col].empty)
                    return true;
                else if (newRow == 3 && board[2][newCol].empty && col == newCol && board[3][col].empty)
                    return true;
            }
            if (newRow == row + 1 && col == newCol && board[newRow][col].empty)
                return true;
            else if (newRow == row + 1 && ((newCol == (col + 1)) || newCol == (col - 1)) && board[newRow][newCol].whitePiece)
                return true;
        }

        return false;
    }
}

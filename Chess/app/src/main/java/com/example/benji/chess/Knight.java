package com.example.benji.chess;

import android.widget.ImageView;

/**
 * Created by benji on 1/2/18.
 */

public class Knight extends Piece {

    public Knight(ImageView iv, int row, int col){
        super(iv, row, col);
    }

    public int getLight(){
        if (whitePiece)
            return R.drawable.lightknightlightsquare;
        else
            return R.drawable.darkknightlightsquare;
    }

    public int getDark(){
        if (whitePiece)
            return R.drawable.lightknightdarksquare;
        else
            return R.drawable.darkknightdarksquare;
    }

    public boolean validMove(Piece[][] board, int newRow, int newCol){
        if (board[newRow][newCol].empty || board[newRow][newCol].whitePiece != whitePiece) {
            if ((newRow == row + 2 || newRow == row - 2) && (newCol == col + 1 || newCol == col - 1))
                return true;
            else if ((newRow == row + 1 || newRow == row - 1) && (newCol == col + 2 || newCol == col - 2))
                return true;
        }

        return false;
    }

}

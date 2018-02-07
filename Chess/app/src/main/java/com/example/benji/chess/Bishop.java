package com.example.benji.chess;

import android.widget.ImageView;

/**
 * Created by benji on 1/2/18.
 */

public class Bishop extends Piece {
    public Bishop(int row, int col){
        super(row, col);
    }

    public Bishop(ImageView iv, int row, int col){
        super(iv, row, col);
    }

    public int getLight(){
        if (whitePiece)
            return R.drawable.lightbishoplightsquare;
        else
            return R.drawable.darkbishoplightsquare;
    }

    public int getDark(){
        if (whitePiece)
            return R.drawable.lightbishopdarksquare;
        else
            return R.drawable.darkbishopdarksquare;
    }

    public boolean validMove(Piece[][] board, int newRow, int newCol){
        if (Math.abs(newRow - row) != Math.abs(newCol - col) || newCol == col)
            return false;
        if (newRow > row && newCol > col)
            for (int i = 1; i < Math.abs(newRow - row); i++)
                if (!board[row + i][col + i].empty)
                    return false;
        if (newRow > row && newCol < col)
            for (int i = 1; i < Math.abs(newRow - row); i++)
                if (!board[row + i][col - i].empty)
                    return false;
        if (newRow < row && newCol > col)
            for (int i = 1; i < Math.abs(newRow - row); i++)
                if (!board[row - i][col + i].empty)
                    return false;
        if (newRow < row && newCol < col)
            for (int i = 1; i < Math.abs(newRow - row); i++)
                if (!board[row - i][col - i].empty)
                    return false;
        if (board[newRow][newCol].empty || board[newRow][newCol].whitePiece != whitePiece)
            return true;
        return false;
    }
}

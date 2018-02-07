package com.example.benji.chess;

import android.widget.ImageView;

/**
 * Created by benji on 1/2/18.
 */

public class Queen extends Piece{
    Bishop b;
    Rook r;


    public Queen(ImageView iv, int row, int col, boolean white){
        super(iv, row, col);
        b = new Bishop(row, col);
        r = new Rook(row, col);
        b.whitePiece = white;
        r.whitePiece = white;

    }

    public int getLight(){
        if (whitePiece)
            return R.drawable.lightqueenlightsquare;
        else
            return R.drawable.darkqueenlightsquare;
    }

    public int getDark(){
        if (whitePiece)
            return R.drawable.lightqueendarksquare;
        else
            return R.drawable.darkqueendarksquare;
    }

    public boolean validMove(Piece[][] board, int newRow, int newCol){
        return b.validMove(board, newRow, newCol) || r.validMove(board, newRow, newCol);
    }

    public void move(int newRow, int newCol){
        b.row = newRow;
        b.col = newCol;
        r.row = newRow;
        r.col = newCol;
        super.move(newRow, newCol);
    }
}

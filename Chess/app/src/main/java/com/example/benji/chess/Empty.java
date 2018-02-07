package com.example.benji.chess;

import android.widget.ImageView;

/**
 * Created by benji on 1/2/18.
 */

public class Empty extends Piece {

    public Empty(ImageView iv, int row, int col){
        super(iv, row, col);
        empty = true;
    }

    public int getLight(){
        return R.drawable.lightsquare;
    }

    public int getDark(){
        return R.drawable.darksquare;
    }

    public boolean validMove(Piece[][] board, int newRow, int newCol){
        return false;
    }

}

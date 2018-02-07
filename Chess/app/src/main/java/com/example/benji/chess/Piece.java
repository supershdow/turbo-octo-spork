package com.example.benji.chess;

import android.view.View;
import android.widget.ImageView;

/**
 * Created by benji on 1/2/18.
 */

public abstract class Piece {
    public ImageView image;
    public int row, col;
    public boolean whitePiece;
    public boolean empty = false;
    Piece self = this;

    public Piece(int row, int col){
        this.row = row;
        this.col = col;
    }

    public Piece(ImageView iv, int row, int col){
        image = iv;
        this.row = row;
        this.col = col;
        whitePiece = false;
        if (iv != null) {
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MainActivity.selected == null && empty) {
                        MainActivity.self.output.setText("Piece selected: ");
                        return;
                    }
                    if (MainActivity.selected == null) {
                        if (whitePiece && MainActivity.self.turn % 2 == 0) {
                            MainActivity.selected = self;
                            MainActivity.self.output.setText("Piece selected: " + self.getClass().getSimpleName());
                        }
                    } else if (MainActivity.selected.validMove(MainActivity.board, self.row, self.col)) {
                        MainActivity.selected.move(self.row, self.col);
                        MainActivity.selected = null;
                        MainActivity.self.output.setText("Piece selected: ");
                        MainActivity.self.turn++;
                    } else {
                        MainActivity.selected = null;
                        MainActivity.self.output.setText("Piece selected: ");
                    }
                }
            });
        }
    }



    public void move(int newRow, int newCol){
        MainActivity.board[newRow][newCol].image.setVisibility(View.GONE);
        MainActivity.self.lay.removeView(MainActivity.board[newRow][newCol].image);
        MainActivity.board[newRow][newCol] = this;
        MainActivity.board[row][col] = null;
        row = newRow;
        col = newCol;
        checkColor();
        MainActivity.self.reload();
    }

    public void checkColor(){
        if ((row + col) % 2 == 0)
            image.setImageResource(getLight());
        else
            image.setImageResource(getDark());
    }

    public abstract boolean validMove(Piece[][] board, int newRow, int newCol);

    public abstract int getLight();

    public abstract int getDark();

}

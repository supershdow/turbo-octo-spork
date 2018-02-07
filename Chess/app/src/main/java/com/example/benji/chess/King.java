package com.example.benji.chess;

import android.widget.ImageView;

/**
 * Created by benji on 1/2/18.
 */

public class King extends Piece {
    public King(ImageView iv, int row, int col){
        super(iv, row, col);
    }

    public int getLight(){
        if (whitePiece)
            return R.drawable.lightkinglightsquare;
        else
            return R.drawable.darkkinglightsquare;
    }

    public int getDark(){
        if (whitePiece)
            return R.drawable.lightkingdarksquare;
        else
            return R.drawable.darkkingdarksquare;
    }

    public boolean validMove(Piece[][] board, int newRow, int newCol){
        if (board[newRow][newCol].empty || board[newRow][newCol].whitePiece != whitePiece)
            if (Math.sqrt(Math.pow(newRow - row, 2) + Math.pow(newCol - col, 2)) < 2) {
                if (isCheck(board, newRow, newCol))
                    return false;
                return true;
            }
        return false;
    }

    public boolean isCheck(Piece[][] board){
        return isCheck(board, row, col);
    }

    public boolean isCheck(Piece[][] board, int row, int col){
        int oldRow = this.row, oldCol = this.col;
        Piece oldPiece = board[row][col];
        board[row][col] = this;
        this.row = row;
        this.col = col;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++){
                if (i == row && j == col)
                    continue;
                else if (board[i][j].empty || board[i][j].whitePiece == this.whitePiece)
                    continue;
                else if (board[i][j].getClass().getName().equals("com.example.benji.chess.King")){
                    if (Math.sqrt(Math.pow(i - row, 2) + Math.pow(j - col, 2)) < 2) {
                        this.row = oldRow;
                        this.col = oldCol;
                        board[row][col] = oldPiece;
                        return true;
                    }
                }
                else if (board[i][j].validMove(board, row, col)) {
                    this.row = oldRow;
                    this.col = oldCol;
                    MainActivity.board[row][col] = oldPiece;
                    return true;
                }

            }
        this.row = oldRow;
        this.col = oldCol;
        board[row][col] = oldPiece;
        return false;
    }
}

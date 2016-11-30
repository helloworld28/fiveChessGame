package com.jun.fivechess.domain;

/**
 * Created by Administrator on 2016-11-18.
 */
public class Move {
        int row;
        int col;

    public Move() {
    }

    public Move(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}

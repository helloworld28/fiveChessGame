package com.jun.fivechess.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-11-28.
 */
public class FivePointUtil {
    static int BOUND_COL = 30;
    static int BOUND_ROW = 30;

    public static List<FivePointUtil.Point[]> getAllFivePointArray(Point point)  {

        List<Point[]> totalPoints = new ArrayList<>();
        //取横向的点组合
        totalPoints.addAll(getRowPoint(point.getRow(), point.getCol()));
        //纵向的点组合
        totalPoints.addAll(getColPoint(point.getRow(), point.getCol()));
        //左斜的点组合
        totalPoints.addAll(getLeftPoint(point.getRow(), point.getCol()));
        //右斜的点组合
        totalPoints.addAll(getRightPoint(point.getRow(), point.getCol()));
        return totalPoints;

    }

    public static List<Point[]> getRowPoint(int row, int col){
        List<Point[]> pointArrays = new ArrayList<Point[]>();
        for(int n = 0; n < 5; n++){

            if(col - n >= 0 && col + 4-n < BOUND_COL){
                Point[] pointArray = new Point[5];
                int beginCol = col - n;
                for(int i = 0; i < 5; i++){
                    pointArray[i] = new Point(row, col + i);
                }
                pointArrays.add(pointArray);
            }

        }
        return pointArrays;
    }

    public static List<Point[]> getColPoint(int row, int col){
        List<Point[]> pointArrays = new ArrayList<Point[]>();
        for(int n = 0; n < 5; n++){
            if(row - n >= 0 && row + 4-n < BOUND_ROW){
                Point[] pointArray = new Point[5];
                int beginRow = row - n;
                for(int i = 0; i < 5; i++){
                    pointArray[i] = new Point(beginRow + i, col);
                }
                pointArrays.add(pointArray);
            }
        }
        return pointArrays;
    }

    public static List<Point[]> getLeftPoint(int row, int col){
        List<Point[]> pointArrays = new ArrayList<Point[]>();

        if(BOUND_ROW - row + col >= 4 || BOUND_COL -col + row >=4){
            //位于前五列的点时斜向的
            //位置于斜线第一点
            for(int n = 0; n < 5; n ++){
                if(col  >= n && row >= n  && row + (4-n) <= BOUND_ROW && col + (4-n) < BOUND_COL){
                    Point[] pointArray = new Point[5];
                    int beginRow = row- n;
                    int beginCol = col- n;
                    for(int i = 0 ; i < 5; i++){
                        pointArray[i] = new Point(beginRow + i, beginCol + i);
                    }
                    pointArrays.add(pointArray);
                }
            }

        }
        return pointArrays;
    }


    public static List<Point[]> getRightPoint(int row, int col){
        List<Point[]> pointArrays = new ArrayList<Point[]>();

        if(row + col >= 4 || BOUND_COL + BOUND_ROW -col - row >=4){
            //分别位于五点时斜向的位置
            for(int n = 0; n < 5; n ++){
                //从左到右的顺序
                if(row >= (4-n)  && (row-4+n) <= BOUND_ROW && col - n >= 0 && col + (4-n) <= BOUND_COL){
                    Point[] pointArray = new Point[5];
                    int beginRow = row+ n;
                    int beginCol = col- n;
                    for(int i = 0 ; i < 5; i++){
                        pointArray[i] = new Point(beginRow - i, beginCol + i);
                    }
                    pointArrays.add(pointArray);
                }
            }

        }


        return pointArrays;
    }


    public static class Point{
        int row, col;

        public Point(int row, int col) {
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

        @Override
        public String toString() {
            return "Point{" +
                    "row=" + row +
                    ", col=" + col +
                    '}';
        }
    }
}

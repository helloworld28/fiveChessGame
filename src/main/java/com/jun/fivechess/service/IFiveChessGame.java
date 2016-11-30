package com.jun.fivechess.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jun.fivechess.common.util.FivePointUtil;
import com.jun.fivechess.constant.GameStatus;

import java.util.*;

/**
 * Created by Administrator on 2016-11-15.
 */
public abstract class IFiveChessGame {



    public String gameId;

    public IPlayer player1;
    public IPlayer player2;
    @JsonIgnore
    public IPlayer[][] grid = new IPlayer[30][30];

    public GameStatus status;
    public IPlayer nextPlayer;
    public IPlayer winner;

    public static Map<String, IFiveChessGame> games = new HashMap<String, IFiveChessGame>();

    public IFiveChessGame( IPlayer player1) {
        this.gameId = UUID.randomUUID().toString();
        this.player1 = player1;
        this.status = GameStatus.WAITING;
        games.put(this.gameId, this);
    }

    public void joinGame(IPlayer player2){
        this.player2 = player2;
    }

    public  void start(){
        this.status = GameStatus.PLAYING;
        this.nextPlayer =  new Random().nextBoolean() ? this.player1 : this.player2;
    }

    public boolean move(IPlayer player, int row, int col){

        if(this.nextPlayer == player){
            this.grid[row][col] = player;
            printChessBoard();
            if(caculate()){

                this.status = GameStatus.OVER;
                this.winner = player;
            }
            this.nextPlayer = player == this.player1 ? this.player2 : this.player1;
            return true;
        }else{
            return false;
        }

    }

    public void printChessBoard(){
        System.out.println("************************************");
        for(int i=0; i< 30; i++){
            for(int j = 0; j< 30; j++){
                if(grid[i][j] == null){

                    System.out.print("(0)");
                }
                if(grid[i][j] == player1){

                    System.out.print("(1)");
                }
                if(grid[i][j] == player2){

                    System.out.print("(2)");
                }
            }
            System.out.println();

        }
    }

    public  boolean caculate(){
      boolean rs = false;
        for(int row = 0 ; row < 30; row ++){
            for(int col = 0; col < 30; col ++){
               if(grid[row][col] != null){
                   List<FivePointUtil.Point[]> pointArrays =
                           FivePointUtil.getAllFivePointArray(new FivePointUtil.Point(row, col));
                  if(hasFivePoint(pointArrays)){
                      rs = true;
                      break;
                  }
               }

            }
        }
        System.out.print("目前计算到游戏结果为"+rs);
        return rs;
    }

    /**
     * 判断是否存在五个点都是同一个下的棋
     * @param pointArrays
     * @return
     */
    private boolean hasFivePoint(List<FivePointUtil.Point[]> pointArrays ){

        for(FivePointUtil.Point[] points : pointArrays){

            int i = 0;
            for(int n = 0; n < 4; n ++){
                if(grid[points[n].getRow()][points[n].getCol()] == null
                        || grid[points[n].getRow()][points[n].getCol()] !=
                        grid[points[n+1].getRow()][points[n+1].getCol()]){
                    break;
                }
                i ++;
            }

            if(i >= 4){
                return true;
            }
        }
        return false;
    }

    public IPlayer getWiner(){
        return this.winner;
    }

    public IPlayer getNextPlayer(){
     return this.nextPlayer;
    }

    public boolean isOver(){
        return GameStatus.OVER == status;
    }

    public String getGameId(){
        return this.gameId;
    }


}

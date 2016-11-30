package com.jun.fivechess.service.impl;

import com.jun.fivechess.constant.GameStatus;
import com.jun.fivechess.service.IFiveChessGame;
import com.jun.fivechess.service.IPlayer;

/**
 * Created by Administrator on 2016-11-16.
 */
public class FiveChessGameImpl extends IFiveChessGame {
    public FiveChessGameImpl( IPlayer player1) {
        super(player1);
    }

    public IPlayer getPlayer1(){
        return this.player1;
    }

    public IPlayer getPlayer2(){
        return this.player2;
    }

    public IPlayer getWiner(){
        return this.winner;
    }

    public IPlayer getNextPlayer(){
        return this.nextPlayer;
    }

    public GameStatus getStatus(){
        return this.status;
    }


    public String getGameId(){
        return this.gameId;
    }

}

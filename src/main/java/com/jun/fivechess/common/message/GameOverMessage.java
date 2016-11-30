package com.jun.fivechess.common.message;

import com.jun.fivechess.constant.MessageAction;
import com.jun.fivechess.service.IFiveChessGame;
import com.jun.fivechess.service.IPlayer;

/**
 * Created by Administrator on 2016-11-17.
 */
public class GameOverMessage extends IMessage {
    private IPlayer winner;

    public GameOverMessage(IPlayer winner) {
        super(MessageAction.GAME_OVER);
        this.winner = winner;
    }

    public IPlayer getWinner() {
        return winner;
    }

    public void setWinner(IPlayer winner) {
        this.winner = winner;
    }
}

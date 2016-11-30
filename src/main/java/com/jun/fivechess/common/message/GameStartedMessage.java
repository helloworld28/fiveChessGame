package com.jun.fivechess.common.message;

import com.jun.fivechess.constant.MessageAction;
import com.jun.fivechess.service.IFiveChessGame;

/**
 * Created by Administrator on 2016-11-17.
 */
public class GameStartedMessage extends IMessage {
    private IFiveChessGame fiveChessGame;

    public GameStartedMessage(IFiveChessGame fiveChessGame) {
        super(MessageAction.GAME_STARTED);
        this.fiveChessGame = fiveChessGame;
    }

    public IFiveChessGame getFiveChessGame() {
        return fiveChessGame;
    }

    public void setFiveChessGame(IFiveChessGame fiveChessGame) {
        this.fiveChessGame = fiveChessGame;
    }
}

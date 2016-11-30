package com.jun.fivechess.common.message;

import com.jun.fivechess.constant.MessageAction;
import com.jun.fivechess.domain.Move;

/**
 * Created by Administrator on 2016-11-18.
 */
public class MoveMessage extends IMessage {

    private Move move;

    public MoveMessage() {
    }

    public MoveMessage(Move move) {
        super(MessageAction.MOVE);
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }
}

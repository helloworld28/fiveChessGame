package com.jun.fivechess.common.message;

import com.jun.fivechess.constant.MessageAction;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-11-17.
 */
public abstract class IMessage implements Serializable{
    protected MessageAction action;

    public IMessage() {
    }

    public IMessage(MessageAction action) {
        this.action = action;
    }

    public MessageAction getAction() {
        return action;
    }

    public void setAction(MessageAction action) {
        this.action = action;
    }
}


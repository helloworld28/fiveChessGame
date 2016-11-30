package com.jun.fivechess.common.message;

import com.jun.fivechess.constant.MessageAction;

/**
 * Created by Administrator on 2016-11-29.
 */
public class GiveUpResponseMessage extends IMessage {
    private boolean content;

    public GiveUpResponseMessage(){

    }

    public boolean isContent() {
        return content;
    }

    public void setContent(boolean content) {
        this.content = content;
    }
    public boolean getContent() {
        return this.content;
    }

    public GiveUpResponseMessage(boolean content) {
        super(MessageAction.GIVE_UP_RESP);
        this.content = content;
    }

}

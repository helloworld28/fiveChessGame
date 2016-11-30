package com.jun.fivechess.common.message;

import com.jun.fivechess.constant.MessageAction;

/**
 * Created by Administrator on 2016-11-29.
 */
public class GiveUpMessage extends IMessage {
    private boolean content;

    public boolean isContent() {
        return content;
    }

    public void setContent(boolean content) {
        this.content = content;
    }
    public boolean getContent(boolean content) {
        return this.content = content;
    }

    public GiveUpMessage( boolean content) {
        super(MessageAction.GIVE_UP);
        this.content = content;
    }

    public GiveUpMessage() {
    }
}

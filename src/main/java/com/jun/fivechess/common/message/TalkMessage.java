package com.jun.fivechess.common.message;

import com.jun.fivechess.constant.MessageAction;

/**
 * Created by Administrator on 2016-11-29.
 */
public class TalkMessage extends IMessage {
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    String content;
    public TalkMessage() {
    }

    public TalkMessage(String content) {
        super(MessageAction.TALK_MESSAGE);
        this.content = content;
    }
}

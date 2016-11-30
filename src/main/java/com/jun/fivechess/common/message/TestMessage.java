package com.jun.fivechess.common.message;

import com.jun.fivechess.constant.MessageAction;
import com.jun.fivechess.domain.Move;

/**
 * Created by Administrator on 2016-11-23.
 */
public class TestMessage {
    String name;

    public TestMessage() {
    }

    public TestMessage(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

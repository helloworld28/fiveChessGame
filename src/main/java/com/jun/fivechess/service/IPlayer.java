package com.jun.fivechess.service;

/**
 * Created by Administrator on 2016-11-15.
 */
public abstract class IPlayer {

    private String name;

    public IPlayer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

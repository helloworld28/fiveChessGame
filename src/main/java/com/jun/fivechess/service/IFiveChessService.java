package com.jun.fivechess.service;

import com.jun.fivechess.common.MyException;

import java.util.List;

/**
 * Created by Administrator on 2016-11-15.
 */
public interface IFiveChessService {

    public void addPlayer(IPlayer player);

    public List<IPlayer> getOnlinePeople();

    public List<IFiveChessGame> getWaitingGames();

    public List<IFiveChessGame> getPlayingGames();

    public void remvoeGame(String gameId);

    public IFiveChessGame createGame(String playerName);

    public IFiveChessGame joinGame(String gameId, String playerName) throws MyException;

    public boolean move(String gameId, String playerName, int row, int col);

}

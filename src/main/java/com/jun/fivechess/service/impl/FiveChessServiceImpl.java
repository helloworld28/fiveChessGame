package com.jun.fivechess.service.impl;

import com.jun.fivechess.common.MyException;
import com.jun.fivechess.constant.GameStatus;
import com.jun.fivechess.service.IFiveChessGame;
import com.jun.fivechess.service.IFiveChessService;
import com.jun.fivechess.service.IPlayer;
import com.jun.fivechess.service.PlayerImpl;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016-11-15.
 */

public class FiveChessServiceImpl implements IFiveChessService {

    public List<IPlayer> onlinePlayers = new ArrayList<IPlayer>();

    public Map<String, IFiveChessGame> fiveChessGames = new HashMap<String, IFiveChessGame>();

    public List<IPlayer> getOnlinePeople() {
        return this.onlinePlayers;
    }

    public void addPlayer(IPlayer player) {
        onlinePlayers.add(player);
    }

    public List<IFiveChessGame> getWaitingGames() {
        Collection<IFiveChessGame> games = fiveChessGames.values();
        List<IFiveChessGame> results =  games.stream().filter( item -> item.status == GameStatus.WAITING ).collect(Collectors.toList());
        return results;
    }

    public List<IFiveChessGame> getPlayingGames() {
        Collection<IFiveChessGame> games = fiveChessGames.values();
        List<IFiveChessGame> results =  games.stream().filter( item -> item.status == GameStatus.PLAYING ).collect(Collectors.toList());
        return results;
    }

    @Override
    public void remvoeGame(String gameId) {
        fiveChessGames.remove(gameId);
    }

    public IFiveChessGame createGame(String playerName) {

        IFiveChessGame game = new FiveChessGameImpl(new PlayerImpl(playerName));
        fiveChessGames.put(game.getGameId(), game);
        return game;
    }

    public IFiveChessGame joinGame(String gameId, String playerName) throws MyException {
        IFiveChessGame game = fiveChessGames.get(gameId);

        if(game.player1.getName().equals(playerName)){
            throw new MyException("playerName can't be same!");
        }
        fiveChessGames.get(gameId).player2 = new PlayerImpl(playerName);
        return game;
    }

    public boolean move(String gameId, String playerName, int row, int col) {
        IFiveChessGame game = fiveChessGames.get(gameId);
        if(game == null)return false;
        IPlayer player = game.player1.getName().equals(playerName) ? game.player1 : game.player2;
        game.move(player, row, col);
        return true;
    }
}

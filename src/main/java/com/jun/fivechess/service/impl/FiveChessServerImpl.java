package com.jun.fivechess.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jun.fivechess.common.message.*;
import com.jun.fivechess.constant.GameStatus;
import com.jun.fivechess.constant.MessageAction;
import com.jun.fivechess.domain.Move;
import com.jun.fivechess.service.IFiveChessGame;
import com.jun.fivechess.service.IFiveChessServer;
import com.jun.fivechess.service.IPlayer;
import com.jun.fivechess.service.PlayerImpl;
import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import org.apache.commons.lang3.StringUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-11-17.
 */
@ServerEndpoint("/fiveChess/{gameId}/{userName}")
public class FiveChessServerImpl extends IFiveChessServer{
    private static Map<String, Game> games = new HashMap<String, Game>();

    private static ObjectMapper objectMapper = new ObjectMapper();
    @OnOpen
    public void onOpen(Session session, @PathParam("gameId") String gameId,@PathParam("userName") String userName){
        Game game = games.get(gameId);
//        try {
//                if (game != null && game.getFiveChessGame().status == GameStatus.PLAYING) {
////                    session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION,
////                            "this game has already started."));
//                }

            List<String> actions = session.getRequestParameterMap().get("action");
            if(actions != null && actions.size() == 1){
                if("start".equalsIgnoreCase(actions.get(0))){
                    IFiveChessGame fiveChessGame = FiveChessGameImpl.games.get(gameId);
                    Game newGame = new Game();
                    newGame.setFiveChessGame(fiveChessGame);
                    newGame.setPlayer1(session);
                    newGame.setGameId(fiveChessGame.gameId);
                    games.put(fiveChessGame.gameId, newGame);
                }else if("join".equalsIgnoreCase(actions.get(0))) {
                    game.setPlayer2(session);
                    game.getFiveChessGame().player2 = new PlayerImpl(userName);
                    game.getFiveChessGame().start();

                    GameStartedMessage message = new GameStartedMessage(game.getFiveChessGame());
                    this.sendJson(game.getPlayer1(), message);
                    this.sendJson(game.getPlayer2(), message);
                }
            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }

    @OnMessage
    public void onMessage(Session session, @PathParam("gameId")String gameId, String message){
        Game game = games.get(gameId);
        boolean isPlayer1 = game.player1 == session;
        Session opponent = isPlayer1 ? game.getPlayer2(): game.getPlayer1() ;
        IFiveChessGame fiveChessGame = game.getFiveChessGame();
        IPlayer currentPlayer = isPlayer1 ? fiveChessGame.player1 : fiveChessGame.player2;
        try {
            if(StringUtils.containsIgnoreCase(message, MessageAction.MOVE.toString())){
                //下棋
                MoveMessage moveMessage =  objectMapper.readValue(message.getBytes(), MoveMessage.class);
                Move move = moveMessage.getMove();
                fiveChessGame.move(currentPlayer, move.getRow(), move.getCol());

                this.sendJson(opponent, moveMessage);

                //游戏结束
                if(fiveChessGame.isOver()){
                    IMessage gameOverMessage = new GameOverMessage(fiveChessGame.getWiner());
                    this.sendJson(game.getPlayer1(), gameOverMessage);
                    this.sendJson(game.getPlayer2(), gameOverMessage);
                }
            }else if(StringUtils.containsIgnoreCase(message, MessageAction.TALK_MESSAGE.toString())){
                //聊天的消息 转发
                    opponent.getBasicRemote().sendText(message);
            }else if(StringUtils.containsIgnoreCase(message, MessageAction.GIVE_UP_RESP.toString())){
                //是否同意投降认输处理
                GiveUpResponseMessage giveUpResponseMessage = objectMapper.readValue(message.getBytes(), GiveUpResponseMessage.class);

                if(giveUpResponseMessage.getContent()){
                    //同意认输则设置游戏已经结束并设备胜利者
                    if(isPlayer1){
                        fiveChessGame.winner = fiveChessGame.player1;
                    }else{
                        fiveChessGame.winner = fiveChessGame.player2;
                    }
                    fiveChessGame.status = GameStatus.OVER;
                    //发送结果信息
                    IMessage gameOverMessage = new GameOverMessage(fiveChessGame.getWiner());
                    this.sendJson(game.getPlayer1(), gameOverMessage);
                    this.sendJson(game.getPlayer2(), gameOverMessage);
                }else{
                    //发送不同意认输的消息
                    this.sendJson(opponent, giveUpResponseMessage);
                }

            }else if(StringUtils.containsIgnoreCase(message, MessageAction.GIVE_UP.toString())){
                //认输的信息转发
                    opponent.getBasicRemote().sendText(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @OnClose
    public void onClose(Session session, @PathParam("gameId") String gameId){
        Game game = games.get(gameId);
        boolean isPlayer1 = game.player1 == session;
        Session opponent = isPlayer1 ? game.getPlayer2() : game.getPlayer1();
        try {
            opponent.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendJson(Session session, IMessage message){
        try {
            session.getBasicRemote().sendText(objectMapper.writeValueAsString(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static class Game{
        private String gameId;
        private Session player1;
        private Session player2;
        private IFiveChessGame fiveChessGame;

        public String getGameId() {
            return gameId;
        }

        public void setGameId(String gameId) {
            this.gameId = gameId;
        }

        public Session getPlayer1() {
            return player1;
        }

        public void setPlayer1(Session player1) {
            this.player1 = player1;
        }

        public Session getPlayer2() {
            return player2;
        }

        public void setPlayer2(Session player2) {
            this.player2 = player2;
        }

        public IFiveChessGame getFiveChessGame() {
            return fiveChessGame;
        }

        public void setFiveChessGame(IFiveChessGame fiveChessGame) {
            this.fiveChessGame = fiveChessGame;
        }
    }
}



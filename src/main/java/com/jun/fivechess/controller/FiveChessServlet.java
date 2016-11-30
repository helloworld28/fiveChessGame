package com.jun.fivechess.controller;

import com.jun.fivechess.common.MyException;
import com.jun.fivechess.service.IFiveChessGame;
import com.jun.fivechess.service.IFiveChessService;
import com.jun.fivechess.service.IPlayer;
import com.jun.fivechess.service.impl.FiveChessServerImpl;
import com.jun.fivechess.service.impl.FiveChessServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-11-15.
 */
@WebServlet(name = "fiveChessServlet", urlPatterns = "/fiveChessServlet")
public class FiveChessServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(FiveChessServlet.class);
    private static IFiveChessService fiveChessService = new FiveChessServiceImpl();
    private static List<String> onlinePeoples = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if("createGame".equals(action)){
            HttpSession session = req.getSession();
            IFiveChessGame fiveChessGame = (IFiveChessGame) session.getAttribute("fiveChessGame");
            if(fiveChessGame != null){
                fiveChessService.remvoeGame(fiveChessGame.getGameId());
            }
            fiveChessGame = fiveChessService.createGame((String) session.getAttribute("playerName"));
            session.setAttribute("fiveChessGame", fiveChessGame);
            req.setAttribute("action", "start");
            req.getRequestDispatcher("/WEB-INF/pages/game.jsp").forward(req, resp);
        }else if("gameList".equals(action)){
            logger.debug("initGame...");
            String playerName = req.getParameter("playerName") ;
            if(StringUtils.isNotBlank(playerName)){
                playerName = new String(playerName.getBytes("ISO-8859-1"), "UTF-8") ;
                req.getSession().setAttribute("playerName", playerName);
                onlinePeoples.add(playerName);
            }
            req.setAttribute("waitingGames", fiveChessService.getWaitingGames());
            req.setAttribute("playingGames", fiveChessService.getPlayingGames());
            req.setAttribute("onlinePeoples", onlinePeoples);
            req.getRequestDispatcher("/view?view=gameList").forward(req, resp);
        }else if("joinGame".equals(action)){
            String gameId = req.getParameter("gameId");
            String playerName = (String) req.getSession().getAttribute("playerName");
            try {
                IFiveChessGame game = fiveChessService.joinGame(gameId, playerName);
                game.start();
                req.setAttribute("action", "join");
                req.getSession().setAttribute("fiveChessGame", game);
                req.getRequestDispatcher("/WEB-INF/pages/game.jsp").forward(req, resp);
            } catch (MyException e) {

                e.printStackTrace();
            }

        }

    }
}

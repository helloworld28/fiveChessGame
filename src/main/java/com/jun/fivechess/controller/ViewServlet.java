package com.jun.fivechess.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016-11-15.
 */
@WebServlet(name="viewServlet", urlPatterns = "/view")
public class ViewServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String view = req.getParameter("view");
        req.getRequestDispatcher("/WEB-INF/pages/"+view+".jsp").forward(req, resp);
    }
}


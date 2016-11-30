<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016-11-15
  Time: 17:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>游戏列表</title>
</head>
<body>

正在对战的游戏：
<ul>
    <c:forEach var="item" items="${playingGames}">
        <li>${item.player1.name}</li>
    </c:forEach>
</ul>
正在等待开始的游戏：
<ul>
    <c:forEach var="item" items="${waitingGames}">
    <li>${item.player1.name}<a href='<c:url value="/fiveChessServlet?action=joinGame">
        <c:param name="gameId" value="${item.gameId}"/>
    </c:url>'>（加入游戏）</a></li>
    </c:forEach>
</ul>
操作
<ul>
    <li><a href="<c:url value="/fiveChessServlet?action=createGame"/>">创建游戏</a></li>
</ul>
</body>
</html>

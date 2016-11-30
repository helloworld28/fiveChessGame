<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<html>
<head>

</head>
<body>
<h2>欢迎来到五子棋世界!</h2>
<ul>
    <li><a href='#' onclick="login()">进入游戏</a></li>
</ul>
<div id="playerNameForm"><form action='<c:url value="/fiveChessServlet?action=gameList"/>' method="post">
    <label>请输入你的大名：</label>
    <input name="playerName">
    <input type="submit"/>

</form></div>
</body>
<script>
    var playerName = '${playerName}';
    document.getElementById('playerNameForm').style.visibility="hidden";
    function login(){
        if(playerName == ''){
            document.getElementById('playerNameForm').style.visibility="visible";
        }else{
          document.getElementsByTagName('form')[0].submit();
        }
    }
</script>
</html>

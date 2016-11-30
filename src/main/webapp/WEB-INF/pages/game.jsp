<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016-11-16
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>五子棋游戏</title>
    <script src='<c:url value="/resource/js/jquery-3.1.1.min.js"/>' type="text/javascript"></script>
    <style type="text/css">
        .gameInfo{
            float:left;
            margin:0;
            width:200px;
            height:768px;
        }
        .game{
            margin:0;
            float:left;
            width:800px;
            height:769px;
        }
        td{
            width:20px;
            height:20px;
            border:1px solid #000;
            background-color:#FFEB3B;
        }
        .myCurrentMove{
            border:2px green solid;
        }
        .opponentCurrentMove{
            border:2px red solid;
        }
    </style>

</head>
<body>
<div>
    <div class="gameInfo"
        <ul>
            <li>你的大名:${playerName}</li>

            <c:if test="${fiveChessGame.player1.name == playerName}">
                <li>对手:<span id="opponent">${fiveChessGame.player2.name}</span></li>
            </c:if>
            <c:if test="${fiveChessGame.player1.name != playerName}">
                <li>对手:<span id="opponent">${fiveChessGame.player1.name}</span></li>
            </c:if>
            <li>游戏状态:<span id="status">${fiveChessGame.status}</span></li>
            <li>当前下棋:<span id="currentPlayer"></span></li>
        </ul>
        <p>聊天信息:</p>
        <div id="scroll" style="height:100px;overflow: scroll">
            <ul id="talkList" >
            </ul>
        </div>
        <div><input id="talkInput"/><input type="button" value="发送" id="sendBtn"/>
        </div>
        <div><input type="button" id="giveUpBtn" value="投降"/></div>

    </div>
    <div class="game" id="game">

    </div>
</div>
</body>
<script>

    $(document).ready(function(){
        var player1Name= '${fiveChessGame.player1.name}';
        var myName = '${playerName}';
        var status = '${fiveChessGame.status}';
        var currentPlayer = '';
        var isYourTurn = false;
        var gameId = '${fiveChessGame.gameId}';
        var action = '${action}';
        var opponent = '';
        var chessColor = "white";
        var opponentChessColor="black";
        if(myName != player1Name){
            chessColor = "black";
            opponentChessColor="white";
        }

        createChessBoard();

        if(!('WebSocket' in window)){
            alert('你的浏览器不支持');
            return
        }
        var url = 'ws://'+window.location.host+'/fiveChess/'+gameId+'/'+myName+'?action='+action;
        var server = new WebSocket(url);
        server.onopen = function (event) {
            console.log('has connect to the server');

        }
        server.onmessage = function (event) {

            console.log(event.data);
            var msg = JSON.parse(event.data);
            if(msg.action == 'GAME_STARTED'){
                $('#status').text(msg.fiveChessGame.status);
                status = msg.fiveChessGame.status;

                $('#currentPlayer').text(msg.fiveChessGame.nextPlayer.name);
                if(myName == msg.fiveChessGame.nextPlayer.name){
                    isYourTurn = true;
                }

                if(myName == msg.fiveChessGame.player1.name){
                    opponent = msg.fiveChessGame.player2.name;
                }else{
                    opponent = msg.fiveChessGame.player1.name;
                }
                $('#opponent').text(opponent);
            }else if(msg.action == 'MOVE'){
               var td =  $('table').find('tr:eq('+msg.move.row+')').find('td:eq('+msg.move.col+')');
                td.css('background-color', opponentChessColor);
                isYourTurn = true;
                $('#currentPlayer').text(myName);

                $('td.opponentCurrentMove').removeClass('opponentCurrentMove');
                td.addClass('opponentCurrentMove');

                td.unbind();
            }else if(msg.action == 'GAME_OVER'){
                $('#status').text('GAME_OVER');
                $('td').unbind();
                var winnerName = msg.winner.name;
                if(myName == winnerName){

                    alert('恭喜你，你已经获胜');
                }else{
                    alert('sorry，对方已经获胜');
                }

            }else if(msg.action == 'TALK_MESSAGE'){
                $('#talkList').append($('<li>他：'+ msg.content +'</li>'));
                $('#msg_end').click();
                $('#scroll').scrollTop( $('#scroll')[0].scrollHeight );
            }else if(msg.action == 'GIVE_UP'){
                if(confirm('对方投降,你是否同意?')){
                    server.send(JSON.stringify({action:'GIVE_UP_RESP',content:true}));
                }else{
                    server.send(JSON.stringify({action:'GIVE_UP_RESP',content:false}));
                }
            }else if(msg.action == 'GIVE_UP_RESP'){
                alert('对方不同意投降');
            }
        }
        server.onclose = function (event) {
            alert('对手已怆惶逃走,请重新开始');
            window.location.href = '<c:url value="/fiveChessServlet?action=gameList"/>';
        }




      function move (data) {

          var row =data.target.getAttribute('row');
          var col = data.target.getAttribute('col');
            if(status == 'WAITING'){
                alert('失主！别着急，正在等待对手加入...');
            }else{
                if(!isYourTurn){
                    alert('客官莫急，对手正在下子．．．');
                }else{

                    server.send(JSON.stringify({'action':'MOVE','move':{'row':row,'col':col}}));
                    var td =  $('table').find('tr:eq('+row+')').find('td:eq('+col+')');
                    td.css('background-color', chessColor);
                    $('td.myCurrentMove').removeClass('myCurrentMove');
                    td.addClass('myCurrentMove');
                    td.unbind();
                    isYourTurn = false;

                    $('#currentPlayer').text(opponent);


                }
            }
        }
        function createChessBoard(){
            var table = document.createElement("table");
            for(var i = 0 ; i < 30 ; i++){
                var row = document.createElement("tr");
                for(var n = 0; n < 30; n++){
                    var td = document.createElement("td");
                    td.setAttribute('row', i);
                    td.setAttribute('col', n);
                    $(td).bind('click', {row: i, col:n},move);

                    row.appendChild(td);
                }
                table.appendChild(row);
            }
            document.getElementById('game').appendChild(table);

        }


        function sendTalkMessage(){
            if(status == 'WAITING'){
                alert('失主！别着急，正在等待对手加入...');
                return;
            }
            var content = $('#talkInput').val();
            if(content !=''){
                console.log('发送消息');
                $('#talkList').append($('<li>你：'+ content +'</li>'));
                $('#msg_end').click();
                $('#scroll').scrollTop( $('#scroll')[0].scrollHeight );
                server.send(JSON.stringify({action:'TALK_MESSAGE', content:content}));
            }
        }
        $('#sendBtn').bind('click', sendTalkMessage);

        function giveUp(){
            if(status == 'WAITING'){
                alert('失主！别着急，正在等待对手加入...');
                return;
            }



            server.send(JSON.stringify({action:'GIVE_UP'}));

        }
        $('#giveUpBtn').bind('click', giveUp);

    });
</script>
</html>

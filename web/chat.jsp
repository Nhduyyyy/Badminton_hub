<%-- 
    Document   : chat
    Created on : 17 thg 3, 2025, 14:40:41
    Author     : nhatduy261179
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/Common/header.jsp" />
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chat với Telegram Bot</title>
        <style>
            /* Reset cơ bản */
            html, body {
                margin: 0;
                padding: 0;
                height: 100%;
                background: #f1faee;
                font-family: 'Helvetica Neue', Arial, sans-serif;
            }

            /* Wrapper bọc khung chat, tạo khoảng cách với nav phía trên */
            #chatWrapper {
                position: relative;
                padding-top: 80px; /* Điều chỉnh khoảng cách này theo chiều cao của nav */
                background: #f1faee;
                height: 100vh;
            }

            /* Container chat căn giữa trong #chatWrapper */
            #chatbox {
                position: absolute;
                top: 50%;
                left: 50%;
                width: 95%;
                max-width: 700px;
                height: 90%;
                max-height: 800px;
                background: #f1faee;
                transform: translate(-50%, -50%);
                display: flex;
                flex-direction: column;
                border: 2px solid #003049;
                border-radius: 15px;
                box-shadow: 0 4px 25px rgba(0, 0, 0, 0.2);
                overflow: hidden;
            }

            /* Header */
            #chatbox::before {
                content: "Chat với Telegram Bot";
                background: #003049;
                color: #f1faee;
                padding: 20px;
                text-align: center;
                font-size: 1.6em;
                font-weight: bold;
            }

            /* Khu vực hiển thị tin nhắn */
            #chat-messages {
                flex: 1;
                overflow-y: auto;
                padding: 20px;
                background: #f1faee;
            }

            /* Khu vực nhập tin */
            #chat-input {
                display: flex;
                padding: 20px;
                background: #003049;
            }
            #chat-input input {
                flex: 1;
                padding: 12px;
                border: 2px solid #003049;
                border-radius: 8px;
                font-size: 1.1em;
            }
            #chat-input button {
                margin-left: 15px;
                padding: 12px 25px;
                border: none;
                border-radius: 8px;
                background: #f1faee;
                color: #003049;
                font-size: 1.1em;
                cursor: pointer;
                transition: background 0.3s;
            }
            #chat-input button:hover {
                background: #e2e8f0;
            }

            /* Thiết kế chat bubble cho tin nhắn chung */
            #chat-messages div {
                max-width: 75%;
                margin: 15px 0;
                padding: 15px;
                border-radius: 20px;
                clear: both;
                word-wrap: break-word;
                font-size: 1.1em;
            }

            /* Tin nhắn bên trái (ví dụ: tin từ bot) */
            #chat-messages div.left {
                background: #003049;
                color: #f1faee;
                float: left;
                border-top-left-radius: 5px;
            }

            /* Tin nhắn bên phải (ví dụ: tin từ người dùng) */
            #chat-messages div.right {
                background: #f1faee;
                color: #003049;
                float: right;
                border-top-right-radius: 5px;
                border: 1px solid #003049;
            }

            /* Clear floats */
            #chat-messages:after {
                content: "";
                display: table;
                clear: both;
            }

            /* Responsive cho màn hình nhỏ */
            @media (max-width: 600px) {
                #chatbox {
                    width: 95%;
                    height: 95%;
                }
                #chat-input input, #chat-input button {
                    font-size: 1em;
                    padding: 10px;
                }
            }
        </style>
        <script>
            function sendMessage() {
                var message = document.getElementById("message").value;
                if (message.trim() === "")
                    return;
                var xhr = new XMLHttpRequest();
                xhr.open("POST", "ChatServlet", true);
                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        loadMessages();
                    }
                };
                xhr.send("message=" + encodeURIComponent(message));
                document.getElementById("message").value = "";
            }

            function loadMessages() {
                var xhr = new XMLHttpRequest();
                xhr.open("GET", "FetchMessagesServlet", true);
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        var messages = JSON.parse(xhr.responseText);
                        var chatMessages = document.getElementById("chat-messages");
                        chatMessages.innerHTML = "";
                        for (var i = 0; i < messages.length; i++) {
                            var msg = messages[i];
                            var div = document.createElement("div");
                            div.innerHTML = "<strong>" + msg.sender + ":</strong> " + msg.text;
                            chatMessages.appendChild(div);
                        }
                        chatMessages.scrollTop = chatMessages.scrollHeight;
                    }
                };
                xhr.send();
            }

            // Tải tin nhắn mới mỗi 3 giây
            setInterval(loadMessages, 3000);
            window.onload = loadMessages;
        </script>
    </head>
    <body>
        <div id="chatWrapper">
            <div id="chatbox">
                <div id="chat-messages">
                    <div class="left"><strong>Bot:</strong> Chào bạn!</div>
                    <div class="right"><strong>Bạn:</strong> Xin chào!</div>
                    <!-- Các tin nhắn khác -->
                </div>
                <div id="chat-input">
                    <input type="text" id="message" placeholder="Nhập tin nhắn..." />
                    <button onclick="sendMessage()">Gửi</button>
                </div>
            </div>
        </div>


    </body>
</html>

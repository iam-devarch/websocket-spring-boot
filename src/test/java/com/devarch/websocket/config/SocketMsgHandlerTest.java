package com.devarch.websocket.config;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


@SpringBootTest
class SocketMsgHandlerTest {

    @Autowired
    private SocketMsgHandler socketMsgHandler;

    @Mock
    WebSocketSession session;

    @Captor
    ArgumentCaptor<WebSocketMessage<String>> webSocketMessageArgumentCaptor;
    @Test
    void handleTextMessage() throws JSONException, IOException {
        socketMsgHandler.handleTextMessage(session, new TextMessage("""
                {"user" : "testUser"}
                """));
        Mockito.doNothing().when(session).sendMessage(any());
        verify(session).sendMessage(webSocketMessageArgumentCaptor.capture());
        WebSocketMessage<String> value = webSocketMessageArgumentCaptor.getValue();
        assertEquals("Hi testUser we received your message. Thanks.", value.getPayload());
    }
}
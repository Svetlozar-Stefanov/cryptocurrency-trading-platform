package com.interviewproject.cryptocurrency_platform.websockets;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class MarketWebSocketHandler extends TextWebSocketHandler {
    private static final Logger logger = Logger.getLogger(MarketWebSocketHandler.class.getName());

    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        logger.info("afterConnectionEstablished");
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        logger.info("afterConnectionClosed");
        sessions.remove(session);
    }

    public void broadcast(String message) {
        if (sessions.isEmpty()) {logger.info("No sessions");}
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                logger.log(Level.WARNING, "Failed to send message", e);
            }
        }
    }
}

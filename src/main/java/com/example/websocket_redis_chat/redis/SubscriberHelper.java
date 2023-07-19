package com.example.websocket_redis_chat.redis;

import com.example.websocket_redis_chat.websocket.WebSocketSessionManager;
import io.lettuce.core.pubsub.RedisPubSubListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;

public class SubscriberHelper implements RedisPubSubListener<String, String> {
    private WebSocketSessionManager webSocketSessionManager;

    private static final Logger logger = LoggerFactory.getLogger(SubscriberHelper.class);
    public SubscriberHelper(WebSocketSessionManager webSocketSessionManager) {
        this.webSocketSessionManager = webSocketSessionManager;
    }

    @Override
    public void message(String channel, String message) {
        logger.info("got the message on redis " + channel + " and " + message);
        var ws = this.webSocketSessionManager.getWebSocketSessions(channel);
        try {
            ws.sendMessage(new TextMessage(message));
        } catch(IOException e ) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void message(String pattern, String channel, String message) {

    }

    @Override
    public void subscribed(String channel, long count) {

    }

    @Override
    public void psubscribed(String pattern, long count) {

    }

    @Override
    public void unsubscribed(String channel, long count) {

    }

    @Override
    public void punsubscribed(String pattern, long count) {

    }
}

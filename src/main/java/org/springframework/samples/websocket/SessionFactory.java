package org.springframework.samples.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 宗祥 on 2016/8/4.
 */
public class SessionFactory {

    public static Map<String, WebSocketSession> sessions = new HashMap<>();

    public static WebSocketSession getSession(String sid){
        WebSocketSession session = null;
        session = sessions.get(sid);
        return session;
    }

    public static void addSession(WebSocketSession session){
        sessions.put(session.getId(), session);
    }

    public static void removeSession(WebSocketSession session){
        sessions.remove(session.getId());
    }
}

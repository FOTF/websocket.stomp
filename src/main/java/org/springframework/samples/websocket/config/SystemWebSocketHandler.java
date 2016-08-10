package org.springframework.samples.websocket.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.samples.websocket.SessionFactory;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by 宗祥 on 2016/8/4.
 */
public class SystemWebSocketHandler implements WebSocketHandler {

    private Logger log = LoggerFactory.getLogger(SystemWebSocketHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.debug("ConnectionEstablished");
        SessionFactory.addSession(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.debug("handleMessage" + message.toString());
        sendMessageToUsers(session, new TextMessage(new Date() + ":" + message.toString()));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
        SessionFactory.removeSession(session);
        log.debug("handleTransportError" + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        SessionFactory.removeSession(session);
        log.debug("afterConnectionClosed" + closeStatus.getReason());

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给所有在线用户发送消息
     * @param message
     */
    public void sendMessageToUsers(WebSocketSession sourceSession, TextMessage message) {
        Map<String, WebSocketSession> sessions = SessionFactory.sessions;
        if(null == sessions && sessions.isEmpty()){
            return;
        }
        Set<String> keys = sessions.keySet();
        WebSocketSession toSession = null;
        for (String key : keys) {
            try {
                if(key.equals(sourceSession.getId())){
                    continue;
                }
                toSession = sessions.get(key);
                if(toSession.isOpen()){
                    log.debug("发送信息成功。");
                    toSession.sendMessage(message);
                }else{
                    log.debug("发送信息失败，对方已经关闭");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

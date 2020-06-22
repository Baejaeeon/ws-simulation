/*
 * Copyright 2019 The Playce-WASUP Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Revision History
 * Author            Date                Description
 * ---------------  ----------------    ------------
 * Jaeeon Bae       Jul 24, 2019            First Draft.
 */
package com.osc.ws.websocket;

import com.osc.ws.domain.Message;
import com.osc.ws.domain.SimpleJsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

import static com.osc.ws.constants.WebSocketConstants.WS_QUEUE_ALARM;
import static com.osc.ws.constants.WebSocketConstants.WS_QUEUE_USER;

/**
 * <pre>
 *
 * </pre>
 *
 * @author Jaeeon Bae
 * @version 1.0
 */
@Component
public class SessionHandler extends StompSessionHandlerAdapter {

    /**
     * The constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(SessionHandler.class);

    /**
     * The Session.
     */
    private StompSession session;

    /**
     * test url.
     */
    private static final String INIT_URL = "/app/init";

    /**
     * After connected.
     *
     * @param session          the session
     * @param connectedHeaders the connected headers
     */
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        logger.info("New session established : " + session.getSessionId());
        this.session = session;

        //subscribe(WS_QUEUE_USER + WS_QUEUE_REPLY);
        //subscribe(WS_QUEUE_USER + WS_QUEUE_ERROR);
        subscribe(WS_QUEUE_USER +  WS_QUEUE_ALARM);

        // sets message
        Message message = new Message("Today Me", "Others", "Hello, world! :)");

        // sets response
        SimpleJsonResponse response = new SimpleJsonResponse();
        response.setResultCode(200);
        response.setStatus(true);
        response.setResultMessage(session.getSessionId());
        response.setData(message);

        session.send(INIT_URL, response);
    }

    /**
     * Handle exception.
     *
     * @param session   the session
     * @param command   the command
     * @param headers   the headers
     * @param payload   the payload
     * @param exception the exception
     */
    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        logger.error("Got an exception. Reason : {}", exception.getMessage());
        logger.error("StompHeaders : [{}], Payload : [{}]", headers, new String(payload));
    }

    /**
     * Gets payload type.
     *
     * @param headers the headers
     * @return the payload type
     */
    @Override
    public Type getPayloadType(StompHeaders headers) {
        return SimpleJsonResponse.class;
    }

    /**
     * Handle frame.
     *
     * @param headers the headers
     * @param payload the payload
     */
    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        SimpleJsonResponse msg = (SimpleJsonResponse) payload;
        logger.info("Received Message : [{}]", msg);

    }

    /**
     * Subscribe.
     *
     * @param destination the destination
     */
    public synchronized void subscribe(String destination) {
        session.subscribe(destination, this);
        logger.debug("[{}] Subscribed.", destination);
    }
}
//end of EcotrolStompSessionHandler.java
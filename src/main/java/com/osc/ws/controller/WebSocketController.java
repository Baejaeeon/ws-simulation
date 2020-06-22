/*
 * Copyright 2019 The WS-Simulation Project.
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
 * Jaeeon Bae       Dec 24, 2019            First Draft.
 */
package com.osc.ws.controller;

import com.osc.ws.domain.BrowserSession;
import com.osc.ws.domain.SimpleJsonResponse;
import com.osc.ws.listener.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import static com.osc.ws.constants.WebSocketConstants.*;

/**
 * <pre>
 *
 * </pre>
 *
 * @author Jaeeon Bae
 * @version 1.0
 */
@Controller
public class WebSocketController {

    /**
     * The constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    @Autowired
    private EventListener eventListener;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * The Session listener.
     */
    @Autowired
    private EventListener sessionListener;

    @MessageMapping("/init")
    @SendToUser(WS_QUEUE_REPLY)
    public SimpleJsonResponse init(SimpMessageHeaderAccessor headerAccessor, final SimpleJsonResponse message) {
        logger.info("Init - SessionID : [{}], Message : [{}]", headerAccessor.getSessionId(), message);
        String uuid = headerAccessor.getSessionId();

        BrowserSession browserSession = new BrowserSession();
        browserSession.setSessionId(headerAccessor.getSessionId());
        browserSession.setUserId("tester");
        browserSession.setUuid(uuid);

        // register session
        sessionListener.registerBrowserSession(browserSession, headerAccessor.getSessionId());

        return message;
    }

    /**
     * Heartbeat.
     *
     * @param message the message
     * @throws Exception the exception
     */
    @MessageMapping("/heartbeat")
    public void heartbeat(SimpMessageHeaderAccessor headerAccessor, final SimpleJsonResponse message) throws Exception {
        logger.info("Heartbeat - SessionID : [{}], Message : [{}]", headerAccessor.getSessionId(), message);
    }

    /**
     * Handle exception string.
     *
     * @param exception
     * @return
     */
    @MessageExceptionHandler
    @SendToUser(WS_QUEUE_ERROR)
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }
}
//end of WebSocketController.java
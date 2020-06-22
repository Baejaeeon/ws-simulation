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
 * Jaeeon Bae       Dec 24, 2019            First Draft.
 */
package com.osc.ws.controller;

import com.osc.ws.domain.SimpleJsonResponse;
import com.osc.ws.listener.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.osc.ws.constants.WebSocketConstants.WS_QUEUE_ALARM;

/**
 * <pre>
 *
 * </pre>
 *
 * @author Jaeeon Bae
 * @version 1.0
 */
@RestController
public class AlarmController {

    private static final Logger logger = LoggerFactory.getLogger(AlarmController.class);

    @Autowired
    private EventListener sessionListener;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private static final String USER_ID = "tester";

    @PostMapping(value = "/sendAlarm")
    public String sendAlaram(@RequestBody String message) {
        logger.info("Gets message :: [{}]", message);

        SimpleJsonResponse response = new SimpleJsonResponse();
        response.setResultMessage("response Message");
        response.setStatus(true);
        response.setData(message);

        sendAlram(response);

        return "success";
    }

    // send message from server to client
    private void sendAlram(SimpleJsonResponse response) {
        List<String> sessionList = sessionListener.findSessionIdsByMemberId(USER_ID);

        if (sessionList.size() > 0) {
            for (String sessionId : sessionList) {

                simpMessagingTemplate.convertAndSendToUser(sessionId, WS_QUEUE_ALARM,
                        response, sessionListener.createHeaders(sessionId));
            }
        }
    }
}
//end of AlarmController.java
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
package com.osc.ws;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * <pre>
 *
 * </pre>
 *
 * @author Jaeeon Bae
 * @version 1.0
 */
public class AlaramTest {

    private static final Logger logger = LoggerFactory.getLogger(AlaramTest.class);

    private static final String USER_AGENT = "Mozila/5.0";
    private static final String SERVICE_URL = "http://localhost:8080/sendAlarm";

    @Test
    public void sendMessage() throws Exception {
        String message = "WebSocket Test :: Hello, Wolrd! :)";

        sendPost(message);
    }

    // send message from server to client
    private void sendPost(String message) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", USER_AGENT);

        System.err.println("message : " + message);
        String body = message;

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(SERVICE_URL, request, String.class);

        logger.info("#### statusCode : [{}], body : [{}]", response.getStatusCode(), response.getBody());
    }
}
//end of AlaramTest.java
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
package com.osc.ws.config;

import com.osc.ws.interceptor.HttpHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

/**
 * <pre>
 * WebSocket 관련 Spring 설정 클래스
 * </pre>
 *
 * @author Jaeeon Bae
 * @version 1.0
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // https://www.baeldung.com/spring-security-websockets
    // https://supawer0728.github.io/2018/03/30/spring-websocket/

    /**
     * Configure message broker.
     *
     * @param config the config
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // eg. To invoke @MessageMapping("/test"), send message's destination is /app/test
        config.setApplicationDestinationPrefixes("/app");

        // eg. Subscribe message's destination is /topic/{value} and use @SendTo annotation in controller
        // eg. Subscribe message's destination is /user/queue/{value} and use @SendToUser annotation in controller
        config.enableSimpleBroker("/topic", "/queue");
    }

    /**
     * Register stomp endpoints.
     *
     * @param registry the registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket").setAllowedOrigins("*").addInterceptors(new HttpHandshakeInterceptor());
        registry.addEndpoint("/websocket").setAllowedOrigins("*").addInterceptors(new HttpHandshakeInterceptor()).withSockJS();
    }

    /**
     * Configure web socket transport.
     *
     * @param registration the registration
     */
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(160 * 64 * 1024);    // Max incoming message size, default : 64 * 1024
        registration.setSendTimeLimit(20 * 10000);            // default : 10 * 10000
        registration.setSendBufferSizeLimit(10 * 512 * 1024); // Max outgoing buffer size, default : 512 * 1024
    }
}
//end of WebSocketConfig.java